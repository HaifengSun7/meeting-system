package request;

/**
 * Throw an exception when the request title repeated.
 */
public class InvalidTitleException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public InvalidTitleException(String errorMessage) {
        super(errorMessage);
    }
}
