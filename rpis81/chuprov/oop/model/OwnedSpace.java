package rpis81.chuprov.oop.model;

public class OwnedSpace implements Space {

    private Vehicle vehicle;
    private Person person;

    public OwnedSpace(Vehicle vehicle, Person person) {
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
        return vehicle == null || vehicle.getRegistrationNumber().isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("# Space #\n");
        builder.append(vehicle.toString()).append(person.toString());
        return builder.toString();
    }
}
