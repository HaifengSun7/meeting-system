package user;

/**
 * Throws when the user provides the wrong username or password when logging in.
 */
public class WrongLogInException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public WrongLogInException(String errorMessage) {
        super(errorMessage);
    }
}
