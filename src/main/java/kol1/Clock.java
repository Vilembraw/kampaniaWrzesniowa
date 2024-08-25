package kol1;

import java.time.LocalTime;

public abstract class Clock {
    public LocalTime time;


    public void setCurrentTime(){
        this.time = LocalTime.now();
    }

    public void setTime(int hour, int minutes, int secs){
        this.time = LocalTime.of(hour,minutes,secs);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", time.getHour()));
        sb.append(":");
        sb.append(String.format("%02d", time.getMinute()));
        sb.append(":");
        sb.append(String.format("%02d", time.getSecond()));
        return sb.toString();
    }
}
