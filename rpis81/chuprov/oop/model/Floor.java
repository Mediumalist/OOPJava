package rpis81.chuprov.oop.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public interface Floor extends InstanceHandler, Comparable<Floor>, Iterable<Space> {

    boolean add(Space space);
    boolean add(int index, Space space) throws IndexOutOfBoundsException;
    Space get(int index) throws IndexOutOfBoundsException;

    default Space get(String registrationNumber) throws NoRentedSpaceException {
        Objects.requireNonNull(registrationNumber, "Параметр registrationNumber не должен быть null");
        for(Space space : getSpaces()) {
            if(isRegistrationNumberEqual(space, registrationNumber)) { return space; }
        }
        throw new NoRentedSpaceException();
    }

    default boolean hasSpace(String registrationNumber) {
        return Arrays.stream(getSpaces())
                .anyMatch(space -> isRegistrationNumberEqual(space, registrationNumber));
    }

    default boolean hasSpace(Person person) {
        return Arrays.stream(getSpaces()).anyMatch(space -> space.getPerson().equals(person));
    }

    Space replaceWith(int index, Space space) throws IndexOutOfBoundsException;

    default Space remove(int index) throws IndexOutOfBoundsException {
        Space removedSpace = get(index);
        shift(index, true);
        return removedSpace;
    }

    default Space remove(String registrationNumber) throws NoSuchElementException {
        for(int i = 0; i < size(); i++) {
            if(isRegistrationNumberEqual(get(i), Vehicle.checkNumber(registrationNumber))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException();
    }

    default boolean remove(Space space) {
        remove(indexOf(space));
        return true;
    }

    default int indexOf(Space space) {
        Objects.requireNonNull(space, "Параметр space не должен быть null");
        for(int i = 0; i < size(); i++) {
            if(get(i).equals(space)) { return i; }
        }
        throw new IndexOutOfBoundsException();
    }

    default int getSpacesCountWithPerson(Person person) {
        Objects.requireNonNull(person, "Параметр person не должен быть null");
        AtomicInteger counter = new AtomicInteger(0);
        iterator().forEachRemaining(space -> {
            if(space.getPerson().equals(person)) { counter.incrementAndGet(); }
        });
        return counter.get();
    }

    default int size() {
        return getSpaces().length;
    }

    Space[] getSpaces();

    default Vehicle[] getVehicles() {
        return Arrays.stream(getSpaces())
                .map(Space::getVehicle)
                .filter(Objects::nonNull)
                .toArray(Vehicle[]::new);
    }

    default int getVehiclesCount() {
        return getVehicles().length;
    }

    default boolean isRegistrationNumberEqual(Space space, String registrationNumber) {
        return space.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    default boolean isVehiclesTypeEqual(Space space, VehicleTypes type) {
        return space.getVehicle().getType().equals(type);
    }

    default Space[] getSpacesByVehiclesType(VehicleTypes type) {
        return Arrays.stream(getSpaces())
                .filter(space -> isVehiclesTypeEqual(space, Objects.requireNonNull(type, "Параметр type не должен быть null")))
                .toArray(Space[]::new);
    }

    default Space[] getFreeSpaces() { return getSpacesByVehiclesType(VehicleTypes.NONE); }

    default RentedSpace[] getRentedSpaces() throws NoRentedSpaceException {
        RentedSpace[] rentedSpaces = Arrays.stream(getSpaces())
                .filter(space -> space instanceof RentedSpace)
                .toArray(RentedSpace[]::new);
        if(rentedSpaces.length == 0) {
            throw new NoRentedSpaceException();
        }
        return rentedSpaces;
    }

    default int getSpacesCountByVehiclesType(VehicleTypes type) {
        return getSpacesByVehiclesType(Objects.requireNonNull(type, "Параметр type не должен быть null")).length;
    }

    default LocalDate getNearestEndsDate() throws NoRentedSpaceException {
        return ((RentedSpace)getSpaceWithNearestEndsDate()).getRentEndsDate();
    }

    default Space getSpaceWithNearestEndsDate() throws NoRentedSpaceException {
        return Objects.requireNonNull(Arrays.stream(getRentedSpaces()).min(RentedSpace::compareTo).get());
    }

    default void printSpacesByVehiclesType(VehicleTypes type) {
        for(Space space : getSpacesByVehiclesType(type)) {
            System.out.println(space.toString());
        }
    }

    String toString();
    int hashCode();
    boolean equals(Object obj);
    public Object clone() throws CloneNotSupportedException;

    @Override
    default int compareTo(Floor floor) {
        return size() - floor.size();
    }

    @Override
    default Iterator<Space> iterator() {
        return new SpaceIterator(getSpaces());
    }
}