package org.example;

public abstract class ShapeDecorator implements Shape{
    private Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

//    @Override
//    public String toSvg() {
//        return this.decoratedShape.toSvg("");
//    }

    @Override
    public String toSvg(String parametr) {
        return this.decoratedShape.toSvg(parametr);
    }

}
