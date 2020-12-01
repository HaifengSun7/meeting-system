package request;

public class InvalidTitleException extends Exception{
    public InvalidTitleException(String errorMessage) {
        super(errorMessage);
    }
}
