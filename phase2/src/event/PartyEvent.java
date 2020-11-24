package event;

import java.sql.Timestamp;
import java.util.ArrayList;

public class PartyEvent  extends Event {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public PartyEvent(Timestamp time) {
        super(time);
        this.type = "Party";
    }

    @Override
    public ArrayList<String> getSpeakers(){
        return new ArrayList<>();
    }

    @Override
    public void setSpeaker(String u) {
    }

    @Override
    public int getMaximumSpeaker(){
        return 0;
    }
}