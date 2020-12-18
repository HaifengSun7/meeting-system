package event.exceptions;

/**
 * Throws when the new maximum number of people of an event is less than the existing attendees of that event.
 */
public class InvalidNewMaxNumberException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public InvalidNewMaxNumberException(String errorMessage) {
        super(errorMessage);
    }
}
