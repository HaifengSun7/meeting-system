package event;

public class InvalidUserException extends Exception {
    public InvalidUserException(String errorMessage) {
        super(errorMessage);
    }
}
