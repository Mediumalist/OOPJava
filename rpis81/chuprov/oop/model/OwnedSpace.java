package rpis81.chuprov.oop.model;

import java.time.LocalDate;

public class OwnedSpace extends AbstractSpace {

    public OwnedSpace() {
        super();
    }

    public OwnedSpace(Vehicle vehicle, Person person) {
        super(vehicle, person);
    }

    public OwnedSpace(Person person, LocalDate sinceDate) {
        super(person, sinceDate);
    }

    public OwnedSpace(Vehicle vehicle, Person person, LocalDate sinceDate) {
        super(vehicle, person, sinceDate);
    }

    @Override
    public String toString() {
        return String.format("Owner:\n%s", super.toString());
    }

    @Override
    public int hashCode() {
        return Math.abs(71 * super.hashCode());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}