package springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static java.lang.Math.clamp;

@Controller
public class ImageFormController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/index/upload")
    public String upload(@RequestParam("image") MultipartFile file, @RequestParam("brightness") int brightness, Model model){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "index";
        }

        try {
            byte[] bytes = file.getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            bis.close();

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
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            String base64EncodedImage = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", base64EncodedImage);
            return "image";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
            return "index";
        }

    }
}
