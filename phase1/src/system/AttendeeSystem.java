package system;

import event.Event;
import event.EventManager;
import message.MessageManager;
import user.User; //TODO: Avoid using entity in controller. ONLY use Cases.
import message.Message;
import message.MessageManager;
import user.Attendee;
import user.User;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeSystem {
    private final User attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public AttendeeSystem(User attendee) {
        this.attendee = attendee;
    }

    public void run() {
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
                    ArrayList<Event> example_list = new ArrayList<Event>(); // Just an example
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
                    //TODO: See the events that I have signed up for
                case "3":
                    System.out.println("To Who?");
                    ArrayList<User> msglst= usermanager.getContactList(attendee);
                    for(int i = 0; i < msglst.size(); i++){
                        System.out.println("[" + i + "] " + msglst.get(i).toString());
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.next();
                    if (!("e".equals(command))) {
                        User receiver = msglst.get(Integer.parseInt(command)); // TODO: what if input wrong?
                        System.out.println("Yo, now input your message. Hint: \n and stuff."); // TODO: WTF?
                        command = reader.nextLine();
                        messagemanager.sendMessage(attendee, receiver, command);
                        //TODO: CAN'T USER entity in controller. This method has other type of input, too. By James.
                        System.out.println("Success! Press something to continue");
                        reader.next();
                    }
                    else{
                        System.out.println("Exiting");
                    }
                case "4":
                    // TODO: see inbox.
                case "default":
                    System.out.println("Press the right key you dumb dumb.");
            }
        }
        ///TODO: Saving... or not?

    }
}
