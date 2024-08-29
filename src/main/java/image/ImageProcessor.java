package image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

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
}
