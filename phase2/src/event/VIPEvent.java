package event;

import java.sql.Timestamp;
import java.util.ArrayList;

public class VIPEvent  extends SingleEvent {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public VIPEvent(Timestamp time) {
        super(time);
        this.type = "VIP";
    }

    @Override
    public ArrayList<String> getSpeakers(){
        return speakers;
    }

    @Override
    public void setSpeaker(String u) {
        speakers = new ArrayList<String>();
        speakers.add(u);
        this.speakStatus = true;
    }

    @Override
    public int getMaximumSpeaker(){
        return 1;
    }
}