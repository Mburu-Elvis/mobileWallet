package natujenge.com.mobilleWallet.service;

import com.itextpdf.text.DocumentException;
import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.domain.TransactionMessage;
import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.domain.enums.TransactionStatus;
import natujenge.com.mobilleWallet.domain.enums.TransactionType;
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
        User user = userRepository.findByPhoneNumber(transactionRequestDTO.getFrom());

        if (accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom()) == null) {
            throw new RuntimeException("User does not exist");
        }

        Account account = accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom());
        BigDecimal balance = account.getBalance();
        System.out.println("Existing balance: " + balance);
        balance = balance.add(transactionRequestDTO.getAmount());
        account.setBalance(balance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getFrom());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
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
        System.out.println(message);


        Transaction savedTransaction =  transactionRepository.save(transaction);

        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setTransaction(savedTransaction);
        transactionMessage.setTransactionDate(savedTransaction.getTransaction_date());
        transactionMessage.setTransactionMessage(message);
        transactionMessage.setUserId(userRepository.findByPhoneNumber(savedTransaction.getUser_received()).getId());

        transactionMessageRepository.save(transactionMessage);

    }

    public void withdrawFunds(TransactionRequestDTO transactionRequestDTO) {
        User user = userRepository.findByPhoneNumber(transactionRequestDTO.getFrom());

        if (accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom()) == null) {
            throw new RuntimeException("User does not exist");
        }

        Account account = accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom());
        BigDecimal balance = account.getBalance();
        System.out.println("Existing balance: " + balance);
        balance = balance.subtract(transactionRequestDTO.getAmount());
        account.setBalance(balance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getFrom());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransaction_date(LocalDateTime.now());

        LocalDateTime transactionDateTime = transaction.getTransaction_date();
        String date = transactionDateTime.toLocalDate().toString();
        int hour = transactionDateTime.getHour();
        int minute = transactionDateTime.getMinute();

        Messaging msgObj = new Messaging();
        BigDecimal amt = transactionRequestDTO.getAmount();
        String message = String.format("Confirmed on %s at %02d:%02d Ksh %.2f has been withdrawn from your account. Your new account balance is %.2f",
                date, hour, minute, amt, balance);
        msgObj.sendMessage(message, user.getPhoneNumber());
        System.out.println(message);


        Transaction savedTransaction =  transactionRepository.save(transaction);

        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setTransaction(savedTransaction);
        transactionMessage.setTransactionDate(savedTransaction.getTransaction_date());
        transactionMessage.setTransactionMessage(message);
        transactionMessage.setUserId(userRepository.findByPhoneNumber(savedTransaction.getUser_received()).getId());

        transactionMessageRepository.save(transactionMessage);
    }

    public void transferFunds(TransactionRequestDTO transactionRequestDTO) throws RuntimeException {
        if (accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom()) != null) {
            if (accountRepository.findByUserPhoneNumber(transactionRequestDTO.getTo()) == null) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }

        Account fromAccount = accountRepository.findByUserPhoneNumber(transactionRequestDTO.getFrom());
        if (fromAccount == null) {
            throw new RuntimeException("Account does not exist");
        }
        if (fromAccount.getBalance().compareTo(transactionRequestDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        BigDecimal fromAccountBalance = fromAccount.getBalance();
        fromAccountBalance = fromAccountBalance.subtract(transactionRequestDTO.getAmount());
        fromAccount.setBalance(fromAccountBalance);
        Account toAccount = accountRepository.findByUserPhoneNumber(transactionRequestDTO.getTo());
        BigDecimal toAccountBalance = toAccount.getBalance();
        toAccountBalance = toAccountBalance.add(transactionRequestDTO.getAmount());
        toAccount.setBalance(toAccountBalance);

        Transaction transaction = new Transaction();
        User user = userRepository.findByPhoneNumber(transactionRequestDTO.getFrom());
        User toUser = userRepository.findByPhoneNumber(transactionRequestDTO.getTo());
        transaction.setUser(user);
        transaction.setUser_received(transactionRequestDTO.getTo());
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransaction_date(LocalDateTime.now());

        Messaging msgObj = new Messaging();

        LocalDateTime transactionDateTime = transaction.getTransaction_date();
        String date = transactionDateTime.toLocalDate().toString();
        int hour = transactionDateTime.getHour();
        int minute = transactionDateTime.getMinute();
        String fromMessage = String.format("Confirmed on %s at %02d:%02d Ksh %.2f has been sent to %s. Your new account balance is %.2f",
                date, hour, minute, transactionRequestDTO.getAmount(), toUser.getPhoneNumber(),fromAccountBalance);
        String toMessage = String.format("Confirmed on %s at %02d:%02d Ksh %.2f has been received from %s. Your new account balance is %.2f",
                date, hour, minute, transactionRequestDTO.getAmount(), user.getPhoneNumber(),toAccountBalance);

        msgObj.sendMessage(fromMessage, user.getPhoneNumber());
        msgObj.sendMessage(toMessage, toUser.getPhoneNumber());
        System.out.println(fromMessage);
        System.out.println(toMessage);

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
        transactionMessage.setUserId(userRepository.findByPhoneNumber(transactionRequestDTO.getTo()).getId());
        transactionMessageRepository.save(transactionMessage);
    }

    public ResponseEntity<List<TransactionResponseDTO>> generateStatement(String email) throws DocumentException, FileNotFoundException {
        User user = userRepository.findByEmail(email);
        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());
        List<TransactionResponseDTO> statements = transactions.stream().map(this::convertToDTO).collect(Collectors.toList());

        StatementGenerator stmt = new StatementGenerator();
        stmt.generateStatement(statements);

        return ResponseEntity.ok(statements);
    }

    public void downloadStatement(String email) throws DocumentException, FileNotFoundException {
        User user = userRepository.findByEmail(email);
        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());
        List<TransactionResponseDTO> statements = transactions.stream().map(this::convertToDTO).collect(Collectors.toList());

        StatementGenerator stmt = new StatementGenerator();
        stmt.generateStatement(statements);
    }

    private TransactionResponseDTO convertToDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();

        User from = transaction.getUser();
        User to = userRepository.findByPhoneNumber(transaction.getUser_received());

        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setFrom(from.getPhoneNumber());
        dto.setUser_received(to.getPhoneNumber());
        dto.setTransaction_date(transaction.getTransaction_date());

        return dto;
    }
}
