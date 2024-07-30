package natujenge.com.mobilleWallet.service;

import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.domain.User;
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

//        String url = "https://api2.tiaraconnect.io/api/messaging/sendsms";
//
//        WebClient webClient = WebClient.create(url);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        headers.set("Authorization", "");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("to", phoneNumber);
//        requestBody.put("from", "TIARACONECT");
//        requestBody.put("message", otpMessage + otp);
//        requestBody.put("refId", "09wiwu088e");
//
//        // Perform the POST request and handle the response
//        webClient.post()
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .flatMap(res -> {
//                    return Mono.just("OTP sent");
//                })
//                .block(); // Block to wait for the response

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
           System.out.println("OTP: " + otp);
           System.out.println("Enter the OTP ");
           String inputOtp = otpObj.nextLine().trim(); // Trim input immediately

           // Debugging to ensure input is captured correctly
           System.out.println("Generated OTP: [" + otp + "]");
           System.out.println("Input OTP after trim: [" + inputOtp + "]");
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
}