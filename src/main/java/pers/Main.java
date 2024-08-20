package pers;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static pers.Person.fromBinaryFile;
import static pers.Person.toBinaryFile;

public class Main {
    public static void main(String[] args) {
//            ArrayList<Person> people = Person.fromCsv("family.csv");
//        for (Person person : people) {
//            ArrayList<Person> parents = person.getParents();
//            if(parents.size() == 0){
//                System.out.println("dziecko: " + person.getName() + "rodzic: brak");
//            }
//            String text = "dziecko: " + person.getName();
//            for(int i = 0; i < parents.size(); i++){
//                text += " rodzic: " + parents.get(i).getName();
//                System.out.println(text);
//            }
//
//        }
//        try {
//            sleep(1000);
//            toBinaryFile("out.bin");
//            fromBinaryFile("out.bin");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        PlantUMLRunner.setJarPath("./plantuml-1.2024.6.jar");
//        PlantUMLRunner.generate("@startuml\n" +
//                        "Class11 <|.. Class12\n" +
//                        "Class13 --> Class14\n" +
//                        "Class15 ..> Class16\n" +
//                        "Class17 ..|> Class18\n" +
//                        "Class19 <--* Class20\n" +
//                        "@enduml\n",
//                "image_output",
//                "test"
//        );

        ArrayList<Person> people = Person.fromCsv("family.csv");
        for(Person person : people){
            //System.out.println(person.generateTree());
            PlantUMLRunner.generate(person.generateTree(),"image_output", person.getName());
        }

    }
}
