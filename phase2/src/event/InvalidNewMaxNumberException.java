package event;

/**
 * Throws when the new maximum number of people of an event is less than the existing attendees of that event.
 */
public class InvalidNewMaxNumberException extends Exception {
    public InvalidNewMaxNumberException(String errorMessage) {
        super(errorMessage);
    }
}
