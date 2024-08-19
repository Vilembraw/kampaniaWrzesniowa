package pers;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
            ArrayList<Person> people = Person.fromCsv("family.csv");
            for(Person person : people) {
                System.out.println(person.name);
            }
    }
}
