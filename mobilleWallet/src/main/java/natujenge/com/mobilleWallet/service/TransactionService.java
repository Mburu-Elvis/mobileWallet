package natujenge.com.mobilleWallet.service;

import com.itextpdf.text.DocumentException;
import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.domain.TransactionMessage;
import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.exceptions.UserNotFoundException;
import natujenge.com.mobilleWallet.helper.Messaging;
import natujenge.com.mobilleWallet.helper.StatementGenerator;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.repository.TransactionMessageRepository;
import natujenge.com.mobilleWallet.repository.TransactionRepository;
import natujenge.com.mobilleWallet.repository.UserRepository;
import natujenge.com.mobilleWallet.service.dto.TransactionRequestDTO;
import natujenge.com.mobilleWallet.service.dto.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    private TransactionMessageRepository transactionMessageRepository;

    public void depositFunds(TransactionRequestDTO transactionRequestDTO) {
        User user = userRepository.findById(transactionRequestDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found"));

        if (accountRepository.findById(transactionRequestDTO.getUserReceived()) == null || accountRepository.findById(transactionRequestDTO.getUserId()) == null) {
            throw new RuntimeException("User does not exist");
        }

        Account account = accountRepository.findByUserId(transactionRequestDTO.getUserId());
        BigDecimal balance = account.getBalance();
        System.out.println("Existing balance: " + balance);
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

        LocalDateTime transactionDateTime = transaction.getTransaction_date();
        String date = transactionDateTime.toLocalDate().toString();
        int hour = transactionDateTime.getHour();
        int minute = transactionDateTime.getMinute();

        Messaging msgObj = new Messaging();
        BigDecimal amt = transactionRequestDTO.getAmount();
        String message = String.format("Confirmed on %s at %02d:%02d Ksh %.2f has been deposited to your account. Your new account balance is %.2f",
                date, hour, minute, amt, balance);
        msgObj.sendMessage(message, user.getPhoneNumber());


        Transaction savedTransaction =  transactionRepository.save(transaction);

        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setTransaction(savedTransaction);
        transactionMessage.setTransactionDate(savedTransaction.getTransaction_date());
        transactionMessage.setTransactionMessage(message);
        transactionMessage.setUserId(savedTransaction.getUser_received());

        transactionMessageRepository.save(transactionMessage);

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
        User toUser = userRepository.findById(transactionRequestDTO.getUserReceived()).orElseThrow(() -> new UserNotFoundException("User not Found"));
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getUserReceived());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(transactionRequestDTO.getTransactionType());
        transaction.setStatus(transactionRequestDTO.getStatus());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransaction_date(LocalDateTime.now());

        Messaging msgObj = new Messaging();

        LocalDateTime transactionDateTime = transaction.getTransaction_date();
        String date = transactionDateTime.toLocalDate().toString();
        int hour = transactionDateTime.getHour();
        int minute = transactionDateTime.getMinute();
        String fromMessage = String.format("Confirmed on %s at %02d:%02d Ksh %f has been sent to %s. Your new account balance is %.2f",
                date, hour, minute, transactionRequestDTO.getAmount(), toUser.getPhoneNumber(),toAccountBalance);
        String toMessage = String.format("Confirmed on %s at %02d:%02d Ksh %f has been received from %s. Your new account balance is %.2f",
                date, hour, minute, transactionRequestDTO.getAmount(), user.getPhoneNumber(),toAccountBalance);

        msgObj.sendMessage(fromMessage, user.getPhoneNumber());
        msgObj.sendMessage(toMessage, toUser.getPhoneNumber());

        TransactionMessage transactionMessage = new TransactionMessage();


        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionMessage.setUserId(savedTransaction.getUser().getId());
        transactionMessage.setTransactionDate(savedTransaction.getTransaction_date());
        transactionMessage.setTransaction(savedTransaction);
        transactionMessage.setTransactionMessage(toMessage);

        transactionMessageRepository.save(transactionMessage);
        transactionMessage.setTransactionDate(savedTransaction.getTransaction_date());
        transactionMessage.setTransaction(savedTransaction);
        transactionMessage.setTransactionMessage(fromMessage);
        transactionMessage.setUserId(savedTransaction.getUser_received());
        transactionMessageRepository.save(transactionMessage);
    }

    public ResponseEntity<List<TransactionResponseDTO>> generateStatement(String email) throws DocumentException, FileNotFoundException {
        System.out.println("Starting");
        User user = userRepository.findByEmail(email);
        System.out.println("\nUUID: " + user.getId() + "\n");
        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());
        List<TransactionResponseDTO> statements = transactions.stream().map(this::convertToDTO).collect(Collectors.toList());

        StatementGenerator stmt = new StatementGenerator();
        stmt.generateStatement(statements);;

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
