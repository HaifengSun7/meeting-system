package event;

import java.sql.Timestamp;

public class PartyEvent  extends Event {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public PartyEvent(Timestamp time) {
        super(time);
    }
}
