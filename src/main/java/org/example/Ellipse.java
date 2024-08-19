package org.example;

public class Ellipse implements Shape{
    private Vec2 center;
    private double rx;
    private double ry;

    public Ellipse(Vec2 center, double rx, double ry) {
//        super(style);
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }


//    @Override
//    public String toSvg() {
//        return this.toSvg("");
//    }

    @Override
    public String toSvg(String parametr) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ellipse " +
                "cx=\""+center.x+"\"" +
                " cy=\""+center.y+"\"" +
                " rx=\""+rx+"\"" +
                " ry=\""+ry+"\" " +
                parametr +
                " /> ");
        return sb.toString();
    }
}
