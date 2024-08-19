package org.example;

public class SolidFieldPolygon extends Polygon{
    private String color;


    @Override
    public String toSvg(String parametr) {
        return super.toSvg(String.format("fill=\"%s\" %s ",color,parametr));
    }

    public SolidFieldPolygon(Vec2[] points, String color) {
        super(points);
        this.color = color;
    }

}
