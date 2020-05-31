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

    @Override
    public String toString() {
        return String.format("Tenant:\n%s", super.toString());
    }

    @Override
    public int hashCode() {
        return 53 * super.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}