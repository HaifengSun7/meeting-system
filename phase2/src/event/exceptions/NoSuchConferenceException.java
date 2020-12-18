package event.exceptions;

/**
 * Throws when there is no such Conference.
 */
public class NoSuchConferenceException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSuchConferenceException(String errorMessage) {
        super(errorMessage);
    }
}
