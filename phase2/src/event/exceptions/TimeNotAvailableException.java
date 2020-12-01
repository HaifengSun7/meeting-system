package event.exceptions;

/**
 * Throws when the event contradicts with another event.
 */
public class TimeNotAvailableException extends Exception {
    public TimeNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
