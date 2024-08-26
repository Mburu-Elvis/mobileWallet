package natujenge.com.mobilleWallet.repository;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUserPhoneNumber(String phoneNumber);
}
