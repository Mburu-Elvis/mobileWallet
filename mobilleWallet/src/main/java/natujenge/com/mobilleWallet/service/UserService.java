package natujenge.com.mobilleWallet.service;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.User;
import natujenge.com.mobilleWallet.helper.Messaging;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.repository.UserRepository;
import natujenge.com.mobilleWallet.service.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public String generateOTP(String phoneNumber) {
        String otpMessage = "Your verification code for mobileWallet\n";
        String otp = "";
        Random rand = new Random();
        otp = otp + (rand.nextInt(9999 - 1001) + 1000);

        return otp;
    }

    public void createUser(UserRequestDTO userRequestDTO) {
       User user = userRepository.findByUsername(userRequestDTO.getUsername());

       if (user != null) {
           throw new RuntimeException();
       }

       Scanner otpObj = new Scanner(System.in);

       try {
           String otp = generateOTP(userRequestDTO.getPhoneNumber());
           String message = "Your verification code for mobileWallet\n" + otp;
           Messaging msgObj = new Messaging();
           msgObj.sendMessage(message, userRequestDTO.getPhoneNumber());
           System.out.println("Enter the OTP ");
           String inputOtp = otpObj.nextLine().trim(); // Trim input immediately

           if (inputOtp.equals(otp)) {
               System.out.println("User Created");

               User usr = new User();
               usr.setFullname(userRequestDTO.getFullname());
               usr.setUsername(userRequestDTO.getUsername());
               usr.setEmail(userRequestDTO.getEmail());
               usr.setPhoneNumber(userRequestDTO.getPhoneNumber());
               usr.setPassword(userRequestDTO.getPassword());
               usr.setCreated_at(LocalDateTime.now());
               usr.setUpdated_at(LocalDateTime.now());
               User savedAccount = userRepository.save(usr);

               Account account = new Account();
               account.setUser(savedAccount);
               account.setBalance(BigDecimal.ZERO);
               account.setUpdated_at(LocalDateTime.now());
               accountRepository.save(account);

           } else {
               System.out.println(inputOtp + " Incorrect OTP");
           }
       } finally {
            otpObj.close();
       }
    }

    public ResponseEntity<?> userSignin(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return ResponseEntity.badRequest().body("Request body is missing");
        }

        if ( userRequestDTO.getEmail() == null && userRequestDTO.getUsername() == null || userRequestDTO.getPassword() == null ) {
            return ResponseEntity.badRequest().body("email or phone number is required");
        }

        User usr = userRepository.findByEmail(userRequestDTO.getEmail());
        if (usr == null && userRequestDTO.getUsername() != null) {
            usr = userRepository.findByUsername(userRequestDTO.getUsername());
        }

        if (usr == null) {
            return ResponseEntity.badRequest().body("user not found");
        }

        if (!userRequestDTO.getPassword().equals(usr.getPassword())) {
            return ResponseEntity.badRequest().body("invalid email  or password. Please try again");
        }

        Map response = new HashMap<>();
        response.put("message", "signin successful");
        return ResponseEntity.ok().body(response);
    }

    public void updateProfile(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail());

        if (user == null) {
            user = userRepository.findByUsername(userRequestDTO.getUsername());
        }

        if (user == null) {
            throw new RuntimeException();
        }

        user.setFullname(userRequestDTO.getFullname());
        user.setUsername(userRequestDTO.getUsername());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setUpdated_at(LocalDateTime.now());
        userRepository.save(user);
    }

    public void changePassword(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail());

        if (user == null) {
            throw new RuntimeException();
        }

        user.setPassword(userRequestDTO.getPassword());

        userRepository.save(user);
    }
}