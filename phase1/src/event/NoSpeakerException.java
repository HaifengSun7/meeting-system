package event;

import java.lang.Exception;

public class NoSpeakerException extends Exception{
    public NoSpeakerException(String errorMessage) {
        super(errorMessage);
    }
}
