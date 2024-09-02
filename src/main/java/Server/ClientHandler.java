package Server;



import client.Client;
import client.Pixel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.*;
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
        for(Client client : clients){
            if(client.isExpired()){
                client.setActive(false);
            }
        }
        return clients;
    }


    @GetMapping("/image")
    public String showImage(Model model){
        try {
            BufferedImage bufferedImage = generateImageDB();
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


    @PostMapping("/pixel")
    public ResponseEntity<String> pixel(@RequestBody Pixel pixel){
        Client wantedClient = null;
        for(Client client : clients){
            if(pixel.getId() == client.getToken()){
                wantedClient = client;
                if(client.isExpired()){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token is not active");
                }
            }
        }
        if(pixel.getX() > 512 || pixel.getX() < 0 || pixel.getY() > 512 || pixel.getY() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("x y incorrect");

        }

        try {
            String path = "pixele.db";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            String sql = "INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement statement =  connection.prepareStatement(sql);
            statement.setInt(1,pixel.getId());
            statement.setInt(2,pixel.getX());
            statement.setInt(3,pixel.getY());
            statement.setString(4,pixel.getHexColor());
            statement.setString(5,wantedClient.getRegTime().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }



    @GetMapping("/tabela")
    public void createTable(){
        try {
            String path = "pixele.db";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            String sql = "CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL)";
            PreparedStatement statement =  connection.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage generateImageDB(){
        List<Pixel> pixels = new ArrayList<>();
        try {
            String path = "pixele.db";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            String sql = "SELECT token, x, y, color FROM entry ORDER BY timestamp";
            PreparedStatement statement =  connection.prepareStatement(sql);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Pixel pixel = new Pixel(resultSet.getInt("token"),resultSet.getInt("x"),resultSet.getInt("y"),resultSet.getString("color"));
                    pixels.add(pixel);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        BufferedImage bufferedImage = generateBlackImage();

        for(Pixel pixel : pixels){
            int newColor = pixel.parseColor();
            int red = (newColor >> 16) & 0xFF; // przesunięcie o 16 bitów i maskowanie
            int green = (newColor >> 8) & 0xFF; // przesunięcie o 8 bitów i maskowanie
            int blue = newColor & 0xFF; // maskowanie bez przesunięcia
            Color color = new Color(red,green,blue);
            bufferedImage.setRGB(pixel.getX(),pixel.getY(),color.getRGB());
        }

        return bufferedImage;
    }
}
