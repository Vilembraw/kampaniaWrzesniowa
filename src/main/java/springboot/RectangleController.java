package springboot;


import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addRect")
    public void addRect(@RequestBody Rectangle rectangle){
        listRect.add(rectangle);
    }
    //curl -X POST -H "Content-Type: application/json" -d "{\"x\":50, \"y\":70, \"width\":100, \"height\":100, \"color\":\"blue\"}" localhost:3001/addRect
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

//    curl localhost:3001/get/1
    @GetMapping("/get/{id}")
    public Rectangle getRectangle(@PathVariable int id){
        return listRect.get(id);
    }

//    curl -X PUT -H "Content-Type: application/json" -d "{\"x\":350, \"y\":270, \"width\":100, \"height\":100, \"color\":\"purple\"}" localhost:3001/put/1
    @PutMapping("/put/{id}")
    public void setRectangle(@PathVariable int id, @RequestBody Rectangle rectangle ){
        listRect.set(id,rectangle);
    }

//    curl -X DELETE localhost:3001/delete/2
    @DeleteMapping("/delete/{id}")
    public void deleteRectangle(@PathVariable int id){
        listRect.remove(id);
    }
}
