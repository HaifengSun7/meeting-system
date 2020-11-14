package event;

public class NoSuchEventException extends Exception {
    public NoSuchEventException(String errorMessage) {
        super(errorMessage);
    }
}