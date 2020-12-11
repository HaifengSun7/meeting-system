package event;

import java.util.ArrayList;

/**
 * Conference that contains several events.
 */
public class Conference {

    private final String name;
    private final ArrayList<Integer> events;

    /**
     * Create a new conference.
     *
     * @param conferenceName the name of the conference.
     */
    public Conference(String conferenceName) {
        this.name = conferenceName;
        this.events = new ArrayList<>();
    }

    /**
     * Add an event to the conference.
     *
     * @param eventID the ID of the event.
     */
    protected void addEvent(int eventID) {
        this.events.add(eventID);
    }

    /**
     * Cancel an event.
     *
     * @param eventID the ID of the event.
     */
    protected void cancelEvent(int eventID) {
        this.events.remove(events.indexOf(eventID));
    }

    /**
     * Get the event of the conference.
     *
     * @return The event IDs of events in this conference.
     */
    protected ArrayList<Integer> getEvents() {
        return this.events;
    }

    /**
     * Get whether the event is in this conference.
     *
     * @param eventID id of the event we are looking for.
     * @return the boolean whether the event is in the conference.
     */
    protected boolean hasEvent(int eventID) {
        return events.contains(eventID);
    }

    /**
     * Get the name of the conference.
     *
     * @return the name of conference.
     */
    protected String getName() {
        return name;
    }

}
