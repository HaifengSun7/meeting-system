package event;

import java.lang.Exception;

public class DuplicateRoomNoException extends Exception{
    public DuplicateRoomNoException(String errorMessage) {
            super(errorMessage);
        }
}
