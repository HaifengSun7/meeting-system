package user;

public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String errorMessage) {
        super(errorMessage);
    }
}