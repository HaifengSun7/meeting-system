package event;

import java.sql.Timestamp;

public class SingleEvent extends Event{
    protected String speaker;
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
    public String getSpeaker() throws NoSpeakerException {
        if (speakStatus) {
            return speaker;
        }
        else {
            throw new NoSpeakerException("No Speaker" + this.id);
        }
    }


    @Override
    public void setSpeaker(String u) {
        this.speaker = u;
        this.speakStatus = true;
    }

}
