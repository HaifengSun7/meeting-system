package event;

public class TimeNotAvailableException extends Exception{
    public TimeNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
