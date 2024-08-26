package natujenge.com.mobilleWallet.service.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {
    private String phoneNumber;

    private BigDecimal balance;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountResponseDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
