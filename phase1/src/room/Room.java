package event;
import java.util.ArrayList;

/**
 * Represents the Room that has certain Room number, capacity and schedules.
 * @author Shaohong Chen
 * @version 1.0.0
 *
 */
public class Room {

    private int roomNumber;
    private int capacity;
    private ArrayList<Event> schedule = new ArrayList<>();

    /**
     * Initialize the Room that has a certain Room number, capacity, with an empty schedule.
     * @param roomNumber represents the Room number, in int.
     * @param size represents the capacity of the Room, in int.
     */
    public Room(int roomNumber, int size){
        this.roomNumber = roomNumber;
        this.capacity = size;
    }

    /**
     * Get the Room number of the Room.
     * @return the Room number of the Room, in int.
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * Get the Room capacity
     * @return the capacity of the Room, in int.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Add an Event to the schedule of the Room. May be successful or not depend on the Event's time.
     * @param e An Event that is planned to be added to the schedule of the Room.
     * @return A boolean that shows if the Event is added into the schedule of the Room successfully.
     */
    public boolean addEvent(Event e){
         for(Event a: this.schedule){
             if(!e.getTime().canAddEvent() || e.getTime().contradicts(a.getTime())){
                return false;
             }
         }
         schedule.add(e);
         return true;
    }
}
