package event;

/**
 * Throws when there is no such speaker.
 */
public class NoSpeakerException extends Exception {
    public NoSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
