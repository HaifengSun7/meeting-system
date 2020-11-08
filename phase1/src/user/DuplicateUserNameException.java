package user;

import java.lang.Exception;

public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String errorMessage) {
        super(errorMessage);
    }
}