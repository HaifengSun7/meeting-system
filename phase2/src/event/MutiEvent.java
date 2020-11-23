package event;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MutiEvent extends Event {
    protected ArrayList<String> speaker;
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public MutiEvent(Timestamp time) {
        super(time);
        this.type = "Muti";
    }

    @Override
    public String getSpeaker() throws NoSpeakerException {
        if (speakStatus) {
            StringBuilder l = new StringBuilder();
            for(String i:speaker){
                l.append(i).append(", ");
            }
            return l.substring(0, l.length() - 2);
        }
        else {
            throw new NoSpeakerException("No Speaker" + this.id);
        }
    }

    @Override
    public void setSpeaker(String u) {
        speaker.add(u);
        this.speakStatus = true;
    }
}
