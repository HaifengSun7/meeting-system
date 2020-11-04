package room;

import event.EventManager;

import javax.activity.InvalidActivityException;
import java.security.PublicKey;
import java.util.ArrayList;

public class RoomManager {
    //Haifeng is working on it.
    private ArrayList<Room> rooms;
    public RoomManager(){
        rooms = new ArrayList<Room>();
    }
    public void add(int roomNumber, int size) {
        Room r = new Room(roomNumber, size);
        rooms.add(r);
    }
//TODO: add events from file?
    public ArrayList<String> getAllRooms (){
        ArrayList<String> result = new ArrayList<String>();
        for(Room room: rooms){
            result.add(room.toString());
        }
        return result;
    }
    public Room findRoom(int i) throws InvalidActivityException {
        /*
        given a room number, return a room.
         */
        for(Room room: rooms){
            if (i == room.getRoomNumber()){
                return room;
            }
        }
        throw new InvalidActivityException();
    }
    public ArrayList<Integer> getSchedule (int i) throws InvalidActivityException {
        /*
        return all event ids of a room, given room number.
         */
        try {
            Room room = this.findRoom(i);
            ArrayList<Integer> schedule = room.getSchedule();
            return schedule;
            }
        catch (InvalidActivityException e){
            System.out.println("Give me a proper room number you dumb dumb");
            throw new InvalidActivityException();
        }

    }
}
