package org.example;

public class Segment {
    private Vec2 start;
    private Vec2 end;

    public Vec2 getStart() {
        return start;
    }

//    public void setPoint1(Vec2 point1) {
//        this.point1 = point1;
//    }

    public Vec2 getEnd() {
        return end;
    }

//    public void setPoint2(Vec2 point2) {
//        this.point2 = point2;
//    }


    public Segment(Vec2 start, Vec2 end) {
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


    public static Segment[] getProstopadly(Vec2 vec2, Segment line){
        int dx = line.getEnd().x - line.getStart().x ;
        int dy = line.getEnd().y - line.getStart().y;



        return new Segment[]{
                new Segment(vec2,new Vec2(vec2.x - dy, vec2.y + dx)),
                new Segment(vec2,new Vec2(vec2.x + dy, vec2.y - dx))
        };
    }
}
