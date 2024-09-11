package org.example.kolos2021canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Odcinek {
    private double p1x;
    private double p1y;
    private double p2x;
    private double p2y;

    private String color;

    public double getP1x() {
        return p1x;
    }

    public double getP1y() {
        return p1y;
    }

    public double getP2x() {
        return p2x;
    }

    public double getP2y() {
        return p2y;
    }

    public String getColor() {
        return color;
    }

    //    public void setOffset(double offsetX, double offsetY){
//        this.p1x += offsetX;
//        this.p1y += offsetY;
//        this.p2x += offsetX;
//        this.p2y += offsetY;
//    }


    public Odcinek(double p1x, double p1y, double p2x, double p2y, String color) {
        this.p1x = p1x;
        this.p1y = p1y;
        this.p2x = p2x;
        this.p2y = p2y;
        this.color = color;
    }

//    public void draw(GraphicsContext graphicsContext) {
//        graphicsContext.setLineWidth(5);
////        System.out.println(color);
//        Color c = Color.web(color);
//        graphicsContext.setStroke(c);
//        graphicsContext.strokeLine(p1x,p1y,p2x,p2y);
//    }
}
