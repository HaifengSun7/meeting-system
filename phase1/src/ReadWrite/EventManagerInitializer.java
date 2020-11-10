package ReadWrite;

import event.EventManager;
import user.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
//Format: roomID, time, duration, user.
public class EventManagerInitializer {
    public EventManager run(){
        int j;
        int k =0;
        EventManager eventmanager = new EventManager();
        EventIterator eventIterator = new EventIterator();
        RoomIterator roomIterator = new RoomIterator();
        UserManager usermanager = new UserManagerInitializer().run();
        String[] temp;
        while (!roomIterator.hasNext()) {
            temp = roomIterator.next(); //do something
            try {
                eventmanager.addRoom(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        String[] temp2;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next(); //do something
            try {
                eventmanager.addEvent(temp2[0], Timestamp.valueOf(temp2[1]), Integer.parseInt(temp2[2]));
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
            for(j = 2; j < temp2.length; j++){
                try {
                    eventmanager.addUserToEvent(usermanager.getUserType(temp2[j]), temp2[j], k);
                } catch (Exception e) {
                    System.out.println("This should not be happening.");
                }
            }
            }
            k += 1;
        return eventmanager;
    }
}
