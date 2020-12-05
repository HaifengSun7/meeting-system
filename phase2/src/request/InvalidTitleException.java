package request;

/**
 * Throw an exception when the request title repeated.
 */
public class InvalidTitleException extends Exception{
    public InvalidTitleException(String errorMessage) {
        super(errorMessage);
    }
}
