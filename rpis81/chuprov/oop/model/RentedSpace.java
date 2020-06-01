package rpis81.chuprov.oop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class RentedSpace extends AbstractSpace implements Comparable<RentedSpace> {

    private final static LocalDate DEFAULT_SINCE_DATE = LocalDate.now().minusDays(1);
    private final static LocalDate DEFAULT_ENDS_DATE = LocalDate.now().plusDays(1);

    private LocalDate rentEndsDate;

    public RentedSpace() {
        this(Vehicle.getNoVehicle(), Person.getUnknownPerson(), DEFAULT_SINCE_DATE, DEFAULT_ENDS_DATE);
    }

    public RentedSpace(Vehicle vehicle, Person person) {
        this(vehicle, person, DEFAULT_SINCE_DATE, DEFAULT_ENDS_DATE);
    }

    public RentedSpace(Person person, LocalDate sinceDate, LocalDate rentEndsDate) {
        this(Vehicle.getNoVehicle(), person, sinceDate, rentEndsDate);
    }

    public RentedSpace(Vehicle vehicle, Person person, LocalDate sinceDate, LocalDate rentEndsDate) {
        super(vehicle, person, sinceDate);
        this.rentEndsDate = checkDates(sinceDate, rentEndsDate);
    }

    public LocalDate getRentEndsDate() {
        return rentEndsDate;
    }

    public void setRentEndsDate(LocalDate rentEndsDate) {
        this.rentEndsDate = Objects.requireNonNull(rentEndsDate, "Значение параметра rentEndsDate не должно быть null");
    }

    @Override
    public Period getPeriod() {
        return Period.between(getSinceDate(), rentEndsDate);
    }

    @Override
    public String toString() {
        return String.format("Tenant:\n%s[Rent ends date] %s\n", super.toString(), rentEndsDate);
    }

    @Override
    public int hashCode() {
        return Math.abs(53 * super.hashCode());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(RentedSpace rentedSpace) {
        return rentEndsDate.compareTo(rentedSpace.rentEndsDate);
    }
}