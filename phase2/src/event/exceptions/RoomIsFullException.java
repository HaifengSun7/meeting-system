package event.exceptions;

/**
 * Throws when user trying to add user to an event of a full room.
 */
public class RoomIsFullException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public RoomIsFullException(String errorMessage) {
        super(errorMessage);
    }
}