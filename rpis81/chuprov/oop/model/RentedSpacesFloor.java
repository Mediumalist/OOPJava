package rpis81.chuprov.oop.model;

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
            addSpace(spaces[i]);
        }
    }

    @Override
    public boolean addSpace(Space space) {
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
    public boolean addSpace(int index, Space space) {
        shift(index, false);
        getNode(index).setValue(space);
        return true;
    }

    @Override
    public Space getSpace(int index) {
        return getNode(index).getValue();
    }

    @Override
    public Space getSpace(String registrationNumber) {
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
        for(int i = 0; i < size; i++) {
            if(checkRegistrationNumber(getSpace(i), registrationNumber)) {
                return true;
            }
        }
        return false;
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
        Space replacedSpace = getSpace(index);
        getNode(index).setValue(space);
        return replacedSpace;
    }

    @Override
    public Space remove(int index) {
        Space removedSpace = getSpace(index);
        shift(index, true);
        return removedSpace;
    }

    @Override
    public Space remove(String registrationNumber) {
        for(int i = 0; i < size; i++) {
            if(checkRegistrationNumber(getSpace(i), registrationNumber)) {
                return remove(i);
            }
        }
        return DEFAULT_SPACE;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Space[] getSpaces() {
        Space[] spaces = new Space[size];
        for(int i = 0; i < size; i++) {
            spaces[i] = getSpace(i);
        }
        return spaces;
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] vehicles = new Vehicle[size];
        for(int i = 0; i < size; i++) {
            vehicles[i] = getSpace(i).getVehicle();
        }
        return vehicles;
    }

    @Override
    public Space[] getSpacesByVehiclesType(VehicleTypes type) {
        int index = 0;
        Space[] spaces = new Space[getSpacesCountByVehiclesType(type)];
        for(Space space : getSpaces()) {
            if(checkVehiclesType(space, type)) {
                spaces[index] = space;
                index++;
            }
        }
        return spaces;
    }

    @Override
    public Space[] getFreeSpaces() {
        return getSpacesByVehiclesType(VehicleTypes.NONE);
    }

    @Override
    public int getSpacesCountByVehiclesType(VehicleTypes type) {
        int count = 0;
        for(int i = 0; i < size; i++) {
            count = (checkVehiclesType(getSpace(i), type)) ? count + 1 : count;
        }
        return count;
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
        addSpace(new RentedSpace());
        size++;
    }

    public void printSpaces() {
        for(Space space : getSpaces()) {
            System.out.println(space.toString());
        }
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
}