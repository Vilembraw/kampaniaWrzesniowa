package pers;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
            ArrayList<Person> people = Person.fromCsv("family.csv");
        for (Person person : people) {
            // Print the name of the person
//            System.out.println("Person: " + person.getName());

            // Get and print parents information
            ArrayList<Person> parents = person.getParents();
            if (parents != null && !parents.isEmpty()) {
                // Check if there are parents and print their details
                for (Person parent : parents) {
                    System.out.println("Parent: " + parent.getBirthDate());
                }
            } else {
                // No parents information available
                System.out.println("No parents information available.");
            }
    }
}
}
