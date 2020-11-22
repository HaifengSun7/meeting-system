package event;

/**
 * Throws when the organizer tries to add speaker to an event that has a speaker.
 */
public class AlreadyHasSpeakerException extends Exception {
    public AlreadyHasSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
