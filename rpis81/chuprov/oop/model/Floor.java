package rpis81.chuprov.oop.model;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public interface Floor extends Comparable<Floor>, Iterable<Space>, Collection<Space> {

    boolean add(Space space);
    boolean add(int index, Space space) throws IndexOutOfBoundsException;
    Space get(int index) throws IndexOutOfBoundsException;

    default Space get(String registrationNumber) throws NoRentedSpaceException {
        Objects.requireNonNull(registrationNumber, "Параметр registrationNumber не должен быть null");
        for(Space space : toArray()) {
            if(isRegistrationNumberEqual(space, registrationNumber)) { return space; }
        }
        throw new NoRentedSpaceException();
    }

    default boolean hasSpace(String registrationNumber) {
        return Arrays.stream(toArray())
                .anyMatch(space -> isRegistrationNumberEqual(space, registrationNumber));
    }

    default boolean hasSpace(Person person) {
        return Arrays.stream(toArray()).anyMatch(space -> space.getPerson().equals(person));
    }

    Space replaceWith(int index, Space space) throws IndexOutOfBoundsException;

    Space remove(int index) throws IndexOutOfBoundsException;

    default Space remove(String registrationNumber) throws NoSuchElementException {
        for(int i = 0; i < size(); i++) {
            if(isRegistrationNumberEqual(get(i), Vehicle.checkNumber(registrationNumber))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    default boolean remove(Object o) {
        int initialSize = size();
        remove(((Space) o).getVehicle().getRegistrationNumber());
        return initialSize > size();
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
        return toArray().length;
    }

    default Collection<Vehicle> getVehicles() {
        return Arrays.stream(toArray())
                .map(Space::getVehicle)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    default int getVehiclesCount() {
        return getVehicles().size();
    }

    default boolean isRegistrationNumberEqual(Space space, String registrationNumber) {
        return space.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    default boolean isVehiclesTypeEqual(Space space, VehicleTypes type) {
        return space.getVehicle().getType().equals(type);
    }

    default List<Space> getSpacesByVehiclesType(VehicleTypes type) {
        return Arrays.stream(toArray())
                .filter(space -> isVehiclesTypeEqual(space, Objects.requireNonNull(type,
                        "Параметр type не должен быть null")))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    default Deque<Space> getFreeSpaces() {
        return new LinkedList<>(getSpacesByVehiclesType(VehicleTypes.NONE));
    }

    default RentedSpace[] getRentedSpaces() throws NoRentedSpaceException {
        RentedSpace[] rentedSpaces = Arrays.stream(toArray())
                .filter(space -> space instanceof RentedSpace)
                .toArray(RentedSpace[]::new);
        if(rentedSpaces.length == 0) {
            throw new NoRentedSpaceException();
        }
        return rentedSpaces;
    }

    default int getSpacesCountByVehiclesType(VehicleTypes type) {
        return getSpacesByVehiclesType(Objects.requireNonNull(type, "Параметр type не должен быть null")).size();
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
    Iterator<Space> iterator();

    @Override
    default boolean isEmpty() {
        return size() == 0;
    }

    @Override
    default boolean contains(Object o) {
        Space space = (Space) o;
        return hasSpace(space.getPerson());
    }

    @Override
    Space[] toArray();

    @Override
    default <T> T[] toArray(T[] ts) {
        Space[] spaces = (Space[]) ts;
        Arrays.sort(spaces, Comparator.comparing(Space::getSinceDate));
        return (T[]) spaces;
    }

    @Override
    default boolean containsAll(Collection<?> collection) {
        return collection.stream().allMatch(this::contains);
    }

    @Override
    default boolean addAll(Collection<? extends Space> collection) {
        collection.forEach(this::add);
        return size() >= collection.size();
    }

    @Override
    default boolean removeAll(Collection<?> collection) {
        int initialSize = size();
        iterator().forEachRemaining(space -> {
            if(collection.contains(space)) { remove(space); }
        });
        return initialSize > size();
    }

    @Override
    default boolean retainAll(Collection<?> collection) {
        int initialSize = size();
        iterator().forEachRemaining(space -> {
            if(!collection.contains(space)) { remove(space); }
        });
        return initialSize > size();
    }

    @Override
    default void clear() {
        iterator().forEachRemaining(this::remove);
    }
}