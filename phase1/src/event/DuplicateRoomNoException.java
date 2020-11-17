package event;

/**
 * Throws when the organizer tries to add a room with a room number that already exists.
 */
public class DuplicateRoomNoException extends Exception {
    public DuplicateRoomNoException(String errorMessage) {
        super(errorMessage);
    }
}
