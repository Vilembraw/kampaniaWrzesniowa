package springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class ImageFormController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/index")
    public String upload(@RequestParam("image") MultipartFile file, Model model){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "index";
        }

        try {
            byte[] bytes = file.getBytes();
            String base64EncodedImage = Base64.getEncoder().encodeToString(bytes);
            model.addAttribute("image", base64EncodedImage);
            return "image";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
            return "index";
        }

    }
}
