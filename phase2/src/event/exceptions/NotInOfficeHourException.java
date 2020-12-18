package event.exceptions;

/**
 * Throws when the event is not between 9am to 5pm.
 */
public class NotInOfficeHourException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NotInOfficeHourException(String errorMessage) {
        super(errorMessage);
    }
}
