package system;

import ReadWrite.Write;
import event.EventManager;
import message.MessageManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeSystem implements SeeMessages, SendMessageToSomeone{
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
        while (true){
            System.out.println("Name:" + attendee);
            System.out.println("Attendee");
            System.out.println("[1] Schedule of events that I can sign up for.\n" +
                    "[2] See events that I have signed up for\n" +
                    "[3] Send a message\n" +
                    "[4] See messages\n" +
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
                            System.out.println("The event doesn't exist.");
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
                        System.out.println(eventmanager.findEventStr(Integer.valueOf(eventsList.get(i))));
                    }
                    System.out.println("press enter to exit");
                    command = reader.nextLine();
                    continue;
                case "3":
                    //TODO: Show contact list and make able to choose whom to send.
                    sendMessageToSomeone(attendee);
                    reader.nextLine();
                    continue;
                case "4":
                    seeMessages(attendee);
                    reader.nextLine();
                    continue;
                default:
                    System.out.println("Please press the right key.");
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    @Override
    public void seeMessages(String attendee) {
        ArrayList<String> inbox = messagemanager.getInbox(attendee);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i));
        }
        System.out.println("press enter to exit to main menu");
    }

    @Override
    public void sendMessageToSomeone(String attendee) {
        System.out.println("Which single person you want to send message?");
        ArrayList<String> contactList= usermanager.getContactList(attendee);
        for(int i = 0; i < contactList.size(); i++){
            System.out.println("[" + i + "] " + contactList.get(i));
        }
        System.out.println("[e] exit to main menu");
        String receive = reader.nextLine();
        if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
            String receiver = contactList.get(Integer.parseInt(receive));
            System.out.println("Now input your message. Hint: Type \\n for changing of lines if you want.");
            String message = reader.nextLine();
            messagemanager.sendMessage(attendee, receiver, message);
            System.out.println("Success! Press enter to continue");
        } else {
            System.out.println("Press enter to exit to main menu");
        }
    }
}
