package rpis81.chuprov.oop.model.classes;

import rpis81.chuprov.oop.model.enums.VehicleTypes;
import rpis81.chuprov.oop.model.exceptions.NoRentedSpaceException;
import rpis81.chuprov.oop.model.interfaces.Floor;
import rpis81.chuprov.oop.model.interfaces.InstanceHandler;
import rpis81.chuprov.oop.model.interfaces.Space;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parking implements InstanceHandler, Iterable<Floor>, Cloneable {

    private final static int INITIAL_SIZE = 0;

    private Floor[] floors;
    private int size;

    public Parking(Floor... floors) {
        this.floors = floors;
        this.size = floors.length;
    }

    public Parking(int size) {
        this.floors = new Floor[size];
        this.size = INITIAL_SIZE;
    }

    public int size() {
        return getFloors().length;
    }

    public Floor[] getFloors() {
        return Arrays.stream(floors).filter(Objects::nonNull).toArray(Floor[]::new);
    }

    public List<Floor> getSortedFloors() {
        return Arrays.stream(getFloors())
                .sorted(Floor::compareTo)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<Vehicle> getVechicles() {
        Collection<Vehicle> vehicles = new ArrayList<>();
        iterator().forEachRemaining(floor -> vehicles.addAll(floor.getVehicles()));
        return vehicles;
    }

    public int getSpacesCount() {
        AtomicInteger count = new AtomicInteger(0);
        iterator().forEachRemaining(floor -> count.addAndGet(floor.size()));
        return count.get();
    }

    public boolean add(Floor floor) {
        expand();
        for(int i = 0; i < floors.length; i++) {
            if(floors[i] == null) {
                floors[i] = Objects.requireNonNull(floor, "Параметр floor не должен быть null");
                size++;
                return true;
            }
        }
        return false;
    }

    public boolean add(int index, Floor floor) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        shift(index, false);
        return add(floor);
    }

    public Floor getFloor(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return Objects.requireNonNull(floors[index]);
    }

    public Floor replaceFloor(int index, Floor floor) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Floor replacedFloor = floors[index];
        floors[index] = Objects.requireNonNull(floor, "Параметр floor не должен быть null");
        return replacedFloor;
    }

    public Floor removeFloor(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Floor removedFloor = floors[index];
        shift(index, true);
        return removedFloor;
    }

    public Space removeSpace(String registrationNumber) {
        for(Floor floor : getFloors()) {
            if(floor.hasSpace(registrationNumber)) {
                return floor.remove(registrationNumber);
            }
        }
        throw new NoSuchElementException();
    }

    public Space getSpace(String registrationNumber) throws NoRentedSpaceException {
        for(Floor floor : floors) {
            for(Space space : floor.toArray()) {
                if(floor.isRegistrationNumberEqual(space, Vehicle.checkNumber(registrationNumber))) {
                    return space;
                }
            }
        }
        throw new NoRentedSpaceException();
    }

    public Space replaceSpace(Space space, String registrationNumber) throws NoRentedSpaceException {
        for(Floor floor : getFloors()) {
            if(floor.hasSpace(registrationNumber)) {
                return floor.replaceWith(floor.indexOf(floor.get(registrationNumber)), space);
            }
        }
        throw new NoRentedSpaceException();
    }

    public int getFreeSpacesCount() {
        return getSpacesCountByVehiclesType(VehicleTypes.NONE);
    }

    public int getSpacesCountByVehiclesType(VehicleTypes type) {
        Objects.requireNonNull(type, "Параметр type не должен быть null");
        AtomicInteger count = new AtomicInteger(0);
        iterator().forEachRemaining(floor -> count.addAndGet(floor.getSpacesCountByVehiclesType(type)));
        return count.get();
    }

    public Set<Floor> getFloorsWithPerson(Person person) {
        Objects.requireNonNull(person, "Параметр person не должен быть null");
        return Arrays.stream(floors)
                .filter(floor -> floor.hasSpace(person))
                .collect(Collectors.toCollection(HashSet::new));
    }

    public void printFloorsWithPerson(Person person) {
        Objects.requireNonNull(person, "Параметр person не должен быть null");
        for(Floor floor : getFloorsWithPerson(person)) {
            System.out.println(floor.toString());
        }
    }

    @Override
    public void shift(int index, boolean isLeft) {
        expand();
        if (floors.length > index && index >= 0) {
            if (isLeft) {
                System.arraycopy(floors, index + 1, floors, index, floors.length - index - 1);
                floors[floors.length - 1] = null;
            }
            else {
                System.arraycopy(floors, index, floors, index + 1, floors.length - index - 1);
                floors[index] = null;
            }
            size = size();
        }
    }

    @Override
    public void expand() {
        if(floors[floors.length - 1] != null) {
            Floor[] updatedFloors = new Floor[size * 2];
            System.arraycopy(floors, 0, updatedFloors, 0, floors.length);
            floors = updatedFloors;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\nFloors (");
        builder.append(size).append(" total):\n");
        iterator().forEachRemaining(floor -> builder.append(floor.toString()));
        return builder.toString();
    }

    @Override
    public Iterator<Floor> iterator() {
        return new CustomizableIterator<>(getFloors());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}