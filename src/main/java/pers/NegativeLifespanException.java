package pers;

public class NegativeLifespanException extends Exception{
    public NegativeLifespanException(Person person) {
        super(String.format("%s, urodził(a) się, %s, później niż umarła, %s", person.getName(), person.getBirthDate(), person.getDeathDate()));
    }
}
