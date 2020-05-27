package rpis81.chuprov.oop.model;

public class Space {

    private Vehicle vehicle;
    private Person person;

    public Space(Vehicle vehicle, Person person) {
        this.vehicle = vehicle;
        this.person = person;
    }

    public Space() {
        this(new Vehicle(), Person.getUnknownPerson());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isEmpty() {
        return vehicle == null || vehicle.getRegistrationNumber().isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n# Space #");
        builder.append(vehicle.toString()).append(person.toString());
        return builder.toString();
    }
}
