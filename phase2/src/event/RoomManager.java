package event;

import event.exceptions.DuplicateRoomNumberException;
import event.exceptions.RoomIsFullException;
import event.exceptions.WrongRoomSizeException;

import javax.activity.InvalidActivityException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The manager of rooms.
 */
public class RoomManager {

    private final ArrayList<Room> rooms;

    protected RoomManager() {
        rooms = new ArrayList<>();
    }

    /**
     * Add a valid room to the conference.
     *
     * @param roomNumber An int representing the room number.
     * @param size       An int representing the capacity of the room.
     * @throws DuplicateRoomNumberException when room number exists.
     * @throws WrongRoomSizeException when the room size is invalid.
     */
    protected void addRoom(int roomNumber, int size) throws DuplicateRoomNumberException, WrongRoomSizeException {
        if (size <= 0) {
            throw new WrongRoomSizeException("Room size must be greater than 0");
        }
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                throw new DuplicateRoomNumberException("Duplicate Room Number: " + roomNumber);
            }
        }
        Room n = new Room(roomNumber, size);
        rooms.add(n);
    }

    /**
     * Add an event to the room.
     *
     * @param roomNo Room number of the room that we want to add the event in.
     * @param eventId Event id of the event we want to add.
     * @param totalPeople total people of the event.
     * @throws RoomIsFullException when the room is full.
     */
    protected void addEvent(int roomNo, int eventId, int totalPeople) throws RoomIsFullException {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNo) {
                if (r.getCapacity() >= totalPeople) {
                    r.addEvent(eventId);
                } else {
                    throw new RoomIsFullException("Room is not big enough.");
                }
            }
        }
    }
    /**
     * Remove an event from the room.
     *
     * @param id id of the event we want to remove.
     */
    protected void remove(int id) {
        for (Room r : rooms) {
            if (r.getSchedule().contains(id)) {
                r.removeEvent(id);
            }
        }
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
        throw new InvalidActivityException("Room number does not exist.");
    }

    /**
     * Get the events planned in a room.
     *
     * @param roomNumber The room number of the room that we are looking for.
     * @return The list of event in id's of the given room.
     * @throws InvalidActivityException When the room number given is not valid.
     */
    public ArrayList<Integer> getSchedule(int roomNumber) throws InvalidActivityException {
        Room room = this.findRoom(roomNumber);
        return room.getSchedule();
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

}

