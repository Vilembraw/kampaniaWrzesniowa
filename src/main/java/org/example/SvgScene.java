package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SvgScene {
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void addPolygon(Polygon polygon){
        shapes.add(polygon);
    }
    public void save(String path){
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write("<HTML>");
            fileWriter.write("<body>");
            fileWriter.write("<svg height=\"200\" width=\"300\" xmlns=\"http://www.w3.org/2000/svg\">");
            for(Shape polygon : shapes)
                fileWriter.write("\t" + polygon.toSvg() + "\n");
            fileWriter.write("</svg>");
            fileWriter.write("</body>");
            fileWriter.write("</HTML>");
            fileWriter.close();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.err.println("cant write" + path);
        }
    }
}
