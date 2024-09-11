package org.example.kolos2021canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GCanvas{

    private double offsetX = 0;
    private double offsetY = 0;
    private GraphicsContext gc;

    public GCanvas(GraphicsContext gc) {
        this.gc = gc;

    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void drawBackground(){
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }


    public void drawLines(){
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawBackground();
        List<Odcinek> odcinekList = Client.getOdcinekList();
        for(Odcinek odcinek : odcinekList){
            draw(odcinek);
        }
    }


    public void draw(Odcinek odcinek) {
        gc.setLineWidth(5);
        Color c = Color.web(odcinek.getColor());
        gc.setStroke(c);
        gc.strokeLine(odcinek.getP1x()+offsetX, odcinek.getP1y()+offsetY, odcinek.getP2x()+offsetX, odcinek.getP2y()+offsetY);
    }



}
