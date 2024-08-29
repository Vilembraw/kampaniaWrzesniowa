package image;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ImageProcessor imageProcessor = new ImageProcessor();
            imageProcessor.readImage("img.jpg");
            imageProcessor.setBrightness(50);
            imageProcessor.writeImage("img1_old.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
