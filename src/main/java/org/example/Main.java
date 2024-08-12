package org.example;

public class Main {
    public static void main(String[] args) {
        Point point1 = new Point(150,150);
        Point point2 = new Point(300,200);
        Segment line = new Segment(point1,point2);
        System.out.println(line.getLength());
        System.out.println(line.toSvg());

        Segment prostLine = getProstopadly(point1,line);
        System.out.println(prostLine.getLength());
        System.out.println(prostLine.toSvg());

    }
    public static Segment getProstopadly(Point point, Segment line){
        int dx = line.getEnd().x - line.getStart().x ;
        int dy = line.getEnd().y - line.getStart().y;


        Segment newLine = new Segment(point,new Point(point.x - dy, point.y + dx));
        return newLine;
    }
}