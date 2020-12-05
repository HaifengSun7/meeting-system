package request;
/**
 * Throw an exception when the request cannot be found.
 */
public class NoSuchRequestException extends Exception{
    public NoSuchRequestException(String errorMessage) {
        super(errorMessage);
    }
}
