package event;

/**
 * Throws when the organizer tries to add a room with a room number that already exists.
 */
public class DuplicateRoomNumberException extends Exception {
    public DuplicateRoomNumberException(String errorMessage) {
        super(errorMessage);
    }
}
