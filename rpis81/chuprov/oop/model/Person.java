package rpis81.chuprov.oop.model;

public class Person {

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
        StringBuilder builder = new StringBuilder("Person info: ");
        builder.append("first name - ").append(firstName).append(", second name - ").append(secondName).append("\n");
        return builder.toString();
    }
}