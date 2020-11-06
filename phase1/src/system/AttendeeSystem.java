package system;

import event.EventManager;
import message.MessageManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeSystem {
    private final String attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    public void run() {
        //TODO: READ.
        while (true){
            System.out.println("Name:" + attendee.toString());
            System.out.println("Attendee");
            System.out.println("[1] Schedule of events that I can sign up for.\n" +
                    "[2] See events that I have signed up for\n" +
                    "[3] Send a message\n " +
                    "[4] See messages \n" +
                    "[e] exit");
            String command = reader.next();
            switch (command) {
                case "e":
                    break;

                case "1":
                    //TODO: Show a list of schedule which they can sign up for.
                    ArrayList<String> example_list = new ArrayList<String>(); // Just an example
                    for (int i = 0; i < example_list.size(); i++) {
                        System.out.println("[" + i + "] " + example_list.get(i).toString());
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.next();
                    if (!("e".equals(command))) {
                        eventmanager.signUp(example_list.get(Integer.parseInt(command)), attendee); // TODO: what if input wrong?

                        System.out.println("Success! Press something to continue");
                        reader.next();
                    }
                    else{
                        System.out.println("Exiting");
                    }
                case "2":
                    //TODO: See the events that user have signed up for
                    ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
                    for(int i = 0; i < eventsList.size(); i++){
                        System.out.println("[" + i + "] " + eventsList.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                case "3":
                    System.out.println("To Who?");
                    ArrayList<String> msglst= usermanager.getContactList(attendee);
                    for(int i = 0; i < msglst.size(); i++){
                        System.out.println("[" + i + "] " + msglst.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.next();
                    if (!("e".equals(command))) {
                        String receiver = msglst.get(Integer.parseInt(command)); // TODO: what if input wrong?
                        System.out.println("Yo, now input your message. Hint: \\n and stuff."); // TODO: string is bad.
                        String command2 = reader.nextLine();
                        messagemanager.sendMessage(attendee, receiver, command2);
                        System.out.println("Success! Press something to continue");
                        reader.next();
                    }
                    else{
                        System.out.println("Exiting");
                    }
                case "4":
                    ArrayList<String> inbox = messagemanager.getInbox(attendee);
                    for(int i = 0; i < inbox.size(); i++){
                        System.out.println("[" + i + "] " + inbox.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                case "default":
                    System.out.println("Press the right key.");
            }
        }
        //TODO: Saving... or not?

    }
}
