package org.example;

public class Segment {
    private Point start;
    private Point end;

    public Point getStart() {
        return start;
    }

//    public void setPoint1(Point point1) {
//        this.point1 = point1;
//    }

    public Point getEnd() {
        return end;
    }

//    public void setPoint2(Point point2) {
//        this.point2 = point2;
//    }


    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public double getLength(){
        return Math.sqrt(Math.pow(start.x - end.x,2) + Math.pow(start.y - end.y,2));

    }
/*


<svg height="200" width="300" xmlns="http://www.w3.org/2000/svg">
  <line x1="0" y1="0" x2="300" y2="200" style="stroke:red;stroke-width:2" />
</svg>


*/
    public String toSvg(){
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"200\" width=\"300\" xmlns=\"http://www.w3.org/2000/svg\">");
        sb.append("<line " +
                "x1=\"" + start.x + "\" " +
                "y1=\" " + start.y + "\" " +
                "x2=\" " + end.x + "\" " +
                "y2=\" " + end.y + "\" " +
                "style=\"stroke:red;stroke-width:2\" />");
        sb.append("</svg>");
        return sb.toString();
    }


    public static Segment[] getProstopadly(Point point, Segment line){
        int dx = line.getEnd().x - line.getStart().x ;
        int dy = line.getEnd().y - line.getStart().y;



        return new Segment[]{
                new Segment(point,new Point(point.x - dy, point.y + dx)),
                new Segment(point,new Point(point.x + dy, point.y - dx))
        };
    }
}
