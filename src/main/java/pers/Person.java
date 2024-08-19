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

    ArrayList<Person> parents = new ArrayList<>();
    static ArrayList<Person> people = new ArrayList<>();

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


    public ArrayList<Person> getParents() {
        return parents;
    }



    public Person(String name, LocalDate birthDate, LocalDate deathDate, ArrayList<Person> parents) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.parents = parents;
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
        ArrayList<Person> parents = new ArrayList<>();
        if(!parts[3].equals("")){
            Person mother = findPerson(parts[3].trim());
            if(mother != null)
            {
                parents.add(mother);
            }
        }
        if(!parts[4].equals("")){
            Person father = findPerson(parts[4].trim());
            if(father != null)
            {
                parents.add(father);
            }
        }
        return new Person (parts[0],birthDate,deathDate,parents);
    }

    public static ArrayList<Person> fromCsv(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

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

    public static Person findPerson(String name){
        for(Person p: people){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
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
