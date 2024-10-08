package natujenge.com.mobilleWallet.web.rest;

import natujenge.com.mobilleWallet.service.UserService;
import natujenge.com.mobilleWallet.service.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://127.0.0.1:8081")
public class UserController {
    @Autowired
    UserService userService = new UserService();

    @GetMapping("/otp")
    public String generateOTP(@RequestParam String phoneNumber) {
        return userService.generateOTP(phoneNumber);
    }

    @PostMapping(path="/register")
    public void createUser(@RequestBody UserRequestDTO accountRequestDTO) {
        userService.createUser(accountRequestDTO);
    }

    @PostMapping(path="/signin")
    public ResponseEntity<?> signin(@RequestBody UserRequestDTO userRequestDTO) throws AccountNotFoundException {
        return  userService.userSignin(userRequestDTO);
    }

    @PutMapping(path="/users/profile")
    public void updateProfile(@RequestBody UserRequestDTO userRequestDTO) {
        userService.updateProfile(userRequestDTO);
    }

    @PutMapping(path="/users/password")
    public void changePassword(@RequestBody UserRequestDTO userRequestDTO) {
        userService.changePassword(userRequestDTO);
    }
}
