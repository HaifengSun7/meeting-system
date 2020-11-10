package event;

import com.sun.tools.corba.se.idl.constExpr.Times;
import user.UserManager;

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

public class EventManager{
//TODO: We are making it a room manager, too. We will check if the time are available here, then add to Room with Event ID.
//TODO: We are doing like this because entities can't use each other. Only the corresponding UseCases can use Entities.
    /*
    What we need(for now):
    1. Add events to the rooms.
    2. getAttendees(eventID)
     */

    private ArrayList<Room> rooms;
    private Map<String, Event> map = new HashMap<String, Event>();


    public EventManager(){
        rooms = new ArrayList<Room>();
    }

    /**
     * Gives the string output of the event.
     * @param description: event description.
     * @return a Event based on its id, but with toString();
     */
    public String findEventStr(String description){
        return map.get(description).toString();
    }

    /**
     * Get's the attendees of a particular event.
     * @param description: event description.
     * @return A list of Attendees' usernames that the event has.
     */
    public ArrayList<String> getAttendees(String description){
        return map.get(description).getAttendees();
    }

    /**
     * Add a valid room to the conference.
     * @param roomNumber An int representing the room number
     * @param size An int representing the capacity of the room.
     */
    public void addRoom(int roomNumber, int size) {
        Room r = new Room(roomNumber, size);
        rooms.add(r);
    }

    public ArrayList<String> getAllRooms(){
        ArrayList<String> result = new ArrayList<String>();
        for(Room room: rooms){
            result.add(room.toString());
        }
        return result;
    }

    /**
     * given a room number, return a room.
     * @param roomNumber: The room number of the Room you are looking for.
     */
    public Room findRoom(int roomNumber) throws InvalidActivityException {
        for(Room room: rooms){
            if (roomNumber == room.getRoomNumber()){
                return room;
            }
        }
        throw new InvalidActivityException();
    }

    /**
     * Get the events planned in a room.
     * @param roomNumber The room number of the room that we are looking for.
     * @return The list of event in Strings of the given room.
     * @throws InvalidActivityException When the room number given is not valid.
     */
    public ArrayList<String> getSchedule (int roomNumber) throws InvalidActivityException {
        try {
            Room room = this.findRoom(roomNumber);
            return room.getSchedule();
        }
        catch (InvalidActivityException e){
            System.out.println("Give me a proper room number you dumb dumb");
            throw new InvalidActivityException();
        }
    }

//    /**
//     * Make attendee signup for an event.
//     * @param event Event, but with String.
//     * @param attendee Attendee, but with String.
//     * @throws Exception when needed. or not, I don't care. but you should tho.
//     */
//    public void signUp(String event, String attendee) {
//
//    }

    /**
     * Return a list of Events.toString() that attendee can sign up for.
     * @param signedEventsList: a User's list of signed Event descriptions.
     * @return a list of Events.toString() that attendee can sign up for.
     */
    public ArrayList<String> canSignUp(ArrayList<String> signedEventsList) {
        ArrayList<String> rslt = new ArrayList<String>();
        for(String eventName: signedEventsList) {
            rslt.add(map.get(eventName).toString());
        }
        return rslt;
    }


//    /**
//     * Get a map that contains all events.
//     * @return the map<eventId, correspondingEvent>.
//     */
//    public Map<String, Event> getMap() {return this.map;}

    /**
     * Check if the room is available or not at the input time.
     * @param roomno: room number of the given room.
     * @param time: start time of the event.
     * @param length: number of hours the event will take.
     * @return true or not
     * @throws Exception
     */
    private boolean ifRoomAvailable(String roomno, Timestamp time, int length) throws Exception{
        for (Room r: rooms) {
            if (r.getRoomNumber() == Integer.parseInt(roomno)) {
                for (String description: r.getSchedule()) {
                    if (map.get(description).contradicts(time, length)) {
                        return false;
                    }
                }return true;
            } else {
                throw new Exception();//TODO: Exception.
            }
        }
    }

    /**
     * Create and add an event.
     * @param roomno: room number.
     * @param time: time the meeting begins.
     * @param meetingLength: time length of the event.
     * @throws Exception: throw exception if the room is unavailable.
     */
    public void addEvent(String roomno, Timestamp time, int meetingLength) throws Exception{
        if (ifRoomAvailable(roomno, time, meetingLength)){
            Event newEvent = new Event(time);
            map.put(newEvent.getDescription(), newEvent);
            for (Room r: rooms) {
                if (r.getRoomNumber() == Integer.parseInt(roomno)) {
                    r.addEvent(newEvent.getDescription());
                }
            }
        } else {
            throw new Exception();//TODO: Exception.
        }
    }

    /**
     * Make attendee a speaker by updating all events related with them.
     * Tips: 1. scan all events with attendee. 2. See if there is an speaker. If there is one already, throw an exception. 3. make attendee speaker.
     * @param attendee Attendee but string.
     */
    public void becomeSpeaker(String attendee) {
    }

    /**
     * Return all events in an ArrayList of Strings.
     * @return all events. Index = eventnumber.
     */
    public ArrayList<String> getAllEvents() {
        ArrayList<String> events = new ArrayList<String> ();
        int i = 0;
        int j = map.size() - 1;
        while (i <= j) {
            for (String description: map.keySet()) {
                if (map.get(description).getId() == i) {
                    events.add(description);
                    i++;
                } else {
                    events.add("cancelled");
                    i++;
                    j++;
                }
            }
        }
        return events;
    }

    /**
     * Add user to an event. MAKE SURE TO DOUBLE CHECK ALL CONDITIONS.
     * @param type type of user
     * @param username username
     * @param eventnumber eventnumber.
     */
    public void addUserToEvent(String type, String username, int eventnumber) throws Exception{
        if (type.equals("Attendee")) {
            map.get(eventnumber).addAttendees();
        } else if(type.equals("Speaker")) {
            map.get(eventnumber).setSpeaker();
        } else {
            throw new Exception();//TODO: Exception.
        }
        )
    }
}