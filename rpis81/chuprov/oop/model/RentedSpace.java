package rpis81.chuprov.oop.model;

public class RentedSpace extends AbstractSpace {

    public RentedSpace() {
        super();
    }

    public RentedSpace(Person person) {
        super(person);
    }

    public RentedSpace(Vehicle vehicle, Person person) {
        super(vehicle, person);
    }
}