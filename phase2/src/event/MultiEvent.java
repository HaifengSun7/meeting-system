package event;

import event.exceptions.TooManySpeakerException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * An event with multiple speakers.
 */
public class MultiEvent extends Event {
    private final int max_speakers;

    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time : The time the meeting begins.
     * @param numSpeakers: The designed number of speakers of the event.
     */
    public MultiEvent(Timestamp time, int numSpeakers) {
        super(time);
        this.type = "Multi";
        max_speakers = numSpeakers;
    }

    /**
     * Get all speakers of the event.
     *
     * @return a list containing all speakers' usernames.
     */
    @Override
    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    /**
     * Set the speaker for the event.
     *
     * @param u: Speaker's name.
     * @throws TooManySpeakerException when num of speakers have reached maximum.
     */
    @Override
    public void setSpeaker(String u) throws TooManySpeakerException {
        if (speakers.size() < max_speakers & !speakers.contains(u)) {
            speakers.add(u);
        } else {
            throw new TooManySpeakerException("Number of speakers exceeds maximum or speaker already exists.");
        }
        this.speakStatus = true;
    }

    /**
     * Get the maximum number of speakers.
     *
     * @return the maximum number of speakers allowed to have in the event.
     */
    @Override
    public int getMaximumSpeaker() {
        return max_speakers;
    }
}
