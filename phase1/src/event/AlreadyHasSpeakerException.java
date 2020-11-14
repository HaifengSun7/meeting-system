package event;

public class AlreadyHasSpeakerException extends Exception {
    public AlreadyHasSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
