package event;

import event.exceptions.NoSuchConferenceException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The manager of conferences. Only works in event manager.
 */
public class ConferenceManager {

    protected HashMap<String, Conference> map;

    /**
     * Constructs the manager. Create a map with conference name as key and conference as value.
     */
    protected ConferenceManager() {
        map = new HashMap<>();
    }

    /**
     * Creates a new conference.
     *
     * @param name the name of the new conference.
     */
    protected void createConference(String name) {
        Conference added = new Conference(name);
        map.put(name, added);
    }

    /**
     * Add an event to a conference.
     *
     * @param conferenceName the name of the conference.
     * @param eventID        the id of the event.
     * @throws NoSuchConferenceException when the conference name is not available.
     */
    protected void addEvent(String conferenceName, int eventID) throws NoSuchConferenceException {
        try {
            map.get(conferenceName).addEvent(eventID);
        } catch (Exception e) {
            throw new NoSuchConferenceException("Conference does not exist.");
        }
    }

    /**
     * Cancel an event.
     *
     * @param conferenceName the name of the conference.
     * @param eventID        the id of the event.
     * @throws NoSuchConferenceException when the conference name is not available.
     */
    protected void cancelEvent(String conferenceName, int eventID) throws NoSuchConferenceException {
        try {
            map.get(conferenceName).cancelEvent(eventID);
        } catch (Exception e) {
            throw new NoSuchConferenceException("Conference does not exist.");
        }
    }

    /**
     * Get the events in an array list of their ID's of the conference
     *
     * @param conferenceName the name of the conference that we are looking for.
     * @return An array list of event ids in that conference.
     * @throws NoSuchConferenceException when the conference name given is not available.
     */
    protected ArrayList<Integer> getEventOfConference(String conferenceName) throws NoSuchConferenceException {
        try {
            return map.get(conferenceName).getEvents();
        } catch (Exception e) {
            throw new NoSuchConferenceException("Conference does not exist.");
        }
    }

    /**
     * Get the name of all conferences.
     *
     * @return An array list of all conference names.
     */
    protected ArrayList<String> getAllConferences() {
        return new ArrayList<>(map.keySet());
    }

    /**
     * Check if the conference is available.
     *
     * @param conferenceName The name of the conference that we are looking for.
     * @return whether the conference is available.
     */
    protected boolean hasConference(String conferenceName) {
        return map.containsKey(conferenceName);
    }

    /**
     * Get the conference name of an event.
     *
     * @param eventID the event that we are looking for.
     * @return the conference name of the event.
     */
    protected String getConferenceOfEvent(int eventID) {
        for (Conference c : map.values()) {
            if (c.hasEvent(eventID)) {
                return c.getName();
            }
        }
        return null;
    }
}
