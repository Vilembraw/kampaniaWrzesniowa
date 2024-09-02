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
    private List<Client> clients = new ArrayList<>();



    @PostMapping("/register")
    public ResponseEntity<Client> register(){
//        String token = UUID.randomUUID().toString();
        int token = tokenCounter++;
        LocalDateTime createdAt = LocalDateTime.now();
        Client client = new Client(token,createdAt);
        clients.add(client);
        client.setActive(true);
        return ResponseEntity.ok(client);
    }


    @GetMapping("/tokens")
    public List<Client> getRegTokens(){
        return clients;
    }
}
