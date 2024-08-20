package pers;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

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

//        return new Person (parts[0],birthDate,deathDate,parents);
        Person person = new Person (parts[0],birthDate,deathDate,parents);
        try {
            person.validateParentAge();
        } catch (ParentingAgeException e) {
            System.err.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to proceed with this case? (Y/N): ");
            String userInput = scanner.nextLine();
            if (!userInput.equalsIgnoreCase("Y")) {
                System.out.println("Person not added.");
                return null;
            }
        }
        return person;
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

    public int getAge() {
        if (birthDate == null){
            return -1;
        }
        LocalDate currentDate = LocalDate.now();
        return currentDate.minusYears(birthDate.getYear()).getYear();
    }

    private void validateParentAge() throws ParentingAgeException {
        int len = parents.size();
        for(int i = 0; i < len; i++){
            if(parents.get(i).getAge() < this.getAge() || parents.get(i).getAge() < 15){
                throw new ParentingAgeException(this);
            }
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

    public static void toBinaryFile(String path){
        File file = new File("out.bin");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            for (int i = 0; i < people.size(); i++)
                dos.writeUTF(people.get(i).getName());
            fos.close();
            dos.close();
        } catch (IOException e) {
            System.err.println("Cannot access: ");
        }
    }

    public static void fromBinaryFile(String path){
        File file = new File("out.bin");
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            int size = (int) (file.length() / Integer.BYTES);
            String[] data = new String[size];
//            for (int i = 0; i < size; i++)
//                data[i] = dis.readUTF();
//            for(String value: data)
//                System.out.println(value);

            while (dis.available() > 0) { // Continue reading until the end of the file
                String name = dis.readUTF(); // Read each name as a UTF string
                System.out.println(name);
            }

            fis.close();
            dis.close();
        } catch (IOException e) {
            System.err.println("Cannot access: ");
        }
    }


    public String generateTree(){
        String result="@startuml\n%s\n%s\n@enduml";
        Function<Person,String> objectName = person -> person.getName().replaceAll(" ","");
        Function<Person,String> objectLine = person -> String.format("object \"%s\" as %s",person.getName(),objectName.apply(person));

         StringBuilder objects=new StringBuilder();
        StringBuilder relations=new StringBuilder();
        objects.append(objectLine.apply(this)).append("\n");
        parents.forEach(parent->{
            objects.append(objectLine.apply(parent)).append("\n");
            relations.append(String.format("%s <-- %s\n",objectName.apply(parent),objectName.apply(this)));
        });
        result=String.format(result,objects,relations);
        return result;
    }
}
