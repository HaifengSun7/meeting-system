package event.exceptions;

/**
 * Throws when there is no such event.
 */
public class NoSuchEventException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSuchEventException(String errorMessage) {
        super(errorMessage);
    }
}