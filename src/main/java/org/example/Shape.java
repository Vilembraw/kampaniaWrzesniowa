package org.example;

public abstract class Shape {
    protected Style style;

    public Shape(Style style) {
        this.style = style;
    }

    public Shape(){
        this.style = new Style("transparent","black",1);
    }


    public abstract String toSvg();

}
