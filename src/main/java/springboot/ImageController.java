package springboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static java.lang.Math.clamp;

@RestController
public class ImageController {




    @GetMapping("/image/setbrightness")
    public String base64(@RequestParam("base64code")  String base64code, @RequestParam("brightness")  int brightness) throws IOException {
//        decode
        byte[] imageBytes = Base64.getDecoder().decode(base64code);
            ByteArrayInputStream bis =new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            bis.close();

//        brightness
            for(int y = 0; y < bufferedImage.getHeight(); y++){
                for(int x = 0; x < bufferedImage.getWidth(); x++){
                    Color color = new Color(bufferedImage.getRGB(x,y));
                    int r = clamp((color.getRed() + brightness),0,255);
                    int g = clamp((color.getGreen() + brightness),0,255);
                    int b = clamp((color.getBlue() + brightness),0,255);
                    Color newColor = new Color(r, g, b);
                    bufferedImage.setRGB(x,y,newColor.getRGB());
                }
            }
//        encode
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"jpg",bos);
            byte[] imageByte = bos.toByteArray();
            return Base64.getEncoder().encodeToString(imageByte);



    }


    //7
    @GetMapping("/image/setbrightness2")
    public ResponseEntity<byte[]> base642(@RequestParam("base64code")  String base64code, @RequestParam("brightness")  int brightness) throws IOException {
//        decode
        byte[] imageBytes = Base64.getDecoder().decode(base64code);
        ByteArrayInputStream bis =new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(bis);
        bis.close();

//        brightness
        for(int y = 0; y < bufferedImage.getHeight(); y++){
            for(int x = 0; x < bufferedImage.getWidth(); x++){
                Color color = new Color(bufferedImage.getRGB(x,y));
                int r = clamp((color.getRed() + brightness),0,255);
                int g = clamp((color.getGreen() + brightness),0,255);
                int b = clamp((color.getBlue() + brightness),0,255);
                Color newColor = new Color(r, g, b);
                bufferedImage.setRGB(x,y,newColor.getRGB());
            }
        }
//        encode
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"jpg",bos);
        byte[] imageByte = bos.toByteArray();
        return ResponseEntity.ok(imageByte);



    }


}
