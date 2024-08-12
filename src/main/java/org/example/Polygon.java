package org.example;

public class Polygon {
    private Point[] points;

    public Polygon(Point[] points) {
        this.points = points;
    }

    public String toSvg(){
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"200\" width=\"300\" xmlns=\"http://www.w3.org/2000/svg\">");
        sb.append(" <polygon points=\"");
        for(Point p : points){

        sb.append(p.x+","+p.y+" ");

        }
        sb.append("\" style=\"fill:lime;stroke:purple;stroke-width:3\" />");
        sb.append("</svg>");
        return sb.toString();
    }
}
