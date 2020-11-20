package user;

/**
 * When Organizer tries to create a user account with a username that already exists.
 */
public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String errorMessage) {
        super(errorMessage);
    }
}