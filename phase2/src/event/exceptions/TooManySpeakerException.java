package event.exceptions;

/**
 * Throws when there are too many speakers more than maximum.
 */
public class TooManySpeakerException extends Exception {
    public TooManySpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
