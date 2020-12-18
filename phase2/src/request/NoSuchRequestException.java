package request;

/**
 * Throw an exception when the request cannot be found.
 */
public class NoSuchRequestException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSuchRequestException(String errorMessage) {
        super(errorMessage);
    }
}
