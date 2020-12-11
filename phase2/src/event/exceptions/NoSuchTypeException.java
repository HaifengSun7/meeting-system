package event.exceptions;

/**
 * Throws when there is no such type.
 */
public class NoSuchTypeException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSuchTypeException(String errorMessage) {
        super(errorMessage);
    }
}
