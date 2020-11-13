package system;

import ReadWrite.Write;
import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeSystem implements SeeMessages, SendMessageToSomeone{
    private final String attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();
    public String command;

    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    public void run() {
        while (true){
            System.out.println(Presenter.name() + attendee);
            System.out.println(Presenter.user("Attendee"));
            System.out.println(Presenter.attendeeMenu());
            command = reader.nextLine();
            switch (command) {
                case "e":
                    usermanager.logout(attendee);
                    break;
                case "1":
                    SignUpForEvent();
                    continue;
                case "2":
                    checkSignedUp();
                    command = reader.nextLine();
                    continue;
                case "3":
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
        System.out.println("Which person you want to send message?");
        ArrayList<String> contactList= usermanager.getContactList(attendee);
        for(int i = 0; i < contactList.size(); i++){
            System.out.println("[" + i + "] " + contactList.get(i));
        }
        System.out.println("[e] exit to main menu");
        String receive = reader.nextLine();
        try{
        if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) &&
                (Integer.parseInt(receive) < contactList.size())) {
            String receiver = contactList.get(Integer.parseInt(receive));
            System.out.println("Now input your message. Hint: Type \\n for changing of lines if you want.");
            String message = reader.nextLine();
            messagemanager.sendMessage(attendee, receiver, message);
            System.out.println("Success! Press enter to continue");
        } else {
            System.out.println("input out of range, exiting to main menu...");
        }
        } catch(Exception e) {
            System.out.println("Invalid Input, exiting to main menu...");
        }
    }

    private void SignUpForEvent(){
        ArrayList<String> example_list = eventmanager.canSignUp(attendee);
        System.out.println("Here are the existing events. Enter serial number to choose the event.");
        for (int i = 0; i < example_list.size(); i++) {
            System.out.println("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        System.out.println("[e] exit to main menu");
        command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.signUp(example_list.get(Integer.parseInt(command)), attendee);
            } catch (Exception e) {
                System.out.println("The event doesn't exist.");
                return;
            }
            usermanager.addSignedEvent(command, attendee);
            System.out.println("Success!\n");
        }
        else{
            System.out.println("Return to main menu.");
        }
    }

    private void checkSignedUp(){
        ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
        for (String s : eventsList) {
            System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
        }
        System.out.println("Press enter to exit");
    }
}
