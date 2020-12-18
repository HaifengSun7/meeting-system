package event;

import event.exceptions.TooManySpeakerException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * An event without speakers.
 */
public class PartyEvent extends Event {
    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     */
    public PartyEvent(Timestamp time) {
        super(time);
        this.type = "Party";
    }

    /**
     * Get an empty list. Since there is no speaker.
     *
     * @return an empty arraylist.
     */
    @Override
    public ArrayList<String> getSpeakers() {
        return new ArrayList<>();
    }

    /**
     * Throws an exception, since it does not allow speaker.
     *
     * @param u: Speaker's name.
     * @throws TooManySpeakerException when being called.
     */
    @Override
    public void setSpeaker(String u) throws TooManySpeakerException {
        throw new TooManySpeakerException("This is a party event");
    }

    /**
     * Get maximum number of speakers allowed (0).
     * @return 0, since there's no speaker.
     */
    @Override
    public int getMaximumSpeaker() {
        return 0;
    }
}
