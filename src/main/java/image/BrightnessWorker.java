package image;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.clamp;

public class BrightnessWorker implements Runnable {
    private int begin,end;
    private int value;
    private BufferedImage bufferedImage;


    public BrightnessWorker(int begin, int end, int value, BufferedImage bufferedImage) {
        this.begin = begin;
        this.end = end;
        this.value = value;
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void run() {
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
