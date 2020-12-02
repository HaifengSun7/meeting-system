package event;

import event.exceptions.TooManySpeakerException;

import java.sql.Timestamp;
import java.util.ArrayList;

public class SingleEvent extends Event{
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public SingleEvent(Timestamp time) {
        super(time);
        this.type = "Single";
    }

    @Override
    public ArrayList<String> getSpeakers(){
        return speakers;
    }


    @Override
    public void setSpeaker(String u) throws TooManySpeakerException {
        if(this.speakStatus){
            throw new TooManySpeakerException("Already has speaker");
        }
        speakers = new ArrayList<String>();
        speakers.add(u);
        this.speakStatus = true;
    }

    @Override
    public int getMaximumSpeaker(){
        return 1;
    }

}
