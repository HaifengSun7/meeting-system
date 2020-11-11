package event;

import java.lang.Exception;

public class AlreadyHasSpeakerException extends Exception {
    public AlreadyHasSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
