package rpis81.chuprov.oop.model.classes;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomizableIterator<T> implements Iterator<T> {

    private static final int INITIAL_VALUE = 0;

    private T[] objects;
    private int index;

    public CustomizableIterator(T[] objects) {
        this.objects = objects;
        this.index = INITIAL_VALUE;
    }

    public boolean hasNext() {
        return index < objects.length;
    }

    public T next() {
        if(hasNext()) {
            return objects[index++];
        }
        throw new NoSuchElementException("Элементов больше не осталось");
    }
}