package rpis81.chuprov.oop.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class RentedSpacesFloor implements Floor, InstanceHandler {

    private final static int DEFAULT_SIZE = 1;
    private final static RentedSpace DEFAULT_SPACE = new RentedSpace();

    private Node head;
    private int size;

    public RentedSpacesFloor() {
        this.head = new Node(null);
        this.size = DEFAULT_SIZE;
    }

    public RentedSpacesFloor(Space[] spaces) {
        this.head = new Node(spaces[0]);
        this.size = DEFAULT_SIZE;
        for(int i = DEFAULT_SIZE; i < spaces.length; i++) {
            add(spaces[i]);
        }
    }

    @Override
    public boolean add(Space space) {
        Node previousNode, node = head;
        while(node.getNext() != head) {
            previousNode = node;
            node = node.getNext();
            node.setPrevious(previousNode);
            node.getPrevious().setNext(node);
        }
        node.setNext(new Node(node, head, Objects.requireNonNull(space, "Параметр space не должен быть null")));
        size++;
        return true;
    }

    @Override
    public boolean add(int index, Space space) {
        shift(index, false);
        getNode(index).setValue(Objects.requireNonNull(space, "Параметр space не должен быть null"));
        return true;
    }

    @Override
    public Space get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getValue();
    }

    @Override
    public Space get(String registrationNumber) throws NoRentedSpaceException {
        Objects.requireNonNull(registrationNumber, "Параметр registrationNumber не должен быть null");
        Node node = head;
        while(node.getNext() != head) {
            node = (node.getNext() != null) ? node.getNext() : node;
            if(isRegistrationNumberEqual(node.getValue(), registrationNumber)) {
                return node.getValue();
            }
        }
        throw new NoRentedSpaceException();
    }

    @Override
    public boolean hasSpace(String registrationNumber) {
        return Arrays.stream(getSpaces())
                .anyMatch(space -> isRegistrationNumberEqual(space, registrationNumber));
    }

    @Override
    public boolean hasSpace(Person person) {
        return Arrays.stream(getSpaces()).anyMatch(space -> space.getPerson().equals(person));
    }

    @Override
    public boolean isRegistrationNumberEqual(Space space, String registrationNumber) {
        return space.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    @Override
    public boolean isVehiclesTypeEqual(Space space, VehicleTypes type) {
        return space.getVehicle().getType().equals(type);
    }

    @Override
    public Space replaceWith(int index, Space space) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Space replacedSpace = get(index);
        getNode(index).setValue(Objects.requireNonNull(space, "Параметр space не должен быть null"));
        return replacedSpace;
    }

    @Override
    public Space remove(int index) {
        Space removedSpace = get(index);
        shift(index, true);
        return removedSpace;
    }

    @Override
    public Space remove(String registrationNumber) {
        for(int i = 0; i < size; i++) {
            if(isRegistrationNumberEqual(get(i), Vehicle.checkNumber(registrationNumber))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean remove(Space space) {
        remove(indexOf(space));
        return true;
    }

    @Override
    public int indexOf(Space space) {
        Objects.requireNonNull(space, "Параметр space не должен быть null");
        for(int i = 0; i < size; i++) {
            if(get(i).equals(space)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getSpacesCountWithPerson(Person person) {
        Objects.requireNonNull(person, "Параметр person не должен быть null");
        return (int) Arrays.stream(getSpaces())
                .filter(space -> space.getPerson().equals(person))
                .count();
    }

    @Override
    public int size() {
        return getSpaces().length;
    }

    @Override
    public Space[] getSpaces() {
        Space[] spaces = new Space[size];
        for(int i = 0; i < size; i++) {
            spaces[i] = get(i);
        }
        return Arrays.stream(spaces).filter(Objects::nonNull).toArray(Space[]::new);
    }

    @Override
    public Vehicle[] getVehicles() {
        return Arrays.stream(getSpaces())
                .map(Space::getVehicle)
                .filter(Objects::nonNull)
                .toArray(Vehicle[]::new);
    }

    @Override
    public int getVehiclesCount() {
        return getVehicles().length;
    }

    @Override
    public Space[] getSpacesByVehiclesType(VehicleTypes type) {
        return Arrays.stream(getSpaces())
                .filter(space -> isVehiclesTypeEqual(space, Objects.requireNonNull(type, "Параметр type не должен быть null")))
                .toArray(Space[]::new);
    }

    @Override
    public Space[] getFreeSpaces() {
        return getSpacesByVehiclesType(VehicleTypes.NONE);
    }

    @Override
    public int getSpacesCountByVehiclesType(VehicleTypes type) {
        return getSpacesByVehiclesType(Objects.requireNonNull(type, "Параметр type не должен быть null")).length;
    }

    @Override
    public LocalDate getNearestEndsDate() {
        LocalDate nearestDate = LocalDate.MAX;
        for(Space space : getRentedSpaces()) {
            if(((RentedSpace)space).getRentEndsDate().isBefore(nearestDate)) {
                nearestDate = ((RentedSpace)space).getRentEndsDate();
            }
        }
        return nearestDate;
    }

    @Override
    public Space getSpaceWithNearestEndsDate() throws NoRentedSpaceException {
        for(Space space : getRentedSpaces()) {
            if(((RentedSpace)space).getRentEndsDate().equals(getNearestEndsDate())) {
                return space;
            }
        }
        throw new NoRentedSpaceException();
    }

    private Space[] getRentedSpaces() {
        return Arrays.stream(getSpaces())
                .filter(Objects::nonNull)
                .filter(space -> space instanceof RentedSpace)
                .toArray(Space[]::new);
    }

    private Node getNode(int index) {
        Node node = head;
        for(int i = 0; i < index; i++) {
            node = (node.getNext() != null) ? node.getNext() : node;
        }
        return node;
    }

    @Override
    public void shift(int index, boolean isLeft) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = getNode(index), nextNode = node.getNext(), previousNode = node.getPrevious();
        if(isLeft) {
            if(index == 0) {
                head = getNode(1);
            }
            else {
                previousNode.setNext(nextNode);
                nextNode.setPrevious(previousNode);
            }
            size--;
        }
        else {
            Node insertedNode = new Node(null);
            if(index == 0) {
                insertedNode.setNext(node);
                insertedNode.setPrevious(insertedNode);
                head = insertedNode;
                getNode(size).setNext(head);
                node.setPrevious(head);
            }
            else {
                previousNode.setNext(insertedNode);
                insertedNode.setNext(node);
                insertedNode.setPrevious(previousNode);
                node.setPrevious(insertedNode);
            }
            size++;
        }
    }

    @Override
    public void expand() {
        add(DEFAULT_SPACE);
    }

    public void printSpaces() {
        System.out.println(toString());
    }

    public void printVehicles() {
        for(Vehicle vehicle : getVehicles()) {
            System.out.println(vehicle.toString());
        }
    }

    public void printSpacesByVehiclesType(VehicleTypes type) {
        for(Space space : getSpacesByVehiclesType(type)) {
            System.out.println(space.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Rented spaces:\n");
        for(Space space : getSpaces()) {
            builder.append(space.toString()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int code = 53 * size;
        for (Space space : getSpaces()) {
            code ^= space.hashCode();
        }
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof RentedSpacesFloor)) {
            return false;
        }
        RentedSpacesFloor other = (RentedSpacesFloor) obj;
        return size == other.size && Objects.deepEquals(getSpaces(), other.getSpaces());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}