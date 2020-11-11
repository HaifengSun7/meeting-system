package system;

import event.InvalidUserException;

public class InvalidUserTypeException extends Exception{
    public InvalidUserTypeException(String errorMessage) {
        super(errorMessage);
    }
}
