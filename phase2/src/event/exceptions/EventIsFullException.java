package event.exceptions;

/**
 * Throws when user trying to add user to a full people event.
 */
public class EventIsFullException extends Exception {
    public EventIsFullException(String errorMessage) {
        super(errorMessage);
    }
}
