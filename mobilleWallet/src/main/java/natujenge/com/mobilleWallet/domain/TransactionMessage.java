package natujenge.com.mobilleWallet.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TransactionMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactiona_message_id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="transaction_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private String transactionMessage;

    public UUID getTransactiona_message_id() {
        return transactiona_message_id;
    }

    public void setTransactiona_message_id(UUID transactiona_message_id) {
        this.transactiona_message_id = transactiona_message_id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    @Override
    public String toString() {
        return "TransactionMessage{" +
                "transactiona_message_id=" + transactiona_message_id +
                ", transaction=" + transaction +
                ", userId=" + userId +
                ", transactionDate=" + transactionDate +
                ", transactionMessage='" + transactionMessage + '\'' +
                '}';
    }
}
