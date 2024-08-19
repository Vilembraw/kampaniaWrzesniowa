package org.example;

import static org.example.Segment.getProstopadly;

public class Polygon extends Shape {
    private Vec2[] points;



    public Polygon(Vec2[] points) {
        this.points = points;
    }

    @Override
    public String toSvg() {
        return this.toSvg("");
    }

    public String toSvg(String parametr){
        StringBuilder sb = new StringBuilder();
//        sb.append("<svg height=\"200\" width=\"300\" xmlns=\"http://www.w3.org/2000/svg\">");
        sb.append(" <polygon points=\"");
        for(Vec2 p : points){

        sb.append(p.x+","+p.y+" ");

        }
        sb.append("\" ");
        sb.append(parametr);
//        sb.append(style.toSvg());
//        sb.append("style=\"fill:lime;stroke:purple;stroke-width:3\" />");

//        sb.append("</svg>");
        return sb.toString();
    }

    public static Polygon square(Segment line, Style style){
        double middle = line.getLength() / 2;
        Vec2 middlePoints = new Vec2((line.getEnd().x + line.getStart().x)/2,(line.getEnd().y + line.getStart().y)/2);
        Segment newLine = new Segment(middlePoints,line.getEnd());
        Segment[] prostLine = getProstopadly(middlePoints,newLine);
        Vec2 vec2_1 = prostLine[0].getEnd();
        Vec2 vec2_2 = prostLine[1].getEnd();

        Vec2[] squareVec2s = {vec2_1,line.getEnd(), vec2_2, line.getStart()};
        return new Polygon(squareVec2s);

    }



}
