package kol1;

import java.time.LocalTime;

public class MinuteHand extends ClockHand{

    public double kat;

    @Override
    public void setTime(LocalTime time) {
        this.kat = time.getMinute() * 6;
    }

    @Override
    public String toSvg() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<!-- Minuty -->\n" +
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-70\" stroke=\"black\" stroke-width=\"2\" " +
                        "transform=\"rotate("+this.kat+")\"" +
                        "/>\n" +
                "\n");

        return stringBuilder.toString();
    }
}
