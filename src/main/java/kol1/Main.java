package kol1;

import java.time.LocalTime;
import java.util.*;


import static kol1.City.generateAnalogClocksSvg;
import static kol1.CityComparator.worstTimezoneFitComparator;
import static kol1.DigitalClock.Type.DWUDZIESTO;

public class Main {
    public static void main(String[] args) {
//        DigitalClock clock24 = new DigitalClock(DWUDZIESTO);
//        clock24.setTime(13,0,0);
//        System.out.println(clock24.toString());
//        DigitalClock clock12 = new DigitalClock(DWUNASTO);
//        clock12.setTime(13,0,0);
//        System.out.println(clock12.toString());

        List<City> cities = new ArrayList<>();
        Map<String, City> cityMap = City.parseFile("strefy.csv");
        for (Map.Entry<String, City> line : cityMap.entrySet()) {
            cities.add(line.getValue());
//            System.out.println(line.getValue().getStolica());
        }

        LocalTime czas = LocalTime.of(12,30,45);

        AnalogClock clockerino = new AnalogClock(czas,cityMap.get("Lublin"));
        clockerino.toSvg("test1.svg");
        System.out.println(clockerino.toString());
        clockerino.setCity(cityMap.get("Nowy Jork"));
        clockerino.toSvg("test2.svg");
        System.out.println(clockerino.toString());
        generateAnalogClocksSvg(cities,clockerino);

//        List<City> entryList = new ArrayList<>(cityMap.values());
//
//        // Sort the list using the comparator
//        entryList.sort(worstTimezoneFitComparator(test));
//
//        Map<String, City> sortedMap = new LinkedHashMap<>();
//        for (City entry : entryList) {
//            sortedMap.put(entry.getStolica(), entry);
//        }
//
//        // Print the sorted map
//        System.out.println("Sorted map:");
//        for (Map.Entry<String, City> entry : sortedMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//        AnalogClock ag = new AnalogClock(test,cityLub);
//        ag.toSvg("zegar.html");
//        SecondHand sc = new SecondHand();
//        HourHand hh = new HourHand();
//        MinuteHand mh = new MinuteHand();
//        sc.setTime(test);
//        hh.setTime(test);
//        mh.setTime(test);
////
//        System.out.println(sc.toSvg());
//        System.out.println(hh.toSvg());
//        System.out.println(mh.toSvg());



    }
}
