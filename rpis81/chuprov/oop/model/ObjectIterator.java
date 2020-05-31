package rpis81.chuprov.oop.model;

import java.util.NoSuchElementException;

public class ObjectIterator {

    private static final int INITIAL_VALUE = 0;

    private Object[] objects;
    private int index;

    ObjectIterator(Object[] objects) {
        this.objects = objects;
        this.index = INITIAL_VALUE;
    }

    public boolean hasNext() {
        return index < objects.length;
    }

    public Object next() {
        if(hasNext()) {
            return objects[index++];
        }
        throw new NoSuchElementException("Элементов больше не осталось");
    }
}