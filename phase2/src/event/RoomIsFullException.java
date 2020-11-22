package event;

/**
 * Throws when user trying to add user to an event of a full room.
 */
public class RoomIsFullException extends Exception {
    public RoomIsFullException(String errorMessage) {
        super(errorMessage);
    }
}