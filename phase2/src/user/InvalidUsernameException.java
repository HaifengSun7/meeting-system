package user;

/**
 * When the username contains characters that cannot be recognized or used.
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String errorMessage) {
        super(errorMessage);
    }
}
