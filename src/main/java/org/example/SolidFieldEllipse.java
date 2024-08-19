package org.example;

public class SolidFieldEllipse extends Ellipse{
    private String color;

    public SolidFieldEllipse(Vec2 center, double rx, double ry, String color) {
        super(center, rx, ry);
        this.color = color;
    }


    @Override
    public String toSvg(String parametr) {
        return super.toSvg(String.format("fill=\"%s\" %s ",color,parametr));
    }




}
