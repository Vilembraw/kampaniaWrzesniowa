package pers;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static pers.Person.fromBinaryFile;
import static pers.Person.toBinaryFile;

public class Main {
    public static void main(String[] args) {
            ArrayList<Person> people = Person.fromCsv("family.csv");
        for (Person person : people) {
            ArrayList<Person> parents = person.getParents();
            if(parents.size() == 0){
                System.out.println("dziecko: " + person.getName() + "rodzic: brak");
            }
            String text = "dziecko: " + person.getName();
            for(int i = 0; i < parents.size(); i++){
                text += " rodzic: " + parents.get(i).getName();
                System.out.println(text);
            }

        }
        try {
            sleep(1000);
            toBinaryFile("out.bin");
            fromBinaryFile("out.bin");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
