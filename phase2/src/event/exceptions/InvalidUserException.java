package event.exceptions;

/**
 * Throws when the user does not exist.
 */
public class InvalidUserException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public InvalidUserException(String errorMessage) {
        super(errorMessage);
    }
}
