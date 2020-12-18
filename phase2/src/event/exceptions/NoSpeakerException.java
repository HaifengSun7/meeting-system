package event.exceptions;

/**
 * Throws when there is no such speaker.
 */
public class NoSpeakerException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public NoSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
