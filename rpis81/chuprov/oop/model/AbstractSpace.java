package rpis81.chuprov.oop.model;

import java.util.Objects;

public abstract class AbstractSpace implements Space, Cloneable {

    private Vehicle vehicle;
    private Person person;

    protected AbstractSpace() {
        this(Vehicle.getNoVehicle(), Person.getUnknownPerson());
    }

    protected AbstractSpace(Person person) {
        this(Vehicle.getNoVehicle(), person);
    }

    protected AbstractSpace(Vehicle vehicle, Person person) {
        this.vehicle = vehicle;
        this.person = person;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Person getPerson() {
        return person;
    }

    @Override
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean isEmpty() {
        return vehicle.equals(Vehicle.getNoVehicle()) || vehicle.getType().equals(VehicleTypes.NONE);
    }

    @Override
    public String toString() {
        return String.format("[Person] %s\n[Vehicle] %s\n", person.toString(), vehicle.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, vehicle);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof AbstractSpace)) {
            return false;
        }
        AbstractSpace other = (AbstractSpace) obj;
        return Objects.equals(person, other.person) && Objects.equals(vehicle, other.vehicle);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}