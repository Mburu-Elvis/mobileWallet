package natujenge.com.mobilleWallet.service.dto;

import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.domain.enums.TransactionStatus;
import natujenge.com.mobilleWallet.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionRequestDTO {
    private BigDecimal amount;;
    private String from;
    private String to;
    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
