package event;
import java.util.ArrayList;

/**
 * Represents the room that has certain room number, capacity and schedules.
 * @author Shaohong Chen
 * @version 1.0.0
 *
 */
public class room {

    private int roomNumber;
    private int capacity;
    private ArrayList<event> schedule = new ArrayList<>();

    /**
     * Initialize the room that has a certain room number, capacity, with an empty schedule.
     * @param roomNumber represents the room number, in int.
     * @param size represents the capacity of the room, in int.
     */
    public room(int roomNumber, int size){
        this.roomNumber = roomNumber;
        this.capacity = size;
    }

    /**
     * Get the room number of the room.
     * @return the room number of the room, in int.
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * Get the room capacity
     * @return the capacity of the room, in int.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Add an event to the schedule of the room. May be successful or not depend on the event's time.
     * @param e An event that is planned to be added to the schedule of the room.
     * @return A boolean that shows if the event is added into the schedule of the room successfully.
     */
    public boolean addEvent(event e){
         for(event a: this.schedule){
             if(!e.getTime().canAddEvent() || e.getTime().contradicts(a.getTime())){
                return false;
             }
         }
         schedule.add(e);
         return true;
    }
}
