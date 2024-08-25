package kol1;

import java.time.LocalTime;

public abstract class Clock {
    public LocalTime time;

    private City city;

    public void setCity(City cityT){

        double temp = cityT.getStrefa() - this.city.getStrefa();
        System.out.println(cityT.getStolica());
        System.out.println(this.city.getStolica());
        System.out.println(String.format("Roznica: %f",temp));
        this.time = time.plusHours((long)temp);
        this.city = cityT;
    }



    public Clock(LocalTime time, City city) {
        this.time = time;
        this.city = city;
    }

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
