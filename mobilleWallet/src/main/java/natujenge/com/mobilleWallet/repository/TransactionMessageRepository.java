package natujenge.com.mobilleWallet.repository;

import natujenge.com.mobilleWallet.domain.TransactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionMessageRepository extends JpaRepository<TransactionMessage, UUID> {
}
