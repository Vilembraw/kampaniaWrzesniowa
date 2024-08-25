package kol1;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String stolica;
    private double strefa;
    private String szerokosc;
    private String dlugosc;




    public String getStolica() {
        return stolica;
    }

    public double getStrefa() {
        return strefa;
    }

    public String getSzerokosc() {
        return szerokosc;
    }

    public String getDlugosc() {
        return dlugosc;
    }

    public City(String stolica, double strefa, String szerokosc, String dlugosc) {
        this.stolica = stolica;
        this.strefa = strefa;
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;
    }

    private static City parseLine(String line){
        String[] parts = line.split(",");
        if(parts.length == 4){
            double temp = Double.parseDouble(parts[1].trim());
            return new City(parts[0],temp,parts[2],parts[3]);
        }
        else{
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
    }

    public static Map<String,City> parseFile(String path){
        Map<String, City> cityMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            bufferedReader.readLine();
            String line;
            while((line = bufferedReader.readLine()) != null){
                City city = parseLine(line);
                cityMap.put(city.getStolica(),city);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityMap;
    }


    public LocalTime localMeanTime(LocalTime zgodnyCzas){
        String dlugoscTemp = getDlugosc().trim();
        String[] parts = dlugoscTemp.split(" ");
//        System.out.println(parts[0].trim());
//        System.out.println(parts[1].trim());
        double dlugoscValue = Double.parseDouble(parts[0]);
        double roznica = dlugoscValue * 4;
        double minuty = roznica%60;
        double godziny = (roznica - minuty)/60;
        double roznicaWSekundach = roznica * 60;
        double sekundy = roznicaWSekundach % 60;
        LocalTime meanTime = zgodnyCzas;

//        System.out.println(String.format("g %f \t,m %f \t,s %f \t",godziny,minuty,sekundy));

        if(parts[1].equals("E") ){
            meanTime = meanTime.plusHours((long)godziny);
            meanTime = meanTime.plusMinutes((long)minuty);
            meanTime = meanTime.plusSeconds((long)sekundy);
//            System.out.println(String.format("g %d \t,m %d \t,s %d \t",(long)godziny,(long)minuty,(long)sekundy));

        }

        if(parts[1].equals("W")){
            meanTime.minusHours((long)godziny);
            meanTime.minusMinutes((long)minuty);
            meanTime.minusSeconds((long)sekundy);



        }
        return meanTime;
    }

//    public static City worstTimezoneFit(City city1, City city2, LocalTime time){
//        Duration duration1 = Duration.between(city1.localMeanTime(time), time).abs();
//        Duration duration2 = Duration.between(city2.localMeanTime(time), time).abs();
//
//
//        // Compare the durations
//        if (duration1.compareTo(duration2) > 0) {
////            System.out.println("City1's local mean time is further from the reference time.");
//            return city1;
//        } else if (duration1.compareTo(duration2) < 0) {
////            System.out.println("City2's local mean time is further from the reference time.");
//            return city2;
//        } else {
//            System.out.println("Both cities' local mean times are equally distant from the reference time.");
//            return city1;
//        }
//    }


    public static void generateAnalogClocksSvg(List<City> cities, AnalogClock clock){
        System.out.println(clock.toString());
        File directory = new File("dupa");
        City firstCity = clock.city;
        directory.mkdirs();

        for(City c : cities){
            clock.setCity(c);
            String name = directory + "/" + c.getStolica() + ".svg";
            clock.toSvg(name);
            clock.setCity(firstCity);
        }
    }
}

class CityComparator {
    public static Comparator<City> worstTimezoneFitComparator(LocalTime referenceTime) {
        return (city1, city2) -> {
            Duration duration1 = Duration.between(city1.localMeanTime(referenceTime), referenceTime).abs();
            Duration duration2 = Duration.between(city2.localMeanTime(referenceTime), referenceTime).abs();
            return duration1.compareTo(duration2);
        };
    }
}