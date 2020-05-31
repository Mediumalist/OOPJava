package rpis81.chuprov.oop.model;

public class OwnersFloor implements Floor, InstanceHandler {

    private Space[] spaces;
    private int size;
    private final static int DEFAULT_SIZE = 16;
    private final static int INITIAL_SIZE = 0;

    public OwnersFloor(Space[] spaces) {
        this.spaces = spaces;
        this.size = spaces.length;
    }

    public OwnersFloor(int size) {
        this.spaces = new Space[size];
        this.size = INITIAL_SIZE;
    }

    public OwnersFloor() {
        this.spaces = new Space[DEFAULT_SIZE];
        this.size = INITIAL_SIZE;
    }


    @Override
    public boolean addSpace(Space space) {
        expand();
        for(int i = 0; i < spaces.length; i++) {
            if(spaces[i] == null) {
                spaces[i] = space;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addSpace(int index, Space space) {
        shift(index, false);
        return addSpace(space);
    }

    @Override
    public Space getSpace(int index) {
        return spaces[index];
    }

    @Override
    public Space getSpace(String registrationNumber) {
        for(Space rentedSpace : spaces) {
            if(checkRegistrationNumber(rentedSpace, registrationNumber)) {
                return rentedSpace;
            }
        }
        return new RentedSpace();
    }

    @Override
    public boolean hasSpace(String registrationNumber) {
        for(Space rentedSpace : spaces) {
            if(checkRegistrationNumber(rentedSpace, registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkRegistrationNumber(Space rentedSpace, String registrationNumber) {
        return rentedSpace.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    @Override
    public Space replaceWith(int index, Space space) {
        Space replacedRentedSpace = spaces[index];
        spaces[index] = space;
        return replacedRentedSpace;
    }

    @Override
    public Space remove(int index) {
        Space removedRentedSpace = spaces[index];
        shift(index, true);
        size--;
        return removedRentedSpace;
    }

    @Override
    public Space remove(String registrationNumber) {
        for(int i = 0; i < getSpaces().length; i++) {
            if(checkRegistrationNumber(spaces[i], registrationNumber)) {
                return remove(i);
            }
        }
        return new RentedSpace();
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public Space[] getSpaces() {
        Space[] notNullSpaces = new Space[size];
        System.arraycopy(spaces, 0, notNullSpaces, 0, size);
        return notNullSpaces;
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] notNullVechicles = new Vehicle[size];
        for(int i = 0; i < size; i++) {
            notNullVechicles[i] = spaces[i].getVehicle();
        }
        return notNullVechicles;
    }

    @Override
    public void shift(int index, boolean isLeft) {
        expand();
        if (spaces.length >= index) {
            if (isLeft) {
                System.arraycopy(spaces, index + 1, spaces, index, spaces.length - index - 1);
                spaces[spaces.length - 1] = null;
            }
            else {
                System.arraycopy(spaces, index, spaces, index + 1, spaces.length - index - 1);
                spaces[index] = null;
            }
        }
    }

    @Override
    public void expand() {
        if(spaces[spaces.length - 1] != null) {
            Space[] updatedRentedSpaces = new Space[size * 2];
            System.arraycopy(spaces, 0, updatedRentedSpaces, 0, spaces.length);
            spaces = updatedRentedSpaces;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n\nFloor with size ");
        builder.append(size);
        for (Space space : getSpaces()) {
            builder.append(space.toString());
        }
        return builder.toString();
    }
}
