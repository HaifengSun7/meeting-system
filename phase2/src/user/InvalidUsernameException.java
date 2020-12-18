package user;

/**
 * When the username contains characters that cannot be recognized or used.
 */
public class InvalidUsernameException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public InvalidUsernameException(String errorMessage) {
        super(errorMessage);
    }
}
