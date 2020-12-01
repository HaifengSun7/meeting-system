package request;

public class NoSuchRequestException extends Exception{
    public NoSuchRequestException(String errorMessage) {
        super(errorMessage);
    }
}
