package event.exceptions;

/**
 * Throws when there is no such type.
 */
public class NoSuchTypeException extends Exception {
    public NoSuchTypeException(String errorMessage) {
        super(errorMessage);
    }
}
