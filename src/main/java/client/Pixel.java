package client;

public class Pixel {

    int id;
    int x;

    int y;

    String hexColor;


    public int parseColor(){
        int color = Integer.parseInt(hexColor, 16);
        return color;
    }


    public Pixel(int id, int x, int y, String hexColor) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.hexColor = hexColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
}
