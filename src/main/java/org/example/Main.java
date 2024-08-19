package org.example;

public class Main {
    public static void main(String[] args) {
//        Vec2 vec2_1 = new Vec2(100,100);
//        Vec2 vec2_2 = new Vec2(50,150);
//        Segment line = new Segment(vec2_1, vec2_2);

//        System.out.println(line.getLength());
//        System.out.println(line.toSvg());

//        Segment[] prostLine = getProstopadly(vec2_1,line);
//        System.out.println(prostLine[0].toSvg());
//        System.out.println(prostLine[1].toSvg());

//        Vec2[] kwardratPoints = {new Vec2(100,100),new Vec2(200,100),new Vec2(200,200),new Vec2(100,200)} ;
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


//
//        Style style = new Style("purple","black",1);
//        Style style1 = new Style("red","yellow",1);
//        Polygon square = Polygon.square(line, style);
//        Ellipse ellipse = new Ellipse(style1,new Vec2(300,300),100,50);
//        SvgScene scene = new SvgScene();
//        scene.addPolygon(square);
//        scene.addPolygon(ellipse);
//        scene.save("sceneE.html");


//        SolidFieldPolygon poly = new SolidFieldPolygon(new Vec2[]{
//                new Vec2(30, 70),
//                new Vec2(60, 80),
//                new Vec2(50, 40)
//
//        }, "green");
//
//        SolidFieldEllipse ellipse = new SolidFieldEllipse(new Vec2(300,300),100,50,"red");
//        SvgScene scene = new SvgScene();
//        scene.addPolygon(poly);
//        scene.addPolygon(ellipse);
//        scene.save("scene1.html");


                Polygon polygon = new Polygon(new Vec2[]{
                new Vec2(30, 70),
                new Vec2(60, 80),
                new Vec2(50, 40)

        });


        Shape poly = new SolidFillShapeDecorator(polygon,"purple");

        Ellipse elipse = new Ellipse(new Vec2(300,300),100,50);
        Shape eli = new SolidFillShapeDecorator(elipse,"yellow");

        Ellipse elipse1 = new Ellipse(new Vec2(500,500),90,50);
        Shape eli1 = new StrokeShapeDecorator(elipse1,"red",20);

        TransformationDecorator.Builder builder = new TransformationDecorator.Builder();
        Shape s = builder
                .rotate(-50, new Vec2(0, 0))
                .translate(new Vec2(50, 60))
                .scale(new Vec2(1, 3))
                .build(new Ellipse(new Vec2(100, 100), 20, 40));
        Shape s1 = new SolidFillShapeDecorator(s,"green");


        SvgScene scene = SvgScene.getInstance();
        scene.addPolygon(poly);
        scene.addPolygon(eli);
        scene.addPolygon(eli1);
        scene.addPolygon(s1);
        scene.save("scene5.html");



    }
}