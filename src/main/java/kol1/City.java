package kol1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class City {
    private String stolica;
    private double strefa;
    private String szerokosc;
    private String dlugosc;

    public int cos;


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

}
