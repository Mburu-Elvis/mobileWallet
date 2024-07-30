package natujenge.com.mobilleWallet.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private BigDecimal amount;
    private String description;
    private LocalDateTime transaction_date;
    private String user_received;
    private String from;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getUser_received() {
        return user_received;
    }

    public void setUser_received(String user_received) {
        this.user_received = user_received;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "TransactionResponseDTO{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", transaction_date=" + transaction_date +
                ", user_received='" + user_received + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
