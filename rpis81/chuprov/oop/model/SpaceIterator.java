package rpis81.chuprov.oop.model;

import java.util.Iterator;

public class SpaceIterator extends ObjectIterator implements Iterator<Space> {

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