package event.exceptions;

public class TooManySpeakerException extends Exception{
    public TooManySpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
