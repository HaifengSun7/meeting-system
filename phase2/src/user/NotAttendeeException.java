package user;

public class NotAttendeeException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NotAttendeeException(String errorMessage) {
        super(errorMessage);
    }
}
