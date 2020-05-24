package rpis81.chuprov.oop.model.interfaces;

import rpis81.chuprov.oop.model.classes.Person;
import rpis81.chuprov.oop.model.classes.Vehicle;

import java.time.LocalDate;
import java.time.Period;

public interface Space {

    Vehicle getVehicle();
    void setVehicle(Vehicle vehicle);
    Person getPerson();
    void setPerson(Person person);
    boolean isEmpty();
    LocalDate getSinceDate();
    void setSinceDate(LocalDate sinceDate);
    Period getPeriod();
    String toString();
    int hashCode();
    boolean equals(Object obj);
    Object clone() throws CloneNotSupportedException;
}