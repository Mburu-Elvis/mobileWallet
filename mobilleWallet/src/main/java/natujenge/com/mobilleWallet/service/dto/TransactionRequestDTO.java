package natujenge.com.mobilleWallet.service.dto;

import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.domain.enums.TransactionStatus;
import natujenge.com.mobilleWallet.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionRequestDTO {
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType transactionType;
    private UUID userReceived;
    private UUID userId;
    private String description;


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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public UUID getUserReceived() {
        return userReceived;
    }

    public void setUserReceived(UUID userReceived) {
        this.userReceived = userReceived;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionRequestDTO{" +
                "amount=" + amount +
                ", status=" + status +
                ", transactionType=" + transactionType +
                ", userReceived=" + userReceived +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                '}';
    }
}
