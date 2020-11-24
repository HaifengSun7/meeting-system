package event;

public class NoSuchTypeException extends Exception {
    public NoSuchTypeException(String errorMessage) {
        super(errorMessage);
    }
}
