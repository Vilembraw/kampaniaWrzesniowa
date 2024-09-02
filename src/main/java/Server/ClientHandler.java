package Server;



import client.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping
public class ClientHandler {

    private int tokenCounter = 1;
    private List<Client> clients = new ArrayList<>();



    @PostMapping("/register")
    @ResponseBody
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
    @ResponseBody
    public List<Client> getRegTokens(){
        return clients;
    }


    @GetMapping("/image")
    public String showImage(Model model){
        try {
            BufferedImage bufferedImage = generateBlackImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            String encoded = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image",encoded);
            return "image";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public BufferedImage generateBlackImage(){
        BufferedImage bufferedImage = new BufferedImage(512,512,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; bufferedImage.getHeight() < y; y++){
            for(int x = 0; bufferedImage.getWidth() < x; x++){
                int r = 0;
                int g = 0;
                int b = 0;
                Color color = new Color(r,g,b);
                bufferedImage.setRGB(x,y,color.getRGB());
            }
        }
        return bufferedImage;
    }
}
