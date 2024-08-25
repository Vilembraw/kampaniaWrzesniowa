package kol1;

import java.time.LocalTime;

public class SecondHand extends ClockHand{

    public double kat;

    @Override
    public void setTime(LocalTime time) {
        this.kat = time.getSecond() * 6;
    }

    @Override
    public String toSvg() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<!-- Sekundy -->\n" +
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-80\" stroke=\"red\" stroke-width=\"1\" " +
                "transform=\"rotate("+this.kat+")\" />\n");
        return stringBuilder.toString();
    }
}
