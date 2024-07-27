package natujenge.com.mobilleWallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import natujenge.com.mobilleWallet.domain.Account;
import natujenge.com.mobilleWallet.repository.AccountRepository;
import natujenge.com.mobilleWallet.service.dto.AccountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public String generateOTP(String phoneNumber) {
        String otp = "Your verification code for mobileWallet\n";
        Random rand = new Random();
        otp = otp + (rand.nextInt(9999 - 1001) + 1000);

        String url = "https://api2.tiaraconnect.io/api/messaging/sendsms";

        WebClient webClient = WebClient.create(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("to", phoneNumber);
        requestBody.put("from", "TIARACONECT");
        requestBody.put("message", otp);
        requestBody.put("refId", "09wiwu088e");

        // Perform the POST request and handle the response
        webClient.post()
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(res -> {
                    return Mono.just("OTP sent");
                })
                .block(); // Block to wait for the response

        return otp;
    }

    public void createAccount(AccountRequestDTO accountRequestDTO) {
       Account account = accountRepository.findByPhoneNumber(accountRequestDTO.getPhoneNumber());

       if (account != null) {
           throw new RuntimeException();
       }

       Account acc = new Account();
       acc.setFirstName(accountRequestDTO.getFirstName());
       acc.setLastName(accountRequestDTO.getLastName());
       acc.setEmail(accountRequestDTO.getEmail());
       acc.setPhoneNumber(accountRequestDTO.getPhoneNumber());
       acc.setPin(accountRequestDTO.getPin());

       Account savedAccount = accountRepository.save(acc);
    }
}