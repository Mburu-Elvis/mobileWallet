package natujenge.com.mobilleWallet.domain;

import jakarta.persistence.*;
import natujenge.com.mobilleWallet.domain.enums.TransactionStatus;
import natujenge.com.mobilleWallet.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="tbl_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transction_id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private String user_received;

    @Column(nullable = false)
    private LocalDateTime transaction_date;

    @Column(nullable = true)
    private String description;

    public UUID getTransction_id() {
        return transction_id;
    }

    public void setTransction_id(UUID transction_id) {
        this.transction_id = transction_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getUser_received() {
        return user_received;
    }

    public void setUser_received(String user_received) {
        this.user_received = user_received;
    }

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transction_id=" + transction_id +
                ", user=" + user +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", status=" + status +
                ", user_received='" + user_received + '\'' +
                ", transaction_date=" + transaction_date +
                ", description='" + description + '\'' +
                '}';
    }
}
