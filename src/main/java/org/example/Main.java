package org.example;

import static org.example.Segment.getProstopadly;

public class Main {
    public static void main(String[] args) {
        Point point1 = new Point(150,150);
        Point point2 = new Point(300,200);
        Segment line = new Segment(point1,point2);
        System.out.println(line.getLength());
        System.out.println(line.toSvg());

        Segment[] prostLine = getProstopadly(point1,line);
        System.out.println(prostLine[0].toSvg());
        System.out.println(prostLine[1].toSvg());



    }

}