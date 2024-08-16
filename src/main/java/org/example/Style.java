package org.example;

public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final double strokeWidth;

    public Style(String fillColor, String strokeColor, double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public String toSvg(){
        StringBuilder sb = new StringBuilder();
        sb.append("style=" +
                "\"" +
                "fill:"+fillColor+";" +
                "stroke:"+strokeColor+";" +
                "stroke-width:"+strokeWidth +
                "\" " +
                "/>");
        return sb.toString();
    }
}
