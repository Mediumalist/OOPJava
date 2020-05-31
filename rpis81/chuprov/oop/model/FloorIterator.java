package rpis81.chuprov.oop.model;

import java.util.Iterator;

public class FloorIterator extends ObjectIterator implements Iterator<Floor> {

    public FloorIterator(Floor[] floors) {
        super(floors);
    }

    @Override
    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    public Floor next() {
        return (Floor) super.next();
    }
}