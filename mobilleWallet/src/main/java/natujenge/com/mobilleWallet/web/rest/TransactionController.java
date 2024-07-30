package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.domain.Transaction;
import natujenge.com.mobilleWallet.service.TransactionService;
import natujenge.com.mobilleWallet.service.dto.TransactionRequestDTO;
import natujenge.com.mobilleWallet.service.dto.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class TransactionController {
    @Autowired
    TransactionService transactionService = new TransactionService();

    @PostMapping("/deposit")
    public void depositFunds(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        System.out.println("Received: " + transactionRequestDTO);
        transactionService.depositFunds(transactionRequestDTO);
    }

    @PostMapping("/transfer")
    public void transferFunds(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        System.out.println("Received: " + transactionRequestDTO);
        transactionService.transferFunds(transactionRequestDTO);
    }

    @GetMapping("/statement")
    public ResponseEntity<List<TransactionResponseDTO>> generateStatement(@RequestParam String email) {
        return transactionService.generateStatement(email);
    }


}
