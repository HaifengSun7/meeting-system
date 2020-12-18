package event.exceptions;

/**
 * Throws when there are too many speakers more than maximum.
 */
public class TooManySpeakerException extends Exception {
    /**
     * Constructs the exception.
     *
     * @param errorMessage the exception of specific case.
     */
    public TooManySpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
