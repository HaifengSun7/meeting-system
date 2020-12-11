package event.exceptions;

/**
 * Throws when the organizer tries to add speaker to an event that has a speaker.
 */
public class AlreadyHasSpeakerException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public AlreadyHasSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
