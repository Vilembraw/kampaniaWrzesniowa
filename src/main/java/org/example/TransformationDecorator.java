package org.example;

public class TransformationDecorator extends ShapeDecorator {
    private boolean translate, rotate, scale;
    private Vec2 translateVector, rotateCenter, scaleVector;
    private double rotateAngle;


    public TransformationDecorator(Shape shape, boolean translate, boolean rotate, boolean scale, Vec2 translateVector, Vec2 rotateCenter, Vec2 scaleVector, double rotateAngle) {
        super(shape);
        this.translate = translate;
        this.rotate = rotate;
        this.scale = scale;
        this.translateVector = translateVector;
        this.rotateCenter = rotateCenter;
        this.scaleVector = scaleVector;
        this.rotateAngle = rotateAngle;
    }

    public static class Builder{
        private boolean translate=false, rotate=false, scale=false;
        private Vec2 translateVector, rotateCenter, scaleVector;
        private double rotateAngle;

        private Shape shape;

        public Builder scale(Vec2 scaleVector){
            this.scaleVector = scaleVector;
            this.scale = true;
            return this;
        }

        public Builder rotate(double rotateAngle, Vec2 rotateCenter){
            this.rotateAngle = rotateAngle;
            this.rotateCenter = rotateCenter;
            this.rotate = true;
            return this;
        }

        public Builder translate(Vec2 translateVector){
            this.translateVector = translateVector;
            this.translate = true;
            return this;
        }


        public TransformationDecorator build(Shape shape){
            return new TransformationDecorator(shape,  translate,  rotate,  scale,  translateVector,  rotateCenter,  scaleVector,  rotateAngle);
        }
    }

    @Override
    public String toSvg(String parametr) {
        StringBuilder sb = new StringBuilder();
        sb.append("transform=\"");
        if(rotate)
            sb.append(String.format(" rotate(%f %d %d) ", rotateAngle, rotateCenter.x, rotateCenter.y));
        if(translate)
            sb.append(String.format(" translate(%d %d) ", translateVector.x, translateVector.y));
        if (scale)
            sb.append(String.format(" scale(%d %d) ", scaleVector.x, scaleVector.y));
        sb.append("\" ");

        return super.toSvg(String.format("%s %s", parametr, sb));
    }
}

