package event.exceptions;

/**
 * Throws when user trying to add user to a full people event.
 */
public class EventIsFullException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public EventIsFullException(String errorMessage) {
        super(errorMessage);
    }
}
