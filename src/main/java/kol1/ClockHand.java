package kol1;

import java.time.LocalTime;

public abstract class ClockHand{

    LocalTime time;
    public abstract void setTime(LocalTime time);
    public abstract String toSvg();
}
