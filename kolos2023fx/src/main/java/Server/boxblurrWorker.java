package Server;

import java.awt.*;
import java.awt.image.BufferedImage;

public class boxblurrWorker implements Runnable{
    private int begin,end;

    private int radius;
    private BufferedImage result;

    private BufferedImage image;


    public boxblurrWorker(int begin, int end, BufferedImage result, BufferedImage image, int radius) {
        this.begin = begin;
        this.end = end;
        this.result = result;
        this.image = image;
        this.radius = radius;
    }

    @Override
    public void run() {
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                if(x < radius || y < radius ||  x > image.getWidth() - 1 - radius ||  y > image.getHeight() - 1 - radius){
                    //krawedzie
                    Color color = new Color(0,0,0, 0);
                    result.setRGB(x,y,color.getRGB());
                }
                else{
//                        System.out.println("pixel");
                    int[] avgColor = {0, 0, 0};
                    int count = 0;

                    for(int dy = -radius; dy <= radius; dy++) {
                        for(int dx = -radius; dx <= radius; dx++){
                            int nx = x + dx;
                            int ny = y + dy;

                            if (nx >= 0 && nx < image.getWidth() && ny >= 0 && ny < image.getHeight()){
                                Color color = new Color(image.getRGB(nx,ny));
                                avgColor[0] += color.getRed();
                                avgColor[1] += color.getGreen();
                                avgColor[2] += color.getBlue();

                                count++;
                            }
                        }
                    }

                    avgColor[0] /= count;
                    avgColor[1] /= count;
                    avgColor[2] /= count;

                    Color avg = new Color(avgColor[0],avgColor[1],avgColor[2]);
                    result.setRGB(x,y,avg.getRGB());
                }
            }

        }
    }
}
