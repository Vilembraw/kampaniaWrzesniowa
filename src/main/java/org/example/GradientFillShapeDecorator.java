package org.example;

import java.util.ArrayList;
import java.util.Locale;

public class GradientFillShapeDecorator extends ShapeDecorator{
    private static int index;
    private  ArrayList<Double> offsets = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();

    public GradientFillShapeDecorator(Shape decoratedShape, int index, ArrayList<Double> offsets, ArrayList<String> colors) {
        super(decoratedShape);
        String indexG = String.format("\t<linearGradient id=\"g%d\" >\n", index);
        for(int i = 0; i < offsets.size(); i++) {
            indexG += String.format(Locale.US,"\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n",offsets.get(i),colors.get(i));
        }
        indexG += "\t</linearGradient>";

        this.index =  SvgScene.getInstance().addFilter(
                indexG
        );

        this.offsets = new ArrayList<>(offsets);
        this.colors = new ArrayList<>(colors);
    }

    public static class Builder {
        private  ArrayList<Double> offsets = new ArrayList<>();
        private ArrayList<String> colors = new ArrayList<>();
        private Shape decoratedShape;

//        public Builder setShape(Shape shape) {
//            this.shape = shape;
//            return this;
//        }

        public Builder addStop(double offset, String color) {
            this.offsets.add(offset);
            this.colors.add(color);
            return this;
        }

        public GradientFillShapeDecorator build(Shape decoratedShape) {
            return new GradientFillShapeDecorator(decoratedShape, index,offsets, colors);
        }
    }

    @Override
    public String toSvg(String parametr) {
        String filterWithId = String.format("fill=\"url(#g%d)\" %s ", index, index, parametr);
        return decoratedShape.toSvg(filterWithId);
    }
}
