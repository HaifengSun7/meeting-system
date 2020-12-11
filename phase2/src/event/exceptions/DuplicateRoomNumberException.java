package event.exceptions;

/**
 * Throws when the organizer tries to add a room with a room number that already exists.
 */
public class DuplicateRoomNumberException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public DuplicateRoomNumberException(String errorMessage) {
        super(errorMessage);
    }
}
