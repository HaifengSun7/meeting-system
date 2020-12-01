package event.exceptions;

/**
 * Throws when the user does not exist.
 */
public class InvalidUserException extends Exception {
    public InvalidUserException(String errorMessage) {
        super(errorMessage);
    }
}
