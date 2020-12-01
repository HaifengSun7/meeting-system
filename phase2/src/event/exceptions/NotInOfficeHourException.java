package event.exceptions;

/**
 * Throws when the event is not between 9am to 5pm.
 */
public class NotInOfficeHourException extends Exception {
    public NotInOfficeHourException(String errorMessage) {
        super(errorMessage);
    }
}
