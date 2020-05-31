package rpis81.chuprov.oop.model;

public class OwnedSpace extends AbstractSpace {

    public OwnedSpace() {
        super();
    }

    public OwnedSpace(Person person) {
        super(person);
    }

    public OwnedSpace(Vehicle vehicle, Person person) {
        super(vehicle, person);
    }

    @Override
    public String toString() {
        return String.format("Owner:\n%s", super.toString());
    }

    @Override
    public int hashCode() {
        return 71 * super.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}