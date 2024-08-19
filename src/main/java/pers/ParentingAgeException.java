package pers;

public class ParentingAgeException extends Exception{
    public ParentingAgeException(Person person) {
        super(String.format("%s, Wiek rodzica??? ", person.getName()));
    }
}
