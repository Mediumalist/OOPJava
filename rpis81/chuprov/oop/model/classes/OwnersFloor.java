package rpis81.chuprov.oop.model.classes;


import rpis81.chuprov.oop.model.interfaces.Floor;
import rpis81.chuprov.oop.model.interfaces.InstanceHandler;
import rpis81.chuprov.oop.model.interfaces.Space;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class OwnersFloor implements Floor, InstanceHandler {

    private final static int DEFAULT_SIZE = 16;
    private final static int INITIAL_SIZE = 0;

    private Space[] spaces;
    private int size;

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
    public boolean add(Space space) {
        expand();
        for(int i = 0; i < spaces.length; i++) {
            if(spaces[i] == null) {
                spaces[i] = Objects.requireNonNull(space, "Параметр space не должен быть null");
                size = size();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(int index, Space space) {
        shift(index, false);
        return add(space);
    }

    @Override
    public Space get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return spaces[index];
    }

    @Override
    public Space replaceWith(int index, Space space) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Space replacedSpace = spaces[index];
        spaces[index] = Objects.requireNonNull(space, "Параметр space не должен быть null");
        return replacedSpace;
    }

    @Override
    public Space[] toArray() {
        return Arrays.stream(spaces).filter(Objects::nonNull).toArray(Space[]::new);
    }

    @Override
    public void shift(int index, boolean isLeft) {
        expand();
        if (spaces.length > index && index >= 0) {
            if (isLeft) {
                System.arraycopy(spaces, index + 1, spaces, index, spaces.length - index - 1);
                spaces[spaces.length - 1] = null;
            }
            else {
                System.arraycopy(spaces, index, spaces, index + 1, spaces.length - index - 1);
                spaces[index] = null;
            }
            size = size();
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void expand() {
        if(spaces[spaces.length - 1] != null) {
            Space[] updatedSpaces = new Space[size * 2];
            System.arraycopy(spaces, 0, updatedSpaces, 0, spaces.length);
            spaces = updatedSpaces;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Spaces:\n");
        iterator().forEachRemaining(space -> builder.append(space.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public int hashCode() {
        AtomicInteger code = new AtomicInteger(71 * size);
        iterator().forEachRemaining(space -> code.updateAndGet(value -> value ^ space.hashCode()));
        return code.get();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof OwnersFloor)) {
            return false;
        }
        OwnersFloor other = (OwnersFloor) obj;
        return size == other.size && Objects.deepEquals(spaces, other.spaces);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return deployBuilder()
                    .setSpaces(spaces)
                    .setSize(size)
                    .build();
        }
    }

    public static Builder deployBuilder() {
        return new OwnersFloor().new Builder();
    }

    public class Builder {

        private Builder() { }

        public Builder setSpaces(Space[] spaces) {
            OwnersFloor.this.spaces = spaces;
            return this;
        }

        public Builder setSize(int size) {
            OwnersFloor.this.size = size;
            return this;
        }

        public OwnersFloor build() {
            return OwnersFloor.this;
        }
    }
}