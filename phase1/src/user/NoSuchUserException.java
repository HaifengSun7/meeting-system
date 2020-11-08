package user;

import java.lang.Exception;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String errorMessage) {
        super(errorMessage);
    }
}
