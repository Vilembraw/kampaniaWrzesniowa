package org.example;

import static org.example.Segment.getProstopadly;

public class Main {
    public static void main(String[] args) {
        Point point1 = new Point(100,100);
        Point point2 = new Point(50,150);
        Segment line = new Segment(point1,point2);
//        System.out.println(line.getLength());
//        System.out.println(line.toSvg());

//        Segment[] prostLine = getProstopadly(point1,line);
//        System.out.println(prostLine[0].toSvg());
//        System.out.println(prostLine[1].toSvg());

//        Point[] kwardratPoints = {new Point(100,100),new Point(200,100),new Point(200,200),new Point(100,200)} ;
//        Polygon kwadratPolygon = new Polygon(kwardratPoints);
//
//
//        SvgScene scene = new SvgScene();
//        scene.addPolygon(kwadratPolygon);
//        scene.save("scene.html");
////        System.out.println(kwadratPolygon.toSvg());
//
//
//        Style style = new Style("purple","black",1);



        Style style = new Style("purple","black",1);
        Style style1 = new Style("red","yellow",1);
        Polygon square = Polygon.square(line, style);
        Ellipse ellipse = new Ellipse(style1,new Point(300,300),100,50);
        SvgScene scene = new SvgScene();
        scene.addPolygon(square);
        scene.addPolygon(ellipse);
        scene.save("sceneE.html");

    }

}