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


    public int[] calculateChannelHistogram(int channel) {
        //(0 dla czerwonego, 1 dla zielonego, 2 dla niebieskiego)
        if (bufferedImage == null) {
            System.out.println("Brak wczytanego obrazu.");
            return null;
        }

        int[] histogram = new int[256];
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Dzielenie obrazu na części i przetwarzanie ich w osobnych wątkach
        int heightPerThread = bufferedImage.getHeight() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startY = i * heightPerThread;
            int endY = (i == numThreads - 1) ? bufferedImage.getHeight() : (i + 1) * heightPerThread;
            Runnable task = new HistogramCalculationTask(channel, startY, endY, histogram);
            executor.execute(task);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Wątek został przerwany: " + e.getMessage());
        }

        return histogram;
    }

    // Klasa wewnętrzna reprezentująca zadanie obliczania histogramu dla wybranego kanału w jednym wątku
    private class HistogramCalculationTask implements Runnable {
        private final int channel;
        private final int startY;
        private final int endY;
        private final int[] histogram;

        public HistogramCalculationTask(int channel, int startY, int endY, int[] histogram) {
            this.channel = channel;
            this.startY = startY;
            this.endY = endY;
            this.histogram = histogram;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int rgb = bufferedImage.getRGB(x, y);
                    int color = (rgb >> (channel * 8)) & 0xFF; // Wybór kanału koloru
                    synchronized (histogram) {
                        histogram[color]++;
                    }
                }
            }
        }
    }

    // Metoda do generowania obrazu przedstawiającego wykres histogramu
    public void generateHistogramImage(int[] histogram, String outputPath) {
        if (histogram == null || histogram.length != 256) {
            System.out.println("Niepoprawny histogram.");
            return;
        }

        int width = 256; // Szerokość obrazu
        int height = 200; // Wysokość obrazu
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = histogramImage.getGraphics();

        // Wypełnienie tła na biało
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Znalezienie maksymalnej wartości w histogramie
        int maxCount = 0;
        for (int count : histogram) {
            maxCount = Math.max(maxCount, count);
        }

        // Rysowanie słupków na podstawie histogramu
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((double) histogram[i] / maxCount * (height - 20)); // Wysokość słupka
            int x = i;
            int y = height - 10 - barHeight; // Górna krawędź słupka
            graphics.drawLine(x, height - 10, x, y); // Linia pionowa
        }

        // Zapisanie obrazu histogramu do pliku
        try {
            File outputFile = new File(outputPath);
            ImageIO.write(histogramImage, "png", outputFile);
            System.out.println("Obraz histogramu został wygenerowany i zapisany pomyślnie.");
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas zapisywania obrazu histogramu: " + e.getMessage());
        }
    }
}
