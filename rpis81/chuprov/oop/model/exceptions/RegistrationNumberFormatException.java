package rpis81.chuprov.oop.model.exceptions;

public class RegistrationNumberFormatException extends RuntimeException {

    public RegistrationNumberFormatException() {
        super();
    }

    public RegistrationNumberFormatException(String message) {
        super(message);
    }

    public RegistrationNumberFormatException(Throwable object, String message) {
        super(message, object);
    }
}