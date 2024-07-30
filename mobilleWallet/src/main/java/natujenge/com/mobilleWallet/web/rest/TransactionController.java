package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.service.TransactionService;
import natujenge.com.mobilleWallet.service.dto.TransactionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
