package system;

import event.EventManager;
import message.MessageManager;
import user.User;
import user.UserManager;

import java.util.Scanner;

public class OrganizerSystem {
    private final User organizer;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();
    public OrganizerSystem(User organizer) {
        this.organizer = organizer;
    }
    public void run() {
        while(true){
            System.out.println("Name:" + organizer.toString());
            System.out.println("Organizer");
            System.out.println("[1] enter a room.\n[2] create speaker account\n[3] Schedule speakers\n [4] Send a message\n [5] See messages \n[e] exit");
            String command = reader.next();
            switch (command){
                case "1":
                    //TODO: enter a room.
                case "2":
                    //TODO: create speaker account
                case "3":
                    //TODO: schedule speakers
                case "4":
                    //TODO: send a message
                case "5":
                    //TODO: See messages.
            }




















        }




    }
}
