package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.service.AccountService;
import natujenge.com.mobilleWallet.service.dto.AccountResponseDTO;
import natujenge.com.mobilleWallet.service.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    AccountService accountService = new AccountService();

    @GetMapping("/balance/{phoneNumber}")
    public ResponseEntity<AccountResponseDTO> getBalance(@PathVariable String phoneNumber) {

       AccountResponseDTO response = accountService.getBalance(phoneNumber);

       return ResponseEntity.ok().body(response);
    }

}
