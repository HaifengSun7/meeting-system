package event;

import java.util.ArrayList;

/**
 * Represents the Room that has certain Room number, capacity and schedules.
 */
public class Room {

    private final int roomNumber;
    private final int capacity;
    private final ArrayList<Integer> schedule = new ArrayList<>();

    /**
     * Initialize the Room that has a certain Room number, capacity, with an empty schedule.
     *
     * @param roomNumber represents the Room number, in int.
     * @param size       represents the capacity of the Room, in int.
     */
    public Room(int roomNumber, int size) {
        this.roomNumber = roomNumber;
        this.capacity = size;
    }

    /**
     * Get the Room number of the Room.
     *
     * @return the Room number of the Room, in int.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Get the Room capacity
     *
     * @return the capacity of the Room, in int.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Add an Event to the schedule of the Room. May be successful or not depend on the Event's time.
     *
     * @param eventID An Event that is planned to be added to the schedule of the Room.
     */
    public void addEvent(int eventID) {
        schedule.add(eventID);
    }

    /**
     * Add an Event to the schedule of the Room. May be successful or not depend on the Event's time.
     *
     * @param eventID An Event that is planned to be added to the schedule of the Room.
     */
    public void removeEvent(int eventID) {
        schedule.remove((Integer) eventID);
    }

    /**
     * Get the schedule of the room, by the event ID's.
     *
     * @return the ID's of events scheduled in this room.
     */
    public ArrayList<Integer> getSchedule() {
        return this.schedule;
    }

    /**
     * Override of toString method, that writes the output of class Room.
     *
     * @return the string form of output for class Room.
     */
    @Override
    public String toString() {
        return "Room#: " +
                roomNumber +
                ": Total Seats = " +
                capacity;
    }
}
