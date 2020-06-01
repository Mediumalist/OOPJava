package rpis81.chuprov.oop.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class OwnersFloor implements Floor {

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

    private void shift(int index, boolean isLeft) {
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

    private void expand() {
        if(spaces[spaces.length - 1] != null) {
            Space[] updatedRentedSpaces = new Space[size * 2];
            System.arraycopy(spaces, 0, updatedRentedSpaces, 0, spaces.length);
            spaces = updatedRentedSpaces;
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Space remove(int index) throws IndexOutOfBoundsException {
        Space removedSpace = get(index);
        shift(index, true);
        return removedSpace;
    }

    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator(toArray());
    }

    private class SpaceIterator extends ObjectIterator implements Iterator<Space> {

        public SpaceIterator(Space[] spaces) {
            super(spaces);
        }

        @Override
        public boolean hasNext() {
            return super.hasNext();
        }

        @Override
        public Space next() {
            return (Space) super.next();
        }
    }
}