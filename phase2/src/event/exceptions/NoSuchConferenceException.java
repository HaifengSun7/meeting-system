package event.exceptions;

/**
 * Throws when there is no such Conference.
 */
public class NoSuchConferenceException extends Exception {
    public NoSuchConferenceException(String errorMessage) {
        super(errorMessage);
    }
}
