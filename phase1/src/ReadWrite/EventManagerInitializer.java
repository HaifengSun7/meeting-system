package ReadWrite;

import event.EventManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventManagerInitializer {
    public EventManager run(){
        EventManager eventmanager = new EventManager();
        EventIterator eventIterator = new EventIterator();
        RoomIterator roomIterator = new RoomIterator();
        String[] temp;
        while (!roomIterator.hasNext()) {
            temp = roomIterator.next(); //do something
            try {
                eventmanager.addRoom(temp[0], temp[1]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        String[] temp2;
        while (!eventIterator.hasNext()) {
            temp2 = eventIterator.next(); //do something
            try {
                eventmanager.addEvent(temp2[0], temp2[1], temp2[2]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        return usermanager;
    }
}
