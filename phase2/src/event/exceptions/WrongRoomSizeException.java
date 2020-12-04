package event.exceptions;

/**
 * Throws when the room size less than 1.
 */
public class WrongRoomSizeException extends Exception {
    public WrongRoomSizeException(String errorMessage) {
        super(errorMessage);
    }
}
