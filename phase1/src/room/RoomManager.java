package room;

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
}
