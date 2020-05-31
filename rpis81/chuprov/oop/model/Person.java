package rpis81.chuprov.oop.model;

import java.util.Objects;

public class Person implements Cloneable {

    private String firstName;
    private String secondName;
    private final static Person UNKNOWN_PERSON = new Person("", "");

    public Person(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public static Person getUnknownPerson() {
        return UNKNOWN_PERSON;
    }

    @Override
    public String toString() {
        return String.format("%s %s", secondName, firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return Objects.equals(firstName, other.firstName) && Objects.equals(secondName, other.secondName);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}