package event;


import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Stores properties of events, with default 1 hour as length of the event. getAttendees and toString
 *
 * @author Dechen Han, Shaohong Chen.
 * @version 1.0.3
 */
//TODO: REMEMBER TO CHANGE THE AUTHOR AND REMOVE THE TODOS.
public class Event {

    private static int eventNumber = 0;
    private final Timestamp time;
    private int length = 1;
    private final int id;
    private final ArrayList<String> user_list;
    private String speaker;
    private String description;
    private boolean speakStatus = false;

    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time: The time the meeting begins.
     */
    public Event(Timestamp time) {
        //TODO: Constructor, The input of time is an input made by users in string.
        //The input time needs to be in the form of 05:00, which means with length 5.
        this.time = time;
        this.id = eventNumber; //To be used in other useCases and entities.
        eventNumber += 1;
        this.user_list = new ArrayList<>();
    }

    /**
     * Reset the event number to 0 so that it counts from 0 in the next time loading events.csv.
     */
    public static void resetID() {
        eventNumber = 0;
    }

    /**
     * Get's the list of attendees.
     *
     * @return the list of attendees name.
     */
    public ArrayList<String> getAttendees() {
        return user_list;
    }

    /**
     * Add attendee to the event.
     *
     * @param attendee: the attendee's name.
     */
    public void addAttendees(String attendee) {
        this.user_list.add(attendee);
    }

    /**
     * Remove the attendee from the event.
     *
     * @param attendee: the attendee's name.
     */
    public void removeAttendees(String attendee) {
        this.user_list.remove(attendee);
    }

    /**
     * Check if the event has a Speaker.
     *
     * @return true or false.
     */
    public boolean getSpeakStatus() {
        return speakStatus;
    }

    /**
     * Remove Speaker from the event.
     */
    public void removeSpeaker() {
        this.speaker = null;
        this.speakStatus = false;
    }

    /**
     * Get the name of the Speaker.
     *
     * @return the name of Speaker.
     * @throws NoSpeakerException when there is no speaker in the event.
     */
    public String getSpeaker() throws Exception {
        if (speakStatus) {
            return speaker;
        } else {
            throw new NoSpeakerException("NoSpeaker: at " + this.id);
        }
    }

    /**
     * Set Speaker to the event.
     *
     * @param u: Speaker's name.
     */
    public void setSpeaker(String u) {
        this.speaker = u;
        this.speakStatus = true;
    }

    /**
     * Get the description of the event.
     *
     * @return the description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the event.
     *
     * @param description: the description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get's everything of the event.
     *
     * @return the event with all information in string.
     */
    public String toString() {
        String t = this.time.toString();
        return "Event {" + "Id: " + this.getId() + ", Description: " + this.description + ", Time: " + t +
                ", Speaker: " + speaker + ", Attendees: " + this.getAttendees() + "}";
    }

    /**
     * Get the id of the event.
     *
     * @return the id of event in int.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the hour of the event time, in 24 hours, in int.
     *
     * @return the hour part of the time, in int.
     */
    public String getTime() {
        //String t = this.time.toString();
        //return "The meeting will begin on:"+t+".";
        return this.time.toString(); //For Write.
    }

    /**
     * Get the length of the Event.
     *
     * @return length of the Event lasts this hour(s), in int.
     */
    public int getMeetingLength() {
        return length;
    }

    /**
     * @param n: The wanted length of the Event.
     */
    public void setMeetingLength(int n) {
        this.length = n;
    }

    /**
     * Check if the event contradicts the other event in time.
     *
     * @param start: start time, length: length add in the start time.
     * @return A boolean showing if the two events contradicts. true for contradict.
     */
    public boolean contradicts(Timestamp start, int length) {
        if (this.time.compareTo(start) == 0) {
            return true;
        }
        long milliseconds = this.time.getTime() - start.getTime();
        float seconds = (float) milliseconds / 1000;
        float diff_h = Math.abs(seconds / 3600);
        if (this.time.compareTo(start) < 0) {
            return this.length > diff_h;
        } else {
            return length > diff_h;
        }
    }

    /**
     * Get the length of the event.
     *
     * @return the length of the event.
     */
    private int getLength() {
        return this.length;
    }

}
