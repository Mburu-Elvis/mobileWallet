package natujenge.com.mobilleWallet.repository;

import natujenge.com.mobilleWallet.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
