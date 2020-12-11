package event.exceptions;

/**
 * Throws when the event contradicts with another event.
 */
public class TimeNotAvailableException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public TimeNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
