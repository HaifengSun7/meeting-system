package event.exceptions;

/**
 * Throws when the room size less than 1.
 */
public class WrongRoomSizeException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public WrongRoomSizeException(String errorMessage) {
        super(errorMessage);
    }
}
