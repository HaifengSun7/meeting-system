package user;

/**
 * Throws when the program cannot find the user in user list.
 */
public class NoSuchUserException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSuchUserException(String errorMessage) {
        super(errorMessage);
    }
}
