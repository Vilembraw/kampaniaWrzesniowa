package org.example;

public class SolidFillShapeDecorator extends ShapeDecorator{
    private String color;

    public SolidFillShapeDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }


    @Override
    public String toSvg(String parametr) {
        return super.toSvg(String.format("fill=\"%s\" %s ",color,parametr));
    }
}
