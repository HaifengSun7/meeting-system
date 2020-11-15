package user;

public class WrongLogInException extends Exception{
    public WrongLogInException(String errorMessage) {
        super(errorMessage);
    }
}
