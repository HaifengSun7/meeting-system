package event;

import javax.activity.InvalidActivityException;
import java.util.ArrayList;

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
    ？？？？？ Q: Do we need a list to store all speakers(different with attendee) ？？？？？
     */

    private ArrayList<Room> rooms;

    //TODO: Complete the constructor.
    public EventManager(){
        rooms = new ArrayList<Room>();
    }

    /**
     * Gives the string output of the event.
     * @param id: The event id.
     * @return a Event based on its id, but with toString();
     */
    public String findEventStr(Integer id){
        //TODO: implement this. Haifeng.
    }

    /**
     * Get's the attendees of a particular event.
     * @param eventId The id of the event that we are looking for.
     * @return A list of Attendees' usernames that the event has.
     */
    public ArrayList<String> getAttendees(String eventId){
        //TODO: get the list of attendees in string that have signed up a particular event
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

    //TODO: add events from file?
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
     * @return The list of event in id's of the given room.
     * @throws InvalidActivityException When the room number given is not valid.
     */
    public ArrayList<Integer> getSchedule (int roomNumber) throws InvalidActivityException {
        try {
            Room room = this.findRoom(roomNumber);
            return room.getSchedule();
        }
        catch (InvalidActivityException e){
            System.out.println("Give me a proper room number you dumb dumb");
            throw new InvalidActivityException();
        }
    }

}
