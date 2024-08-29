package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.clamp;

public class ImageProcessor {

    BufferedImage bufferedImage;

    void readImage(String path) throws IOException {

        File file = new File(path);
        bufferedImage = ImageIO.read(file);
    }

    void writeImage(String path) throws IOException {

        File file = new File(path);
        ImageIO.write(bufferedImage, "jpg", file);
    }

    void setBrightness(int value){
        for(int x = 0; x < bufferedImage.getWidth(); x++){
            for(int y = 0; y < bufferedImage.getHeight(); y++){
                int rgb = bufferedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                if(alpha == 0){
                    //transparent
                }else{
                Color color = new Color(bufferedImage.getRGB(x,y));

                int red = clamp((color.getRed() + value),0,255);
                int blue = clamp((color.getBlue() + value),0,255);
                int green = clamp((color.getGreen() + value),0,255);
                Color newColor = new Color(red, green, blue, alpha);
                bufferedImage.setRGB(x, y, newColor.getRGB());
                }
            }
        }
    }
}
