package rpis81.chuprov.oop.model;

public class OwnersFloor {

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

    public int size() {
        return size;
    }

    public Space[] getSpaces() {
        /*
         *  Благодаря конструкторам, а также методам add() и remove() поле size
         *  всегда имеет актуальное значение, поэтому при выполнении операций
         *  с кол-вом элементов = size проверку на null выполнять необязательно
         */
        Space[] notNullSpaces = new Space[size];
        System.arraycopy(spaces, 0, notNullSpaces, 0, size);
        return notNullSpaces;
    }

    public Vehicle[] getVehicles() {
        Vehicle[] notNullVechicles = new Vehicle[size];
        for(int i = 0; i < size; i++) {
            notNullVechicles[i] = spaces[i].getVehicle();
        }
        return notNullVechicles;
    }

    public boolean add(Space space) {
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

    public boolean add(int index, Space space) {
        shift(index, false);
        return add(space);
    }

    public Space getSpace(int index) {
        return spaces[index];
    }

    public Space getSpace(String registrationNumber) {
        for(Space space : spaces) {
            if(checkRegistrationNumber(space, registrationNumber)) {
                return space;
            }
        }
        return new Space();
    }

    public boolean hasSpace(String registrationNumber) {
        for(Space space : spaces) {
            if(checkRegistrationNumber(space, registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRegistrationNumber(Space space, String registrationNumber) {
        return space.getVehicle().getRegistrationNumber().equals(registrationNumber);
    }

    public Space replaceWith(int index, Space space) {
        Space replacedSpace = spaces[index];
        spaces[index] = space;
        return replacedSpace;
    }

    public Space remove(int index) {
        Space removedSpace = spaces[index];
        shift(index, true);
        size--;
        return removedSpace;
    }

    public Space remove(String registrationNumber) {
        for(int i = 0; i < getSpaces().length; i++) {
            if(checkRegistrationNumber(spaces[i], registrationNumber)) {
                return remove(i);
            }
        }
        return new Space();
    }

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

    public void expand() {
        if(spaces[spaces.length - 1] != null) {
            Space[] updatedSpaces = new Space[size * 2];
            System.arraycopy(spaces, 0, updatedSpaces, 0, spaces.length);
            spaces = updatedSpaces;
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
