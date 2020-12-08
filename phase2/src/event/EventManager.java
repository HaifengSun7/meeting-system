package event;

import event.exceptions.*;
import javafx.util.Pair;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The manager that manages the scheduling of events with their rooms.
 */
public class EventManager {

    private final Map<Integer, Event> map = new HashMap<>();
    private final RoomManager roomManager = new RoomManager();
    private final ConferenceManager conferenceManager = new ConferenceManager();

    /**
     * Initializes the event manager. It goes through the saved database.
     */
    public EventManager() {
        Event.resetID();
    }

    /**
     * Return all events in an ArrayList of Strings.
     *
     * @return all events. Index = event number.
     */
    public ArrayList<String> getAllEvents(String conference) throws NoSuchConferenceException {
        ArrayList<Integer> eventIDs = conferenceManager.getEventOfConference(conference);
        ArrayList<String> events = new ArrayList<>();
        for (int i : eventIDs) {
            events.add(map.get(i).toString());
        }
        return events;
    }

    /**
     * Get's the attendees of a particular event.
     *
     * @param eventId The id of the event that we are looking for.
     * @return A list of Attendees' usernames that the event has.
     */
    public ArrayList<String> getAttendees(String eventId) {
        return map.get(Integer.parseInt(eventId)).getAttendees();
    }

    /**
     * Get the events planned in a room.
     *
     * @param roomNumber The room number of the room that we are looking for.
     * @return The list of event in id's of the given room.
     * @throws InvalidActivityException When the room number given is not valid.
     */
    public ArrayList<Integer> getSchedule(int roomNumber) throws InvalidActivityException {
        return roomManager.getSchedule(roomNumber);
    }

    /**
     * Get the capacity of an event
     *
     * @param id the event ID.
     * @return a pair of integers that the first is number of speakers, while the second is the number of attendees.
     */
    public Pair<Integer, Integer> getCapacity(int id) {
        int numSpeaker = map.get(id).getMaximumSpeaker();
        int numPeople = map.get(id).getMaximumAttendee();
        return new Pair<>(numSpeaker, numPeople);
    }

    /**
     * Get a hash map that keys are room numbers and values are their capacities.
     *
     * @return A hashmap that maps room numbers to their capacities.
     */
    public HashMap<Integer, Integer> getRoomNumberMapToCapacity() {
        return roomManager.getRoomNumberMapToCapacity();
    }

    /**
     * Get a hashmap that keys are eventID and values are room numbers.
     *
     * @return a hashmap that keys are eventID and values are room numbers.
     */
    public HashMap<Integer, Integer> getEventIDMapToRoomNumber() {
        return roomManager.getEventIDMapToRoomNumber();
    }

    /**
     * Get the time of a particular event.
     *
     * @param eventNum the eventNumber of the event that we are looking for.
     * @return the time of the event with the eventNum
     */
    public String getTime(Integer eventNum) {
        return map.get(eventNum).getTime();
    }

    /**
     * Get the length of a particular event.
     *
     * @param eventNum the eventNumber of the event that we are looking for.
     * @return the length of the event with the eventNum
     */
    public String getDuration(Integer eventNum) {
        return String.valueOf(map.get(eventNum).getMeetingLength());
    }

    /**
     * Get the speaker of a particular event.
     *
     * @param eventNum the eventNumber of the event that we are looking for.
     * @return the speaker of the event with the eventNum
     */
    public ArrayList<String> getSpeakers(Integer eventNum) {
        try {
            return map.get(eventNum).getSpeakers();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Get all existing conference.
     *
     * @return the list of all conference names.
     */
    public ArrayList<String> getAllConference() {
        return conferenceManager.getAllConferences();
    }

    /**
     * Get the conference of event.
     *
     * @param eventID the event that we are looking for.
     * @return the conference name of the event.
     */
    public String getConferenceOfEvent(int eventID) {
        return conferenceManager.getConferenceOfEvent(eventID);
    }

    /**
     * Get if the event is VIP event.
     *
     * @param eventID the event we are looking for.
     * @return a boolean showing if it is VIP only.
     */
    public boolean getVipStatus(int eventID) {
        return map.get(eventID).getVip();
    }

    /**
     * Get the description from the event's id.
     *
     * @param event: event's id.
     * @return the description of the event.
     */
    public String getDescription(Integer event) {
        return map.get(event).getDescription();
    }

    public ArrayList<String> getAllRooms() {
        return roomManager.getAllRooms();
    }

    /**
     * Get the events that the attendee can sign up.
     *
     * @param attendee the attendee's username.
     * @return the array list containing all events in string, that the attendee can sign up.
     */
    public ArrayList<String> canSignUp(String attendee, String conference) {
        ArrayList<String> rslt = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            if (map.containsKey(i) && !map.get(i).getAttendees().contains(attendee)
                    && !this.dontHaveTime(attendee).contains(map.get(i).getTime())
                    && conferenceManager.getConferenceOfEvent(i).equals(conference)) {
                if (!map.get(i).getVip()) {
                    rslt.add(String.valueOf(map.get(i).getId()));
                }
            }
        }
        return rslt;
    }

    /**
     * Return a list of Events.toString() that attendee can sign up for.
     *
     * @param attendee Attendee, but string.
     * @return a list of Event ids in String that attendee can sign up for.
     */
    public ArrayList<String> canSignUp(String attendee, boolean vip, String conference) {
        ArrayList<String> rslt = new ArrayList<>();
        if (vip) {
            for (int i = 0; i < map.size(); i++) {
                if (map.containsKey(i) && !map.get(i).getAttendees().contains(attendee)
                        && !this.dontHaveTime(attendee).contains(map.get(i).getTime())
                        && conferenceManager.getConferenceOfEvent(i).equals(conference)) {
                    rslt.add(String.valueOf(map.get(i).getId()));
                }
            }
        } else {
            rslt = canSignUp(attendee,conference);
        }
        return rslt;
    }

    /*
     * Make attendee sign up for an event.
     *
     * @param event    Event id in string.
     * @param attendee Attendee, but with String.
     * @throws NoSuchEventException when the event does not exist.
     */
    private void signUp(String event, String attendee) throws NoSuchEventException {
        if (map.containsKey(Integer.parseInt(event))) {
            map.get(Integer.parseInt(event)).addAttendees(attendee);
        } else {
            throw new NoSuchEventException("This event does not exist: id: " + event);
        }
    }

    /**
     * Add a valid room to the conference.
     *
     * @param roomNumber An int representing the room number
     * @param size       An int representing the capacity of the room.
     * @throws DuplicateRoomNumberException when room number exists.
     */
    public void addRoom(int roomNumber, int size) throws DuplicateRoomNumberException, WrongRoomSizeException {
        roomManager.addRoom(roomNumber, size);
    }

    /**
     * Gives the string output of the event.
     *
     * @param id: The event id.
     * @return a Event based on its id, but with toString();
     */
    public String findEventStr(Integer id) {
        return map.get(id).toString();
    }

    /**
     * Make attendee a speaker by updating all events related with them.
     * Tips:
     * 1. scan all events with attendee.
     * 2. See if there is an speaker. If there is one already, We skip the event.
     * 3. make attendee speaker of all the rest of events.
     *
     * @param attendee Attendee but string.
     */
    public void becomeSpeaker(String attendee) throws TooManySpeakerException {
        ArrayList<Event> events = new ArrayList<>(this.map.values());
        ArrayList<Event> attended = new ArrayList<>();
        for (Event i : events) {
            if (i.getAttendees().contains(attendee)) {
                attended.add(i);
            }
        }
        for (Event j : attended) {
            j.setSpeaker(attendee);
        }
    }

//    public ArrayList<String> getLimitedEvents() {
//        ArrayList<String> events = new ArrayList<>();
//        for (int i = 0; i < map.size() - 1; i++) {
//            if (map.containsKey(i)) {
//                if (! map.get(i).getVip()) {
//                    events.add(map.get(i).toString());
//                }
//            } else {
//                events.add("cancelled");
//            }
//        }
//        return events;
//    }

    /**
     * Switch an event between VIP and normal event.
     *
     * @param eventId The event we are setting.
     * @param vip     if the event is VIP event.
     */
    public void switchVipEvent(String eventId, boolean vip) throws NoSuchEventException {
        if (map.containsKey(Integer.parseInt(eventId))) {
            map.get(Integer.parseInt(eventId)).setVip(vip);
        } else {
            throw new NoSuchEventException("There is not an event with event number " + eventId);
        }
    }

    /**
     * Add user to an event. MAKE SURE TO DOUBLE CHECK ALL CONDITIONS.
     *
     * @param type        type of user
     * @param username    username
     * @param eventNumber eventNumber.
     * @throws InvalidUserException if input type is "Organizer".
     * @throws NoSuchEventException if event corresponding to the input eventNumber does not exist.
     * @throws EventIsFullException if event is full.
     */
    public void addUserToEvent(String type, String username, int eventNumber) throws NoSuchEventException,
            InvalidUserException, EventIsFullException, TooManySpeakerException {
        int event_size = map.get(eventNumber).getAttendees().size();
        int maximumAttendee = map.get(eventNumber).getMaximumAttendee();
        if (map.containsKey(eventNumber)) {
            if (type.equals("Speaker")) {
                map.get(eventNumber).setSpeaker(username);
            } else if (type.equals("Attendee")) {
                if (event_size >= maximumAttendee) {
                    throw new EventIsFullException("Event: " + eventNumber + " is full of attendees! " +
                            "You can't sign up this event!");
                }
                signUp(String.valueOf(eventNumber), username);
            } else {
                throw new InvalidUserException("Invalid user type.");
            }
        } else {
            throw new NoSuchEventException("There is not an event with event number " + eventNumber);
        }
    }

    /**
     * set the maximum number of people in the selected event.
     *
     * @param newMaximum  the new maximum number of people.
     * @param eventNumber the event number of the selected event.
     * @throws NoSuchEventException         if event corresponding to the input eventNumber does not exist.
     * @throws InvalidNewMaxNumberException if the new maximum number of the event is less than the existing attendees in that event
     */
    public void setMaximumPeople(int roomNumber, int newMaximum, int eventNumber) throws NoSuchEventException,
            InvalidNewMaxNumberException, InvalidActivityException {
        int eventSize = map.get(eventNumber).getAttendees().size();
        int speakerSize = getSpeakers(eventNumber).size();
        if (map.containsKey(eventNumber) && this.getSchedule(roomNumber).contains(eventNumber)) {
            if (newMaximum >= (eventSize + speakerSize)) {
                map.get(eventNumber).setMaximumAttendee(newMaximum);
            } else {
                throw new InvalidNewMaxNumberException("Invalid input. The new maximum number of this event is " +
                        "less than the existing attendees in that event.");
            }
        } else {
            throw new NoSuchEventException("There is not an event with event number " + eventNumber);
        }
    }

    /**
     * Create and add a event.
     *
     * @param roomNo:        room number.
     * @param time:          time the meeting begins.
     * @param meetingLength: time length of the event.
     * @param description:   description of event
     * @param vip:           whether the event is for VIP only.
     * @throws NotInOfficeHourException  if time out of working hour.
     * @throws TimeNotAvailableException if time is not available.
     * @throws InvalidActivityException  if there's no such room.
     */
    public void addEvent(String roomNo, int numSpeakers, int numAttendees, Timestamp time, int meetingLength,
                         String description, String vip, String conferenceName)
            throws NotInOfficeHourException, TimeNotAvailableException, InvalidActivityException,
            RoomIsFullException, NoSuchConferenceException {
        if (!inOfficeHour(time)) {
            throw new NotInOfficeHourException("Invalid time." +
                    " Please enter time between 9:00 to 16:00 to ensure meeting ends before 17:00");
        }
        if (ifRoomAvailable(roomNo, time, meetingLength)) {
            Event newEvent = eventFactory(numSpeakers, time);
            if (!conferenceManager.hasConference(conferenceName)) {
                conferenceManager.createConference(conferenceName);
            }
            conferenceManager.addEvent(conferenceName, newEvent.getId());
            map.put(newEvent.getId(), newEvent);
            newEvent.setDescription(description);
            newEvent.setVip(Boolean.parseBoolean(vip));
            newEvent.setMaximumAttendee(numAttendees);
            roomManager.addEvent(Integer.parseInt(roomNo), newEvent.getId(), numAttendees + numSpeakers);
        } else {
            throw new TimeNotAvailableException("Failed to add event to room "
                    + roomNo + " : Time has been taken by other events.");
        }
    }

    /**
     * Cancel the certain Event.
     *
     * @param eventId: the id of the event in string.
     * @throws NoSuchEventException if cannot find event with given eventId.
     */
    public void cancelEvent(String eventId, String conferenceName) throws NoSuchEventException, NoSuchConferenceException {
        Integer i = Integer.parseInt(eventId);
        if (!map.containsKey(i)) {
            throw new NoSuchEventException("This event does not exist: id: " + eventId);
        }
//        ArrayList<String> attendees = this.getAttendees(eventId);
//        for(String a:attendees){
//            this.signOut(eventId, a);
//        }
        if (map.containsKey(Integer.parseInt(eventId))) {
            conferenceManager.cancelEvent(conferenceName, Integer.parseInt(eventId));
            map.remove(Integer.parseInt(eventId));
            roomManager.remove(Integer.parseInt(eventId));
        } else {
            throw new NoSuchEventException("NoSuchEvent: " + eventId);
        }
    }

    /**
     * Make attendee sign out for an event.
     *
     * @param eventId  Event id in String.
     * @param attendee Attendee with String.
     * @throws NoSuchEventException     when the event does not exist.
     * @throws InvalidActivityException when Attendee does not have that event.
     */
    public void signOut(String eventId, String attendee) throws InvalidActivityException, NoSuchEventException {
        if (map.containsKey(Integer.parseInt(eventId))) {
            if (map.get(Integer.parseInt(eventId)).getAttendees().contains(attendee)) {
                map.get(Integer.parseInt(eventId)).removeAttendees(attendee);
            } else {
                throw new InvalidActivityException();
            }
        } else {
            throw new NoSuchEventException("This event does not exist: id: " + eventId);
        }
    }

    /*
     * Return a list of times an attendee have signed up for events.
     *
     * @param attendee Attendee to string
     * @return list of times this attendee is busy
     */
    private ArrayList<String> dontHaveTime(String attendee) {
        ArrayList<String> res = new ArrayList<>();
        for (Integer key : map.keySet()) {
            if (map.get(key).getAttendees().contains(attendee)) {
                res.add(map.get(key).getTime());
            }
        }
        return res;
    }

//    /**
//     * Get a map that contains all events.
//     * @return the map<eventId, correspondingEvent>.
//     */
//    public Map<Integer, Event> getMap() {return this.map;}


    /*
     * Check if the time in office hour.
     *
     * @param time: time want to be checked.
     * @return true or false.
     */
    private boolean inOfficeHour(Timestamp time) {
        String t = time.toString();
        int hour = Integer.parseInt(String.valueOf(t.charAt(11)) + t.charAt(12));
        int min = Integer.parseInt(String.valueOf(t.charAt(14)) + t.charAt(15));
        int sec = Integer.parseInt(String.valueOf(t.charAt(17)) + t.charAt(18));
        if (hour >= 9) {
            if (hour == 16 && min == 0 && sec == 0) {
                return true;
            } else {
                return hour < 16;
            }
        }
        return false;
    }

    private boolean ifRoomAvailable(String roomNo, Timestamp time, int length) throws InvalidActivityException {
        try {
            for (int id : map.keySet()) {
                if (roomManager.getSchedule(Integer.parseInt(roomNo)).contains(id) &&
                        map.get(id).contradicts(time, length))
                    return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidActivityException("invalid room number: " + roomNo);
        }
    }

    private Event eventFactory(int numSpeakers, Timestamp time) {
        switch (numSpeakers) {
            case 0:
                return new PartyEvent(time);
            case 1:
                return new SingleEvent(time);
            default:
                return new MultiEvent(time, numSpeakers);
        }
    }
}