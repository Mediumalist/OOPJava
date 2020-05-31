package rpis81.chuprov.oop.model;

public class NoRentedSpaceException extends Exception {

    public NoRentedSpaceException() {
        super();
    }

    public NoRentedSpaceException(String message) {
        super(message);
    }

    public NoRentedSpaceException(String message, Throwable object) {
        super(message, object);
    }
}