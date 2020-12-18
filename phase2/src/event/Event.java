package event;


import event.exceptions.TooManySpeakerException;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Stores properties of events, with default 1 hour as length of the event. getAttendees and toString
 */

public abstract class Event {

    private static int eventNumber = 0;
    protected final int id;
    private final Timestamp time;
    private final ArrayList<String> user_list;
    private final int length = 1;
    protected String type;
    protected boolean speakStatus = false;
    protected ArrayList<String> speakers = new ArrayList<>();
    private String description;
    private boolean vip = false;
    private int maximumPeople;

    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     *
     * @param time: The time the meeting begins.
     */
    public Event(Timestamp time) {
        this.time = time;
        this.id = eventNumber;
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
     * Get the name of the Speaker.
     *
     * @return the name of Speaker.
     */
    public abstract ArrayList<String> getSpeakers();

    /**
     * Set Speaker to the event.
     *
     * @param u: Speaker's name.
     * @throws TooManySpeakerException when the number of speaker exceeds maximum.
     */
    public abstract void setSpeaker(String u) throws TooManySpeakerException;

    /**
     * Get the type of event.
     *
     * @return the type of the Event in string.
     */
    public String getType() {
        return type;
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
     * Returns if the event is VIP only.
     *
     * @return boolean representing if this is VIP only.
     */
    public boolean getVip() {
        return vip;
    }

    /**
     * Sets whether the event is VIP-only.
     *
     * @param vip_status true for VIP only, false for non-VIP.
     */
    public void setVip(boolean vip_status) {
        vip = vip_status;
    }

    /**
     * Get's everything of the event.
     *
     * @return the event with all information in string.
     */
    public String toString() {
        String t = this.time.toString();
        StringBuilder speakerString = new StringBuilder();
        for (String s : this.speakers) {
            speakerString.append(s);
            speakerString.append(",");
        }
        String sub;
        if (speakerString.length() != 0) {
            sub = speakerString.substring(0, speakerString.length() - 1);
        } else {
            sub = "null";
        }
        String output_id;
        if (this.id <10){
            output_id = "0"+ this.id;
        } else {
            output_id = String.valueOf(this.id);
        }
        return "Event {" + "Id: " + output_id +
                ", Type:" + this.type +
                ", Description: " + this.description +
                ", Time: " + t +
                ", Maximum people: " + maximumPeople +
                ", Speaker: " + sub +
                ", VIP only: " + vip +
                ", Attendees: " + this.getAttendees() + "}";
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
     * Get the time of the event.
     *
     * @return the time of event, in form: "yyyy-mm-dd hr:mm:ss.f"
     */
    public String getTime() {
        return this.time.toString();
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
     * Check if the event contradicts the other event in time.
     *
     * @param start:  start time, length: length add in the start time.
     * @param length: The length of the event.
     * @return A boolean showing if the two events contradicts. true for contradict.
     */
    public boolean contradicts(Timestamp start, float length) {
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
     * get the maximum number of people in the event.
     *
     * @return an integer of the maximum number of people in the event.
     */
    public int getMaximumAttendee() {
        return maximumPeople;
    }

    /**
     * set the maximum number of people in the event.
     *
     * @param number the number of maximum people.
     */
    public void setMaximumAttendee(int number) {
        this.maximumPeople = number;
    }

    /**
     * Get the maximum number of speakers.
     *
     * @return the number of speakers in int.
     */
    public abstract int getMaximumSpeaker();
}
