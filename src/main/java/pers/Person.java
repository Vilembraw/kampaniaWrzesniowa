package pers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    String name;
    LocalDate brithDate;
    LocalDate deathDate;

//    Person mother;
//    Person father;


    public Person(String name, LocalDate brithDate, LocalDate deathDate) {
        this.name = name;
        this.brithDate = brithDate;
        this.deathDate = deathDate;
    }


    public Person fromCsvLine(String csvLine){
        String[] parts = csvLine.split(",", -1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(parts[1],formatter);
        if(!parts[2].equals("")){
            deathDate = LocalDate.parse(parts[2],formatter);
        }
        else{
            deathDate = null;
        }
        return new Person (parts[0],birthDate,deathDate);
    }

    public String fromCsv(String path){

    };
}
