package event;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MultiEvent extends Event {
    private final int max_speakers;
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public MultiEvent(Timestamp time, int numSpeakers) {
        super(time);
        this.type = "Multi";
        max_speakers = numSpeakers;
    }

    @Override
    public ArrayList<String> getSpeakers(){
        return speakers;
    }

    @Override
    public void setSpeaker(String u) {
        if(speakers.size()<max_speakers){
            speakers.add(u);
        }
        this.speakStatus = true;
    }

    @Override
    public int getMaximumSpeaker(){
        return max_speakers;
    }
}
