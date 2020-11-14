package event;

public class RoomIsFullException extends Exception {
    public RoomIsFullException(String errorMessage) {
        super(errorMessage);
    }
}