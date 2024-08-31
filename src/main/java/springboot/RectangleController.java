package springboot;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {

    private List<Rectangle> listRect = new ArrayList<>();


    //1
    @GetMapping
    public String hello(){
        return "Hello world";
    }

    //2
    @GetMapping("/rect")
    public Rectangle rect(){
        Rectangle newRect = new Rectangle(3,5,30,20,"red");
        return newRect;
    }

    //3
    @GetMapping("/add")
    public void addRect(){
        listRect.add(new Rectangle(20,10,30,50,"green"));
    }

    @GetMapping("/lista")
    public List<Rectangle> returnRec(){
        return listRect;
    }

    @GetMapping("/toSvg")
    public String toSvg(){
        StringBuilder sb = new StringBuilder();
        sb.append("<svg width=\"300\" height=\"130\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        for(Rectangle rect : listRect){
        sb.append(
                String.format(
                "<rect " +
                "width=\"%d\" " +
                "height=\"%d\" " +
                "x=\"%d\" " +
                "y=\"%d\" " +
                "fill=\"%s\" " +
                "/>\n",rect.getWidth(),rect.getHeight(),rect.getX(),rect.getY(),rect.getColor()));
        }
        sb.append("</svg>\n");
        return sb.toString();
    }


}
