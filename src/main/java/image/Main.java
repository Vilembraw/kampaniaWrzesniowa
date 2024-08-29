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
                imageProcessor.writeImage("img\\obrazek1.jpg");
            }
            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightnessAllThreads(100);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime);
                imageProcessor.writeImage("img\\obrazek2.jpg");
            }

            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightnessThreadsAmount(100,10);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime);
                imageProcessor.writeImage("img\\obrazek3.jpg");
            }

            {
                long startTime = System.currentTimeMillis();
                imageProcessor.adjustBrightnessPoolThread(100);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime);
                imageProcessor.writeImage("img\\obrazek4.jpg");
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
