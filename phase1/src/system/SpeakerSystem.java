package system;

import event.EventManager;
import message.MessageManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class SpeakerSystem {
    private final String speaker;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public SpeakerSystem(String speaker) {
        this.speaker = speaker;
    }

    public void run() {
        //TODO: READ.
        while (true) {
            System.out.println("Name:" + speaker.toString());
            System.out.println("Speaker");
            System.out.println("[1] See a list of talks.\n" +
                    "[2] Message all Attendees who signed up for a particular event\n" +
                    "[3] Message a particular Attendee who signed up for a particular event\n " +
                    "[e] exit");
            String command = reader.next();

            switch (command) {
                case "e":
                    break;
                case "1":   //See the talks that the speaker is giving
                    ArrayList<String> messageList = messagemanager.getSent(speaker);
                    for (int i = 0; i < messageList.size(); i++) {
                        System.out.println("[" + i + "] " + messageList.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                    continue;
                case "2":  //send messages to all Attendees who signed up for a particular event
                    System.out.println("Event name that you want to send messages");
                    reader.nextLine();
                    String eventName = reader.nextLine();
                    System.out.println("Messages that you are sending to all attendees");
                    String messageToAllAttendees = reader.nextLine();
                    ArrayList<String> attendeeList = eventmanager.getAttendees(eventName);
                    messagemanager.sendToList(speaker, attendeeList, messageToAllAttendees);
                    System.out.println("Success! Press something to continue");
                    reader.next();
                    continue;
                case "3": //send messages to a particular Attendee who signed up for a particular event
                    System.out.println("Which single person you want to send message?");
                    ArrayList<String> msglst = usermanager.getContactList(speaker);
                    for (int i = 0; i < msglst.size(); i++) {
                        System.out.println("[" + i + "] " + msglst.get(i));
                    }
                    System.out.println("[e] exit to main menu");
                    command = reader.next();
                    if (!("e".equals(command))) {
                        String receiver = msglst.get(Integer.parseInt(command)); // TODO: what if input wrong?
                        System.out.println("Yo, now input your message. Hint: \\n and stuff."); // TODO: string is bad.
                        message = reader.nextLine();
                        messagemanager.sendMessage(speaker, receiver, message);
                        System.out.println("Success! Press something to continue");
                        reader.next();
                    } else {
                        System.out.println("Exiting");
                    }
                    continue;
            }
            break;
        }
    }
}

