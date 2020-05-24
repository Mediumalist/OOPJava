package rpis81.chuprov.oop.model.classes;

import rpis81.chuprov.oop.model.interfaces.Space;

public class Node implements Cloneable {

    private Node previous;
    private Node next;
    private Space value;

    public Node(Node previous, Node next, Space value) {
        this.previous = previous;
        this.next = next;
        this.value = value;
    }

    public Node(Space value) {
        this.previous = this;
        this.next = this;
        this.value = value;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Space getValue() {
        return value;
    }

    public void setValue(Space value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}