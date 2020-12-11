package message;

/**
 * Exception thrown when there is no message matching the search.
 */
public class NoSuchMessageException extends Exception {
    /**
     * Throws the exception.
     *
     * @param errorMessage The error Message of the specific cases.
     */
    public NoSuchMessageException(String errorMessage) {
        super(errorMessage);
    }
}
