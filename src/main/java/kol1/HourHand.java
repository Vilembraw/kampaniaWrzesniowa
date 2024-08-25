package kol1;

import java.time.LocalTime;

public class HourHand extends ClockHand{

    public double kat;

    @Override
    public void setTime(LocalTime time) {
        this.kat = (time.getHour() * 30) + (double) time.getMinute() /2;
    }

    @Override
    public String toSvg() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<!-- Godziny -->\n" +
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-50\" stroke=\"black\" stroke-width=\"4\" " +
                        "transform=\"rotate("+this.kat+")\"  />\n");
        return stringBuilder.toString();
    }
}
