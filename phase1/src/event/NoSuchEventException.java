package event;

import java.lang.Exception;

public class NoSuchEventException extends Exception {
    public NoSuchEventException(String errorMessage) {
        super(errorMessage);
    }
}