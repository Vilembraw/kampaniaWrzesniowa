package org.example;

public class Ellipse extends Shape{
    private Point center;
    private double rx;
    private double ry;

    public Ellipse(Style style, Point center, double rx, double ry) {
        super(style);
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ellipse " +
                "cx=\""+center.x+"\"" +
                " cy=\""+center.y+"\"" +
                " rx=\""+rx+"\"" +
                " ry=\""+ry+"\"" +
                style.toSvg() +
                " />");
        return sb.toString();
    }
}
