package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.service.AccountService;
import natujenge.com.mobilleWallet.service.dto.AccountRequestDTO;
import natujenge.com.mobilleWallet.service.dto.AccountResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    AccountService accountService = new AccountService();

    @PostMapping(path="/accounts")
    public void createEmployee(@RequestBody AccountRequestDTO accountRequestDTO) {

    }
}
