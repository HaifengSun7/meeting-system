package event;

public class NotInOfficeHourException extends Exception{
    public NotInOfficeHourException(String errorMessage) {
        super(errorMessage);
    }
}
