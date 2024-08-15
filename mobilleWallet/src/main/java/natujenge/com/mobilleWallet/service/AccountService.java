package natujenge.com.mobilleWallet.service;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.repository.UserRepository;
import natujenge.com.mobilleWallet.service.dto.AccountResponseDTO;
import natujenge.com.mobilleWallet.service.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public AccountResponseDTO getBalance(String phoneNumber) {
        Account acc = accountRepository.findByUserPhoneNumber(phoneNumber);

        if (acc == null) {
            throw new RuntimeException("User not found");
        }

        AccountResponseDTO responseDTO = new AccountResponseDTO();
        responseDTO.setPhoneNumber(acc.getUser().getPhoneNumber());
        responseDTO.setBalance(acc.getBalance());

        return responseDTO;
    }
}