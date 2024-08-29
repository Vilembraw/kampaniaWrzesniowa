package image;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ImageProcessor imageProcessor = new ImageProcessor();
            imageProcessor.readImage("img.jpg");

            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightness(100);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime);
            }
            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightnessAllThreads(100);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime);
            }

            imageProcessor.writeImage("obrazek.jpeg");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
