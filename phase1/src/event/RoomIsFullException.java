package event;

import java.lang.Exception;

public class RoomIsFullException extends Exception {
    public RoomIsFullException(String errorMessage) {
        super(errorMessage);
    }
}