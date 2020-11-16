package event;


import readWrite.EventIterator;
import readWrite.RoomIterator;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.*;

/**
 * The manager that manages the scheduling of events with their rooms.
 */

public class EventManager {

    private final ArrayList<Room> rooms;
    private final Map<Integer, Event> map = new HashMap<>();

    /**
     * Initializes the event manager. It goes through the saved files of event.csv.
     */
    public EventManager() {
        Event.resetID();
        rooms = new ArrayList<>();
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
            temp2 = eventIterator.next();
            try {
                this.addEvent(temp2[0], Timestamp.valueOf(temp2[1]), Integer.parseInt(temp2[2]), temp2[3]);
            } catch (Exception e) {
                System.out.println("Failed to load event" + temp2[0] + "Invalid room number.");
            }
            for (j = 4; j < temp2.length; j++) {
                try {
                    this.addUserToEvent(usermanager.getUserType(temp2[j]), temp2[j], k);
                } catch (Exception e) {
                    System.out.println("Failed to add User to Event, event" + k + " does not exist.");
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
     * @throws DuplicateRoomNoException when room number exists.
     */
    public void addRoom(int roomNumber, int size) throws DuplicateRoomNoException {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                throw new DuplicateRoomNoException("DuplicateRoomNo: " + roomNumber);
            }
        }
        Room n = new Room(roomNumber, size);
        rooms.add(n);
    }


    /**
     * Get all the rooms in the conference.
     *
     * @return a list of strings of Rooms.
     */
    public ArrayList<String> getAllRooms() {
        ArrayList<String> result = new ArrayList<>();
        for (Room room : rooms) {
            result.add(room.toString());
        }
        return result;
    }

    /**
     * given a room number, return a room.
     *
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
            throw new InvalidActivityException();
        }
    }

    /**
     * Return a list of Events.toString() that attendee can sign up for.
     *
     * @param attendee Attendee, but string.
     * @return a list of Event ids in String that attendee can sign up for.
     */
    public ArrayList<String> canSignUp(String attendee) {
        ArrayList<String> rslt = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            if (!map.get(i).getAttendees().contains(attendee) && !this.dontHaveTime(attendee).contains(map.get(i).getTime())) {
                rslt.add(String.valueOf(map.get(i).getId()));
            }
        }
        return rslt;
    }

    /**
     * Make attendee a speaker by updating all events related with them.
     * Tips:
     * 1. scan all events with attendee.
     * 2. See if there is an speaker. If there is one already, We skip the event.
     * 3. make attendee speaker of all the rest of events.
     *
     * @param attendee Attendee but string.
     * @throws AlreadyHasSpeakerException if the event already has a speaker.
     */
    public void becomeSpeaker(String attendee) throws AlreadyHasSpeakerException{
        ArrayList<Event> events = new ArrayList<>(this.map.values());
        ArrayList<Event> attended = new ArrayList<>();
        for (Event i : events) {
            if (i.getAttendees().contains(attendee)) {
                attended.add(i);
            }
        }
        for (Event j : attended) {
            if (j.getSpeakStatus()) {
                throw new AlreadyHasSpeakerException("CannotAddSpeaker: " + attendee);
            } else {
                j.setSpeaker(attendee);
            }
        }
    }

    /**
     * Return all events in an ArrayList of Strings.
     *
     * @return all events. Index = event number.
     */
    public ArrayList<String> getAllEvents() {
        ArrayList<String> events = new ArrayList<>();
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
     * @throws Exception when the input were Invalid in some ways.
     */
    public void addUserToEvent(String type, String username, int eventNumber) throws Exception {
        int room_number = this.getEventIDMapToRoomNumber().get(eventNumber);
        int capacity = this.getRoomNumberMapToCapacity().get(room_number);
        int event_size = map.get(eventNumber).getAttendees().size();
        if (map.containsKey(eventNumber)) {
            if (type.equals("Speaker")) {
                if (!map.get(eventNumber).getSpeakStatus()) {
                    map.get(eventNumber).setSpeaker(username);
                } else {
                    throw new AlreadyHasSpeakerException("AlreadyHasSpeaker: " +
                            map.get(eventNumber).getSpeaker() + " at " + map.get(eventNumber));
                }
            } else if (type.equals("Attendee")) {
                if (event_size >= capacity - 1) {
                    throw new RoomIsFullException("Room is Full");
                }
                signUp(String.valueOf(eventNumber), username);
            } else {
                throw new InvalidUserException("Invalid user type");
            }
        } else {
            throw new NoSuchEventException("NoSuchEvent: " + eventNumber);
        }
    }


    /**
     * Get a hash map that keys are room numbers and values are their capacities.
     *
     * @return A hashmap that maps room numbers to their capacities.
     */
    public HashMap<Integer, Integer> getRoomNumberMapToCapacity() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (Room room : rooms) {
            result.put(room.getRoomNumber(), room.getCapacity());
        }
        return result;
    }

    /**
     * Get a hashmap that keys are eventID and values are room numbers.
     *
     * @return a hashmap that keys are eventID and values are room numbers.
     */
    public HashMap<Integer, Integer> getEventIDMapToRoomNumber() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (Room room : rooms) {
            for (Integer eventID : room.getSchedule()) {
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

    /**
     * Get the description from the event's id.
     *
     * @param event: event's id.
     * @return the description of the event.
     */
    public String getDescription(Integer event) {
        return map.get(event).getDescription();
    }


    /**
     * Create and add a event.
     *
     * @param roomNo:        room number.
     * @param time:          time the meeting begins.
     * @param meetingLength: time length of the event.
     * @param description: description of event
     * @throws NotInOfficeHourException if time out of working hour.
     * @throws TimeNotAvailableException if time is not available.
     * @throws InvalidActivityException if there's no such room.
     */
    public void addEvent(String roomNo, Timestamp time, int meetingLength, String description) throws Exception {
        try {
            if (!inOfficeHour(time)) {
                throw new NotInOfficeHourException("NotInOfficeHour: " + time);
            }
            if (ifRoomAvailable(roomNo, time, meetingLength)) {
                Event newEvent = new Event(time);
                map.put(newEvent.getId(), newEvent);
                newEvent.setDescription(description);
                for (Room r : rooms) {
                    if (r.getRoomNumber() == Integer.parseInt(roomNo)) {
                        r.addEvent(newEvent.getId());
                    }
                }
            } else {
                throw new TimeNotAvailableException("TimeNotAvailable: " + time);
            }
        } catch (Exception e) {
            throw new InvalidActivityException();
        }
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
            throw new NoSuchEventException("NoSuchEvent: " + event);
        }
    }

//    /**
//     * Make attendee sign out for an event.
//     *
//     * @param eventId  Event id in string.
//     * @param attendee Attendee with String.
//     * @throws NoSuchEventException when the event does not exist.
//     */
//    public void signOut(String eventId, String attendee) throws Exception {
//        if (map.containsKey(Integer.parseInt(eventId))) {
//            if (map.get(Integer.parseInt(eventId)).getAttendees().contains(attendee)) {
//                map.get(Integer.parseInt(eventId)).removeAttendees(attendee);
//            } else {
//                throw new InvalidActivityException();
//            }
//        } else {
//            throw new NoSuchEventException("NoSuchEvent: " + eventId);
//        }
//    }

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

//
//    /**
//     * Cancel the certain Event.
//     *
//     * @param eventId: the id of the event in string.
//     */
//    public void cancelEvent(String eventId) throws NoSuchEventException {
//        for (Integer id : map.keySet()) {
//            if (id == Integer.parseInt(eventId)) {
//                map.remove(id);
//                for (Room r : rooms) {
//                    if (r.getSchedule().contains(id)) {
//                        r.removeEvent(id);
//                    }
//                }
//            }
//            System.out.println("Successfully cancel the event" + eventId);
//            return;
//        }
//        throw new NoSuchEventException("NoSuchEvent: " + eventId);
//    }

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
}