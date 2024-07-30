package natujenge.com.mobilleWallet.service;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.exceptions.UserNotFoundException;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.repository.TransactionRepository;
import natujenge.com.mobilleWallet.repository.UserRepository;
import natujenge.com.mobilleWallet.service.dto.TransactionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void depositFunds(TransactionRequestDTO transactionRequestDTO) {
        User user = userRepository.findById(transactionRequestDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found"));

        if (accountRepository.findById(transactionRequestDTO.getUserReceived()) == null || accountRepository.findById(transactionRequestDTO.getUserId()) == null) {
            throw new RuntimeException("User does not exist");
        }

        Account account = accountRepository.findByUserId(transactionRequestDTO.getUserId());
        BigDecimal balance = account.getBalance();
        balance = balance.add(transactionRequestDTO.getAmount());
        account.setBalance(balance);

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getUserReceived());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(transactionRequestDTO.getTransactionType());
        transaction.setStatus(transactionRequestDTO.getStatus());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransaction_date(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public void transferFunds(TransactionRequestDTO transactionRequestDTO) {

    }
}
