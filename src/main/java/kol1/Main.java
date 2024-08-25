package kol1;

import java.time.LocalTime;
import java.util.Map;


import static kol1.DigitalClock.Type.DWUDZIESTO;
import static kol1.DigitalClock.Type.DWUNASTO;

public class Main {
    public static void main(String[] args) {
//        DigitalClock clock24 = new DigitalClock(DWUDZIESTO);
//        clock24.setTime(13,0,0);
//        System.out.println(clock24.toString());
//        DigitalClock clock12 = new DigitalClock(DWUNASTO);
//        clock12.setTime(13,0,0);
//        System.out.println(clock12.toString());

        LocalTime time = LocalTime.now();
        time.plusHours(1);
        System.out.println(time);

        Map<String, City> cityMap = City.parseFile("strefy.csv");
        for (Map.Entry<String, City> line : cityMap.entrySet()) {
            System.out.println(line.getValue());
        }
    }
}
