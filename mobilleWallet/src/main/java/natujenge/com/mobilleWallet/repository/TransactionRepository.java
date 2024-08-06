package natujenge.com.mobilleWallet.repository;

import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.service.dto.TransactionResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByUserId(UUID userId);
}
