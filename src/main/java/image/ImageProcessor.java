package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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


    void setBrightnessAllThreads(int value) throws InterruptedException {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        Thread[] threads;
        threads = new Thread[threadsCount];
        int chunk = bufferedImage.getHeight()/threadsCount;
        for(int i = 0; i < threadsCount; i++){
            int begin = i*chunk;
            int end;
            if(i == threadsCount - 1){
                end = bufferedImage.getHeight();
            }else {
                end = (i+1)*chunk;
            }
            threads[i] = new Thread(new BrightnessWorker(begin, end, value, bufferedImage));
            threads[i].start();
        }

        for(int i = 0; i < threadsCount; i++){
            threads[i].join();
        }
    }


    void setBrightnessThreadsAmount(int value,int threadsCount) throws InterruptedException {
        Thread[] threads;
        threads = new Thread[threadsCount];
        int chunk = bufferedImage.getHeight() / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int begin = i * chunk;
            int end;
            if (i == threadsCount - 1) {
                end = bufferedImage.getHeight();
            } else {
                end = (i + 1) * chunk;
            }
            threads[i] = new Thread(new BrightnessWorker(begin, end, value, bufferedImage));
            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }
    }

    public void adjustBrightnessPoolThread(int value /*, int numThreads*/) {
        if (bufferedImage == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }
        int numThreads = bufferedImage.getHeight();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Dzielenie obrazu na części i przetwarzanie ich w osobnych wątkach
        int heightPerThread = 1;
        for (int i = 0; i < numThreads-1; i++) {
            int startY = i;
            int endY = i + 1;
            Runnable task = new BrightnessAdjustmentTask(startY, endY, value);
            executor.execute(task);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Wątek został przerwany: " + e.getMessage());
        }
    }

    private class BrightnessAdjustmentTask implements Runnable {
        private final int startY;
        private final int endY;
        private final int value;

        public BrightnessAdjustmentTask(int startY, int endY, int value) {
            this.startY = startY;
            this.endY = endY;
            this.value = value;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
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
}
