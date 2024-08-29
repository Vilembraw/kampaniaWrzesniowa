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

            {
                int[] redHistogram = imageProcessor.calculateChannelHistogram(3);
                // Wyświetlenie histogramu
                if (redHistogram != null) {
                    for (int i = 0; i < redHistogram.length; i++) {
                        System.out.println("Wartość: " + i + ", Liczba pikseli: " + redHistogram[i]);
                    }
                }

                ImageProcessor handler = new ImageProcessor();
                handler.readImage("img.jpg");
                int[] histogram = new int[256];
                for (int i = 0; i < 256; i++) {
                    histogram[i] = (int) (Math.random() * 500); // Losowe wartości do histogramu
                }

                handler.generateHistogramImage(histogram, "Histogram.png");
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
