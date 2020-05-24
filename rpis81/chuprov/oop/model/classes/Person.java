package rpis81.chuprov.oop.model.classes;

import java.util.Objects;

public class Person implements Cloneable {

    private final static Person UNKNOWN_PERSON = new Person("", "");

    private String firstName;
    private String secondName;

    public Person(String firstName, String secondName) {
        this.firstName = Objects.requireNonNull(firstName, "Значение firstName не должно быть null");
        this.secondName = Objects.requireNonNull(secondName, "Значение secondName не должно быть null");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName, "Значение firstName не должно быть null");;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = Objects.requireNonNull(secondName, "Значение secondName не должно быть null");
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
