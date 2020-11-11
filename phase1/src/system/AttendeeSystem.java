package system;

import ReadWrite.EventManagerInitializer;
import ReadWrite.MessageManagerInitializer;
import ReadWrite.UserManagerInitializer;
import ReadWrite.Write;
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
    public String command; //command buffer
    public String message; //message buffer

    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    public void run() {
        EventManagerInitializer eventManagerInitializer = new EventManagerInitializer();
        eventmanager = eventManagerInitializer.run();
        MessageManagerInitializer messageManagerInitializer = new MessageManagerInitializer();
        messagemanager = messageManagerInitializer.run();
        UserManagerInitializer userManagerInitializer = new UserManagerInitializer();
        usermanager = userManagerInitializer.run();

        while (true){
            System.out.println("Name:" + attendee);
            System.out.println("Attendee");
            System.out.println("[1] Schedule of events that I can sign up for.\n" +
                    "[2] See events that I have signed up for\n" +
                    "[3] Send a message\n" +
                    "[4] See messages \n" +
                    "[e] exit");
            command = reader.nextLine();
            switch (command) {
                case "e":
                    break;

                case "1":
                    // Show a list of schedule which they can sign up for.
                    ArrayList<String> example_list = eventmanager.canSignUp(attendee);
                    for (int i = 0; i < example_list.size(); i++) {
                        System.out.println(eventmanager.findEventStr(Integer.parseInt(example_list.get(i))));
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.nextLine();
                    if (!("e".equals(command))) {
                        try {
                            eventmanager.signUp(example_list.get(Integer.parseInt(command)), attendee);
                        } catch (Exception e) {
                            System.out.println("Cant do that, bye bye.");
                            continue;
                        }
                        usermanager.addSignedEvent(command,attendee);
                        System.out.println("Success! Press something to continue");
                        command = reader.nextLine();
                    }
                    else{
                        System.out.println("Exiting");
                    }
                    continue;
                case "2":
                    //See the events that user have signed up for
                    ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
                    for(int i = 0; i < eventsList.size(); i++){
                        System.out.println(eventsList.get(i)); //TODO: fix this
                    }
                    System.out.println("[e] exit to main menu");
                    continue;
                case "3":
                    System.out.println("To Who?");
                    ArrayList<String> msglst= usermanager.getContactList(attendee);
                    for(int i = 0; i < msglst.size(); i++){
                        System.out.println("[" + i + "] " + msglst.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.nextLine();
                    if (!("e".equals(command))) {
                        String receiver = msglst.get(Integer.parseInt(command)); // TODO: WHAT IF input wrong?
                        System.out.println("Yo, now input your message. Hint: \\n and stuff.");
                        message = reader.nextLine();
                        messagemanager.sendMessage(attendee, receiver, message);
                        System.out.println("Success! Press something to continue");
                        reader.nextLine();
                    }
                    else{
                        System.out.println("Exiting");
                    }
                    continue;
                case "4":
                    ArrayList<String> inbox = messagemanager.getInbox(attendee);
                    for(int i = 0; i < inbox.size(); i++){
                        System.out.println("[" + i + "] " + inbox.get(i));
                    }
                    System.out.println("press enter to exit to main menu");
                    reader.nextLine();
                    continue;
                case "default":
                    System.out.println("Press the right key.");
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        //write.run();

    }
}
