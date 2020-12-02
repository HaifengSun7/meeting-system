package event.exceptions;

public class NoSuchConferenceException extends Exception{
    public NoSuchConferenceException(String errorMessage) {
        super(errorMessage);
    }
}
