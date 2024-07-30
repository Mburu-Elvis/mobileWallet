package natujenge.com.mobilleWallet.service;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.exceptions.UserNotFoundException;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.repository.TransactionRepository;
import natujenge.com.mobilleWallet.repository.UserRepository;
import natujenge.com.mobilleWallet.service.dto.TransactionRequestDTO;
import natujenge.com.mobilleWallet.service.dto.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        if (accountRepository.findById(transactionRequestDTO.getUserId()) != null) {
            if (accountRepository.findById(transactionRequestDTO.getUserReceived()) == null) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }

        Account fromAccount = accountRepository.findByUserId(transactionRequestDTO.getUserId());
        BigDecimal fromAccountBalance = fromAccount.getBalance();
        fromAccountBalance = fromAccountBalance.subtract(transactionRequestDTO.getAmount());
        fromAccount.setBalance(fromAccountBalance);
        Account toAccount = accountRepository.findByUserId(transactionRequestDTO.getUserReceived());
        BigDecimal toAccountBalance = toAccount.getBalance();
        toAccountBalance = toAccountBalance.add(transactionRequestDTO.getAmount());
        toAccount.setBalance(toAccountBalance);

        Transaction transaction = new Transaction();
        User user = userRepository.findById(transactionRequestDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found"));
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getUserReceived());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(transactionRequestDTO.getTransactionType());
        transaction.setStatus(transactionRequestDTO.getStatus());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransaction_date(LocalDateTime.now());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transactionRepository.save(transaction);
    }

    public ResponseEntity<List<TransactionResponseDTO>> generateStatement(String email) {
        System.out.println("Starting");
        User user = userRepository.findByEmail(email);
        System.out.println("\nUUID: " + user.getId() + "\n");
        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());
        List<TransactionResponseDTO> statements = transactions.stream().map(this::convertToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(statements);
    }

    private TransactionResponseDTO convertToDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();

        User from = transaction.getUser();
        User to = userRepository.findById(transaction.getUser_received()).orElseThrow(() -> new UserNotFoundException("user not found"));

        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setFrom(from.getPhoneNumber());
        dto.setUser_received(to.getPhoneNumber());
        dto.setTransaction_date(transaction.getTransaction_date());

        return dto;
    }
}
