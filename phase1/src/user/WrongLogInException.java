package user;

/**
 * Throws when the user provides the wrong username or password when logging in.
 */
public class WrongLogInException extends Exception{
    public WrongLogInException(String errorMessage) {
        super(errorMessage);
    }
}
