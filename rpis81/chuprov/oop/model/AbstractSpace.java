package rpis81.chuprov.oop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public abstract class AbstractSpace implements Space, Cloneable {

    private Vehicle vehicle;
    private Person person;
    private LocalDate sinceDate;

    protected AbstractSpace() {
        this(Vehicle.getNoVehicle(), Person.getUnknownPerson(), LocalDate.now());
    }

    protected AbstractSpace(Person person, LocalDate sinceDate) {
        this(Vehicle.getNoVehicle(), person, sinceDate);
    }

    protected AbstractSpace(Vehicle vehicle, Person person) {
        this(vehicle, person, LocalDate.now());
    }

    protected AbstractSpace(Vehicle vehicle, Person person, LocalDate sinceDate) {
        this.vehicle = Objects.requireNonNull(vehicle, "Значение vehicle не должно быть null");
        this.person = Objects.requireNonNull(person, "Значение person не должно быть null");
        checkDates(sinceDate, LocalDate.now().plusDays(1));
    }

    protected LocalDate checkDates(LocalDate sinceDate, LocalDate date) {
        if(sinceDate.isBefore(date)) {
            this.sinceDate = Objects.requireNonNull(sinceDate, "Значение sinceDate не должно быть null");
        }
        else {
            throw new IllegalArgumentException("Дата начала владения парковочным " +
                    "местом не может быть позже текущей даты");
        }
        return date;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = Objects.requireNonNull(vehicle, "Значение vehicle не должно быть null");
    }

    @Override
    public Person getPerson() {
        return person;
    }

    @Override
    public void setPerson(Person person) {
        this.person = Objects.requireNonNull(person, "Значение person не должно быть null");
    }

    @Override
    public LocalDate getSinceDate() {
        return sinceDate;
    }

    @Override
    public void setSinceDate(LocalDate sinceDate) {
        this.sinceDate = Objects.requireNonNull(sinceDate, "Значение sinceDate не должно быть null");
    }

    @Override
    public Period getPeriod() {
        return Period.between(sinceDate, LocalDate.now());
    }

    @Override
    public boolean isEmpty() {
        return vehicle.equals(Vehicle.getNoVehicle()) || vehicle.getType().equals(VehicleTypes.NONE);
    }

    @Override
    public String toString() {
        return String.format("[Person] %s\n[Vehicle] %s\n[Since date] %s\n",
                person.toString(), vehicle.toString(), sinceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, vehicle, sinceDate);
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
        return Objects.equals(person, other.person) && Objects.equals(vehicle, other.vehicle) &&
                Objects.equals(sinceDate, other.sinceDate);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}