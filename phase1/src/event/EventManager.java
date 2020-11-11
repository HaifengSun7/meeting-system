package event;


import javax.activity.InvalidActivityException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The manager that manages the scheduling of events with their rooms.
 * @author Haifeng Sun
 * @version 1.0.0
 */

public class EventManager {
//TODO: We are making it a room manager, too. We will check if the time are available here, then add to Room with Event ID.
//TODO: We are doing like this because entities can't use each other. Only the corresponding UseCases can use Entities.
    /*
    What we need(for now):
    1. Add events to the rooms.
    2. getAttendees(eventID)
     */

    private ArrayList<Room> rooms;
    private Map<Integer, Event> map = new HashMap<Integer, Event>();

    //TODO: Complete the constructor.
    public EventManager() {
        rooms = new ArrayList<Room>();
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
     * Get's the attendees of a particular event.
     *
     * @param eventId The id of the event that we are looking for.
     * @return A list of Attendees' usernames that the event has.
     */
    public ArrayList<String> getAttendees(String eventId) {
        return map.get(Integer.parseInt(eventId)).getAttendees();
    }

    /**
     * Add a valid room to the conference.
     *
     * @param roomNumber An int representing the room number
     * @param size       An int representing the capacity of the room.
     */
    public void addRoom(int roomNumber, int size) {
        Room r = new Room(roomNumber, size);
        rooms.add(r);
    }

    public ArrayList<String> getAllRooms() {
        ArrayList<String> result = new ArrayList<String>();
        for (Room room : rooms) {
            result.add(room.toString());
        }
        return result;
    }

    /**
     * given a room number, return a room.
     *
     * @param roomNumber: The room number of the Room you are looking for.
     */
    public Room findRoom(int roomNumber) throws InvalidActivityException {
        for (Room room : rooms) {
            if (roomNumber == room.getRoomNumber()) {
                return room;
            }
        }
        throw new InvalidActivityException();
    }

    /**
     * Get the events planned in a room.
     *
     * @param roomNumber The room number of the room that we are looking for.
     * @return The list of event in id's of the given room.
     * @throws InvalidActivityException When the room number given is not valid.
     */
    public ArrayList<Integer> getSchedule(int roomNumber) throws InvalidActivityException {
        try {
            Room room = this.findRoom(roomNumber);
            return room.getSchedule();
        } catch (InvalidActivityException e) {
            System.out.println("Give me a proper room number you dumb dumb");
            throw new InvalidActivityException();
        }
    }

    /**
     * Make attendee signup for an event.
     *
     * @param event    Event id in string.
     * @param attendee Attendee, but with String.
     * @throws Exception when needed. or not, I don't care. but you should tho.
     */
    public void signUp(String event, String attendee) throws NoSuchEventException {
        if (map.containsKey(Integer.parseInt(event))) {
            map.get(Integer.parseInt(event)).addAttendees(attendee);
        } else {
            throw new NoSuchEventException("NoSuchEvent: " + event);
        }
    }

    /**
     * Return a list of Events.toString() that attendee can sign up for.
     *
     * @param attendee Attendee, but string.
     * @return a list of Event ids in String that attendee can sign up for.
     */
    public ArrayList<String> canSignUp(String attendee) {
        ArrayList<String> rslt = new ArrayList<String>();
        for (int i = 0; i < map.size() - 1; i++) {
            if (!map.get(i).getAttendees().contains(attendee)) {
                rslt.add(String.valueOf(map.get(i).getId()));
            }
        }
        return rslt;
    }

//    /**
//     * Get a map that contains all events.
//     * @return the map<eventId, correspondingEvent>.
//     */
//    public Map<Integer, Event> getMap() {return this.map;}

    /**
     * Check if the room is available or not at the input time.
     *
     * @param roomno: room number of the given room.
     * @param time:   start time of the event.
     * @param length: number of hours the event will take.
     * @return true or not
     * @throws InvalidActivityException when the room number is invalid.
     */
    private boolean ifRoomAvailable(String roomno, Timestamp time, int length) throws InvalidActivityException {
        for (Room r : rooms) {
            if (r.getRoomNumber() == Integer.parseInt(roomno)) {
                for (int id : r.getSchedule()) {
                    if (map.get(id).contradicts(time, length)) {
                        return false;
                    }
                }
                return true;
            }
        }
        throw new InvalidActivityException();
    }

    /**
     * Create and add a event.
     *
     * @param roomno:        room number.
     * @param time:          time the meeting begins.
     * @param meetingLength: time length of the event.
     * @throws InvalidActivityException: if cannot find a room with room number roomno.
     */
    public void addEvent(String roomno, Timestamp time, int meetingLength) throws InvalidActivityException {
        try {
            if (ifRoomAvailable(roomno, time, meetingLength)) {
                Event newEvent = new Event(time);
                map.put(newEvent.getId(), newEvent);
                for (Room r : rooms) {
                    if (r.getRoomNumber() == Integer.parseInt(roomno)) {
                        r.addEvent(newEvent.getId());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Give me a proper room number.");
            throw new InvalidActivityException();
        }
    }

    /**
     * Make attendee a speaker by updating all events related with them.
     * Tips: 1. scan all events with attendee. 2. See if there is an speaker. If there is one already, throw an exception. 3. make attendee speaker.
     *
     * @param attendee Attendee but string.
     * @throws AlreadyHasSpeakerException if the event already has a speaker.
     */
    public void becomeSpeaker(String attendee) throws Exception {
        ArrayList<Event> events = new ArrayList<>(this.map.values());
        ArrayList<Event> attended = new ArrayList<>();
        for(Event i :events){
            if(i.getAttendees().contains(attendee)){
                attended.add(i);
            }
        }
        for(Event j :attended){
            if(j.getSpeakStatus()){
                throw new AlreadyHasSpeakerException("AlreadyHasSpeaker: " + j.getSpeaker());
            }
            else{
                j.setSpeaker(attendee);
            }
        }
    }

    /**
     * Return all events in an ArrayList of Strings.
     *
     * @return all events. Index = eventnumber.
     */
    public ArrayList<String> getAllEvents() {
        ArrayList<String> events = new ArrayList<String>();
        for (int i = 0; i < map.size() - 1; i++) {
            if (map.containsKey(i)) {
                events.add(map.get(i).toString());
            } else {
                events.add("cancelled");
            }
        }
        return events;
    }

    /**
     * Add user to an event. MAKE SURE TO DOUBLE CHECK ALL CONDITIONS.
     *
     * @param type        type of user
     * @param username    username
     * @param eventnumber eventnumber.
     */
    public void addUserToEvent(String type, String username, int eventnumber) throws Exception {
        if (map.containsKey(eventnumber)) {
            if (type.equals("Speaker")) {
                if (!map.get(eventnumber).getSpeakStatus()) {
                    map.get(eventnumber).setSpeaker(username);
                    try {
                        signUp(String.valueOf(eventnumber), username);
                    } catch (Exception e1) {
                        throw new NoSuchEventException("NoSuchEvent: " + String.valueOf(eventnumber));
                    }
                } else {
                    throw new AlreadyHasSpeakerException("AlreadyHasSpeaker: " + map.get(eventnumber).getSpeaker());
                }
            } else if (type.equals("Attendee")) {
                try{
                    signUp(String.valueOf(eventnumber), username);
                } catch (Exception e2) {
                    throw new NoSuchEventException("NoSuchEvent: " + String.valueOf(eventnumber));
                }
            } else {
                throw new Exception();
                //TODO: Is organizer an attendee?
            }
        } else {
            throw new NoSuchEventException("NoSuchEvent: " + String.valueOf(eventnumber));
        }
    }


    /**
     * Added for Write. DO NOT CHANGE ANYTHING!
     * @return A hashmap.
     */
    public HashMap<Integer, Integer> getRoomNumberMapToCapacity(){
        HashMap<Integer, Integer> result = new HashMap<>();
        for(Room room : rooms){
            result.put(room.getRoomNumber(), room.getCapacity());
        }
        return result;
    }

    public HashMap<Integer, Integer> getEventIDMapToRoomNumber(){
        HashMap<Integer, Integer> result = new HashMap<>();
        for(Room room: rooms){
            for(Integer eventID: room.getSchedule()){
                result.put(eventID, room.getRoomNumber());
            }
        }
        return result;
    }

    public String getTime(Integer event) {
        return map.get(event).getTime();
    }

    public String getDuration(Integer event) {
        return String.valueOf(map.get(event).getMeetingLength());
    }

    public String getSpeakers(Integer event) {
        try {
            return map.get(event).getSpeaker();
        } catch (Exception e) {
            return "";
        }
    }
}