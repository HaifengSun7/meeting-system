package user;

/**
 * When Organizer tries to create a user account with a username that already exists.
 */
public class DuplicateUserNameException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public DuplicateUserNameException(String errorMessage) {
        super(errorMessage);
    }
}