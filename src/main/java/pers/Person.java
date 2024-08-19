package pers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Person {
    String name;
    LocalDate birthDate;
    LocalDate deathDate;

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    //    Person mother;
//    Person father;


    public Person(String name, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }


    public static Person fromCsvLine(String csvLine){
        String[] parts = csvLine.split(",", -1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(parts[1],formatter);
        LocalDate deathDate;
        if(!parts[2].equals("")){
            deathDate = LocalDate.parse(parts[2],formatter);
        }
        else{
            deathDate = null;
        }
        return new Person (parts[0],birthDate,deathDate);
    }

    public static ArrayList<Person> fromCsv(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            ArrayList<Person> people = new ArrayList<>();
            try {
                String line;
                bufferedReader.readLine(); //header line
                while((line = bufferedReader.readLine()) != null){
//                    people.add(Person.fromCsvLine(line));
                    Person person = Person.fromCsvLine(line);
                    try {
                        person.validateLifespan();
                        person.validateAmbiguous(people);
                        people.add(person);
                    } catch (NegativeLifespanException | AmbiguousPersonException e) {
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
                bufferedReader.close();
                return people;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateLifespan() throws NegativeLifespanException {
        if(deathDate != null && deathDate.isBefore(birthDate)){
            throw new NegativeLifespanException(this);
        }
    }

    private void validateAmbiguous(ArrayList<Person> people) throws AmbiguousPersonException {
        for(Person person : people){
            if(person.getName().equals(getName())){
                throw new AmbiguousPersonException(person);
            }
        }
    }
}
