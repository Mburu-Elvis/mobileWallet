package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.service.AccountService;
import natujenge.com.mobilleWallet.service.dto.AccountRequestDTO;
import natujenge.com.mobilleWallet.service.dto.AccountResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    AccountService accountService = new AccountService();

    @GetMapping("/otp")
    public String generateOTP(@RequestParam String phoneNumber) {
        return accountService.generateOTP(phoneNumber);
    }

    @PostMapping(path="/accounts")
    public void createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        accountService.createAccount(accountRequestDTO);
    }
}
