package event;

import event.exceptions.TooManySpeakerException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * An event with only one speaker.
 */
public class SingleEvent extends Event {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public SingleEvent(Timestamp time) {
        super(time);
        this.type = "Single";
    }

    /**
     * Get the speaker of the event.
     *
     * @return An array list with only element being the speaker.
     */
    @Override
    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    /**
     * Set the speaker of the event.
     *
     * @param u: Speaker's name.
     * @throws TooManySpeakerException when the event already has a speaker.
     */
    @Override
    public void setSpeaker(String u) throws TooManySpeakerException {
        if (this.speakStatus) {
            throw new TooManySpeakerException("Already has speaker");
        }
        speakers = new ArrayList<>();
        speakers.add(u);
        this.speakStatus = true;
    }

    /**
     * get the maximum number of speaker allowed.
     *
     * @return 1, since it is single event.
     */
    @Override
    public int getMaximumSpeaker() {
        return 1;
    }

}
