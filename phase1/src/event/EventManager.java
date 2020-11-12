package event;


import ReadWrite.EventIterator;
import ReadWrite.RoomIterator;
import user.UserManager;

import javax.activity.InvalidActivityException;
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
    /*
    What we need(for now):
    1. Add events to the rooms.
    2. getAttendees(eventID)
     */

    private ArrayList<Room> rooms;
    private Map<Integer, Event> map = new HashMap<Integer, Event>();

    public EventManager() {
        rooms = new ArrayList<Room>();
        int j;
        int k = 0;
        EventIterator eventIterator = new EventIterator();
        RoomIterator roomIterator = new RoomIterator();
        UserManager usermanager = new UserManager();
        String[] temp;
        System.out.println("loading existing events from file...");
        while (roomIterator.hasNext()) {
            temp = roomIterator.next();
            try {
                this.addRoom(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            } catch (Exception e) {
                System.out.println("Failed to add room" + Integer.parseInt(temp[0]));
            }
        }
        String[] temp2;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next(); //do something
            try {
                this.addEvent(temp2[0], Timestamp.valueOf(temp2[1]), Integer.parseInt(temp2[2]));
            } catch (Exception e) {
                System.out.println("Failed to load event" + temp2[0] + "Invalid room number.");
            }
            for(j = 3; j < temp2.length; j++){
                try {
                    this.addUserToEvent(usermanager.getUserType(temp2[j]), temp2[j], k);
                } catch (Exception e) {
                    System.out.println("Failed to add User to Event, event"+k+" does not exist.");
                }
            }
            k += 1;
        }
        System.out.println("\n Load complete. Welcome to the system. \n");
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

    /**
     * Get all the rooms in the conference.
     * @return a list of strings of Rooms.
     */
    public ArrayList<String> getAllRooms() {
        ArrayList<String> result = new ArrayList<String>();
        for (Room room : rooms) {
            result.add(room.toString());
        }
        return result;
    }

    /**
     * given a room number, return a room.
     * @param roomNumber: The room number of the Room you are looking for.
     * @return Returns a room with the room number if the room exists.
     * @throws InvalidActivityException When the room number does not exist.
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
            System.out.println("Sorry, please give a proper room number");
            throw new InvalidActivityException();
        }
    }

    /**
     * Make attendee signup for an event.
     *
     * @param event    Event id in string.
     * @param attendee Attendee, but with String.
     * @throws NoSuchEventException when needed. or not, I don't care. but you should tho.
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
        for (int i = 0; i < map.size(); i++) {
            if (!map.get(i).getAttendees().contains(attendee)&&!this.dontHaveTime(attendee).contains(map.get(i).getTime())) {
                rslt.add(String.valueOf(map.get(i).getId()));
            }
        }
        return rslt;
    }

    /**
     * Return a list of times an attendee have signed up for events.
     * @param attendee Attendee to string
     * @return list of times this attendee is busy
     */
    private ArrayList<String> dontHaveTime(String attendee) {
        ArrayList<String> res = new ArrayList<String>();
        for(Integer key: map.keySet()){
            if(map.get(key).getAttendees().contains(attendee)){
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

    /**
     * Check if the room is available or not at the input time.
     *
     * @param roomNo: room number of the given room.
     * @param time:   start time of the event.
     * @param length: number of hours the event will take.
     * @return true or not
     * @throws InvalidActivityException when the room number is invalid.
     */
    private boolean ifRoomAvailable(String roomNo, Timestamp time, int length) throws InvalidActivityException {
        for (Room r : rooms) {
            if (r.getRoomNumber() == Integer.parseInt(roomNo)) {
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
     * @param roomNo:        room number.
     * @param time:          time the meeting begins.
     * @param meetingLength: time length of the event.
     * @throws InvalidActivityException: if cannot find a room with room number roomNo.
     */
    public void addEvent(String roomNo, Timestamp time, int meetingLength) throws InvalidActivityException {
        System.out.println("Adding event to room "+roomNo+", time: "+time.toString()+" Duration: "+meetingLength);
        try {
            if (!inOfficeHour(time)){
                System.out.println("Invalid time slot. Not in working hour.");
                return;
            }
            if (ifRoomAvailable(roomNo, time, meetingLength)) {
                Event newEvent = new Event(time);
                map.put(newEvent.getId(), newEvent);
                for (Room r : rooms) {
                    if (r.getRoomNumber() == Integer.parseInt(roomNo)) {
                        r.addEvent(newEvent.getId());
                    }
                }
            }
        } catch (Exception e) {
            throw new InvalidActivityException();
        }
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
                System.out.println("event"+j.getId()+" already has a speaker. Unable to promote\n");
            }
            else{
                j.setSpeaker(attendee);
                System.out.println("Successfully set "+attendee+" to be the speaker of event"+j.getId()+"\n");
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
     * @param eventNumber eventNumber.
     */
    public void addUserToEvent(String type, String username, int eventNumber) throws Exception {
        if (map.containsKey(eventNumber)) {
            if (type.equals("Speaker")) {
                if (!map.get(eventNumber).getSpeakStatus()) {
                    map.get(eventNumber).setSpeaker(username);
                    signUp(String.valueOf(eventNumber), username);
                } else {
                    throw new AlreadyHasSpeakerException("AlreadyHasSpeaker: " +
                            map.get(eventNumber).getSpeaker() + " at " + map.get(eventNumber));
                }
            } else if (type.equals("Attendee")) {
                signUp(String.valueOf(eventNumber), username);
            } else {
                throw new InvalidUserException("Invalid user type");
            }
        } else {
            throw new NoSuchEventException("NoSuchEvent: " + String.valueOf(eventNumber));
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
    public String getSpeakers(Integer eventNum) {
        try {
            return map.get(eventNum).getSpeaker();
        } catch (Exception e) {
            return "(No speaker yet.)";
        }
    }

    private boolean inOfficeHour(Timestamp time){
        String t = time.toString();
        int hour = Integer.parseInt(String.valueOf(t.charAt(11))+String.valueOf(t.charAt(12)));
        int min = Integer.parseInt(String.valueOf(t.charAt(14))+String.valueOf(t.charAt(15)));
        int sec = Integer.parseInt(String.valueOf(t.charAt(17))+String.valueOf(t.charAt(18)));
        if(hour >=9){
            if(hour == 16 && min == 0 && sec == 0){
                return true;
            }else{
                return hour < 16;
            }
        }
        return false;
    }

}