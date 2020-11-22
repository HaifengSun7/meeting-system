package event;

import java.sql.Timestamp;

public class MutiEvent extends Event {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public MutiEvent(Timestamp time) {
        super(time);
        this.type = "Muti";
    }
}
