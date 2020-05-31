package rpis81.chuprov.oop.model;

public abstract class AbstractSpace implements Space {

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
        StringBuilder builder = new StringBuilder("# Space #\n");
        builder.append(vehicle.toString()).append(person.toString());
        return builder.toString();
    }
}