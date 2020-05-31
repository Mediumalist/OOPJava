package rpis81.chuprov.oop.model;

import java.util.Arrays;
import java.util.Objects;

public class RentedSpacesFloor implements Floor, InstanceHandler {

    private Node head;
    private int size;
    private final static int DEFAULT_SIZE = 1;
    private final static Space DEFAULT_SPACE = new RentedSpace();

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
        node.setNext(new Node(node, head, space));
        size++;
        return true;
    }

    @Override
    public boolean add(int index, Space space) {
        shift(index, false);
        getNode(index).setValue(space);
        return true;
    }

    @Override
    public Space get(int index) {
        return getNode(index).getValue();
    }

    @Override
    public Space get(String registrationNumber) {
        Node node = head;
        while(node.getNext() != head) {
            node = (node.getNext() != null) ? node.getNext() : node;
            if(checkRegistrationNumber(node.getValue(), registrationNumber)) {
                return node.getValue();
            }
        }
        return DEFAULT_SPACE;
    }

    @Override
    public boolean hasSpace(String registrationNumber) {
        return Arrays.stream(getSpaces())
                .anyMatch(space -> checkRegistrationNumber(space, registrationNumber));
    }

    @Override
    public boolean hasSpace(Person person) {
        return Arrays.stream(getSpaces()).anyMatch(space -> space.getPerson().equals(person));
    }

    @Override
    public boolean checkRegistrationNumber(Space space, String registrationNumber) {
        return space.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    @Override
    public boolean checkVehiclesType(Space space, VehicleTypes type) {
        return space.getVehicle().getType().equals(type);
    }

    @Override
    public Space replaceWith(int index, Space space) {
        Space replacedSpace = get(index);
        getNode(index).setValue(space);
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
            if(checkRegistrationNumber(get(i), registrationNumber)) {
                return remove(i);
            }
        }
        return DEFAULT_SPACE;
    }

    @Override
    public boolean remove(Space space) {
        remove(indexOf(space));
        return true;
    }

    @Override
    public int indexOf(Space space) {
        for(int i = 0; i < size; i++) {
            if(get(i).equals(space)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getSpacesCountWithPerson(Person person) {
        return (int) Arrays.stream(getSpaces())
                .filter(space -> space.getPerson().equals(person))
                .count();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Space[] getSpaces() {
        Space[] spaces = new Space[size];
        for(int i = 0; i < size; i++) {
            spaces[i] = get(i);
        }
        return spaces;
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
                .filter(space -> checkVehiclesType(space, type))
                .toArray(Space[]::new);
    }

    @Override
    public Space[] getFreeSpaces() {
        return getSpacesByVehiclesType(VehicleTypes.NONE);
    }

    @Override
    public int getSpacesCountByVehiclesType(VehicleTypes type) {
        return getSpacesByVehiclesType(type).length;
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
        Node node = getNode(index), nextNode = node.getNext(), previousNode = node.getPrevious();
        if(isLeft) {
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            size--;
        }
        else {
            Node insertedNode = new Node(null);
            previousNode.setNext(insertedNode);
            insertedNode.setNext(node);
            insertedNode.setPrevious(previousNode);
            node.setPrevious(insertedNode);
            size++;
        }
    }

    @Override
    public void expand() {
        add(new RentedSpace());
        size++;
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