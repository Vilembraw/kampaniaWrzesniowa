package kol1;

import java.time.LocalTime;

public class DigitalClock extends Clock{

    public enum Type{
        DWUNASTO,
        DWUDZIESTO

    }

    public Type type;

    public DigitalClock(LocalTime time, City city, Type type) {
        super(time, city);
        this.type = type;
    }

    @Override
    public String toString(){
        if(type == Type.DWUDZIESTO){
            return super.toString();
        }
        else{
            int hour = time.getHour();
            String dopisek;
            if(hour < 12){
                dopisek = "AM";
            }
            else {
                hour = hour - 12;
                dopisek = "PM";
            }
            return String.format("%d:%02d:%02d %s",hour, time.getMinute(),time.getSecond(),dopisek);
        }
    }
}
