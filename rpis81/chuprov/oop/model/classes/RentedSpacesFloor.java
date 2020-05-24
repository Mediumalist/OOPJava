package rpis81.chuprov.oop.model.classes;


import rpis81.chuprov.oop.model.interfaces.Floor;
import rpis81.chuprov.oop.model.interfaces.InstanceHandler;
import rpis81.chuprov.oop.model.interfaces.Space;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
        int initialSize = size();
        Node previousNode, node = head;
        while(node.getNext() != head) {
            previousNode = node;
            node = node.getNext();
            node.setPrevious(previousNode);
            node.getPrevious().setNext(node);
        }
        node.setNext(new Node(node, head, Objects.requireNonNull(space, "Параметр space не должен быть null")));
        size++;
        return initialSize < size();
    }

    @Override
    public boolean add(int index, Space space) {
        int initialSize = size();
        shift(index, false);
        getNode(index).setValue(Objects.requireNonNull(space, "Параметр space не должен быть null"));
        return initialSize < size();
    }

    @Override
    public Space get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getValue();
    }

    private Node getNode(int index) {
        Node node = head;
        for(int i = 0; i < index; i++) {
            node = (node.getNext() != null) ? node.getNext() : node;
        }
        return node;
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
    public Space[] toArray() {
        Space[] spaces = new Space[size];
        for(int i = 0; i < size; i++) {
            spaces[i] = get(i);
        }
        return Arrays.stream(spaces).filter(Objects::nonNull).toArray(Space[]::new);
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
            insertedNode.setNext(node);
            if(index == 0) {
                insertedNode.setPrevious(insertedNode);
                head = insertedNode;
                getNode(size).setNext(head);
                node.setPrevious(head);
            }
            else {
                previousNode.setNext(insertedNode);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Rented spaces:\n");
        iterator().forEachRemaining(space -> builder.append(space.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public int hashCode() {
        AtomicInteger code = new AtomicInteger(53 * size);
        iterator().forEachRemaining(space -> code.updateAndGet(value -> value ^ space.hashCode()));
        return code.get();
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
        return size == other.size && Objects.deepEquals(toArray(), other.toArray());
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return deployBuilder()
                    .setHead(head)
                    .setSize(size)
                    .build();
        }
    }

    public static Builder deployBuilder() {
        return new RentedSpacesFloor().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setHead(Node head) {
            RentedSpacesFloor.this.head = head;
            return this;
        }

        Builder setSize(int size) {
            RentedSpacesFloor.this.size = size;
            return this;
        }

        RentedSpacesFloor build() {
            return RentedSpacesFloor.this;
        }
    }
}