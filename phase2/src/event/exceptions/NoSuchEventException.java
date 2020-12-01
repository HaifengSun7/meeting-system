package event.exceptions;

/**
 * Throws when there is no such event.
 */
public class NoSuchEventException extends Exception {
    public NoSuchEventException(String errorMessage) {
        super(errorMessage);
    }
}