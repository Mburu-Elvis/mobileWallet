package natujenge.com.mobilleWallet.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class Messaging {
    public void sendMessage(String message, String phoneNumber) {
        String url = "https://api2.tiaraconnect.io/api/messaging/sendsms";

        WebClient webClient = WebClient.create(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("to", phoneNumber);
        requestBody.put("from", "TIARACONECT");
        requestBody.put("message", message);
        requestBody.put("refId", "09wiwu088e");

        // Perform the POST request and handle the response
        webClient.post()
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(res -> {
                    return Mono.just("Message Sent");
                })
                .block(); // Block to wait for the response
    }
}
