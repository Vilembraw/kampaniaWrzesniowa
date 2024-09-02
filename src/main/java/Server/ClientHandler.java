package Server;



import client.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientHandler {

    private int tokenCounter = 1;
    private List<Integer> regTokens = new ArrayList<>();


    @PostMapping("/register")
    public ResponseEntity<Client> register(){
//        String token = UUID.randomUUID().toString();
        int token = tokenCounter++;
        LocalDateTime createdAt = LocalDateTime.now();
        regTokens.add(token);
        Client client = new Client(token,createdAt);
        return ResponseEntity.ok(client);
    }

}
