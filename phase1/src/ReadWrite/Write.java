package ReadWrite;

import event.EventManager;
import message.MessageManager;
import user.UserManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Write {
    private final UserManager usermanager;
    private final EventManager eventmanager;
    private final MessageManager messagemanager;

    public Write(UserManager usermanager, EventManager eventmanager, MessageManager messagemanager){
        this.usermanager = usermanager;
        this.eventmanager = eventmanager;
        this.messagemanager = messagemanager;

    }
    public void run(){
        try {
            String password;
            ArrayList<String> msglst;
            String type;
            FileWriter userWriter = new FileWriter("src/resources/user.csv", false);
            Collection<String> usernames = usermanager.getAllUsernames();
            for(String username : usernames) {
                password = usermanager.getPassword(username);
                type = usermanager.getUserType(username);
                msglst = usermanager.getContactList(username);
                userWriter.append(username);
                userWriter.append(",");
                userWriter.append(password);
                userWriter.append(",");
                userWriter.append(type);
                for(String contactListedUser : msglst){
                    userWriter.append(",");
                    userWriter.append(contactListedUser);
                }
                userWriter.append("\n");
                //That's user. now output.
            }
            userWriter.flush();
            userWriter.close();

            FileWriter roomWriter = new FileWriter("src/resources/room.csv", false);
            HashMap<Integer, Integer> roomToCapacity = eventmanager.getRoomNumberMapToCapacity();
            for (Map.Entry<Integer, Integer> item : roomToCapacity.entrySet()) {
                Integer room = item.getKey();
                Integer capacity = item.getValue();
                roomWriter.append(Character.highSurrogate(room));
                roomWriter.append(",");
                roomWriter.append(Character.highSurrogate(capacity));
                roomWriter.append("\n");
            }
            //That's room. now output.
            roomWriter.flush();
            roomWriter.close();

            FileWriter eventWriter = new FileWriter("src/resources/event.csv", false);
            HashMap<Integer, Integer> eventToRoom = eventmanager.getEventIDMapToRoomNumber();
            String time;
            String duration;
            ArrayList<String> attendees;
            String speaker;
            for (Map.Entry<Integer, Integer> item : eventToRoom.entrySet()) {
                Integer event = item.getKey();
                Integer room2 = item.getValue();
                time = eventmanager.getTime(event);
                duration = eventmanager.getDuration(event);
                attendees = eventmanager.getAttendees(String.valueOf(event));
                speaker = eventmanager.getSpeakers(event);
                eventWriter.append(Character.highSurrogate(room2));
                eventWriter.append(",");
                eventWriter.append(time);
                eventWriter.append(",");
                eventWriter.append(duration);
                for(String attendee : attendees){
                    eventWriter.append(",");
                    eventWriter.append(attendee);
                }
                if (!(speaker.equals(""))){
                    eventWriter.append(",");
                    eventWriter.append(speaker);
                }
                eventWriter.append("\n");
            }
            eventWriter.flush();
            eventWriter.close();

            FileWriter messageWriter = new FileWriter("src/resources/message.csv", false);
            ArrayList<ArrayList<String>> allMessage = messagemanager.getAllMessage();
            for(ArrayList<String> messageinfo : allMessage){
                messageWriter.append(messageinfo.get(0));
                messageWriter.append(",");
                messageWriter.append(messageinfo.get(1));
                messageWriter.append(",");
                messageWriter.append(messageinfo.get(2));
                messageWriter.append("\n");
            }
            messageWriter.flush();
            messageWriter.close();






        } catch (IOException e) {
            System.out.println("Some random shit happened i don't know dont ask me");
        }


    }
}
