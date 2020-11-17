package user;

/**
 * Throws when the program cannot find the user in user list.
 */
public class NoSuchUserException extends Exception {
    public NoSuchUserException(String errorMessage) {
        super(errorMessage);
    }
}
