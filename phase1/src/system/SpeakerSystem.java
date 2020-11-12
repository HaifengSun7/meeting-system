package system;

import ReadWrite.Write;
import event.EventManager;
import message.MessageManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class SpeakerSystem implements SeeMessages, SendMessageToSomeone, SendMessageToAll{
    private final String speaker;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public SpeakerSystem(String speaker) {
        this.speaker = speaker;
    }

    public void run() {
        while (true) {
            System.out.println("Name:" + speaker);
            System.out.println("Speaker");
            System.out.println("[1] See a list of messages the speaker gave.\n" +
                    "[2] Message all Attendees who signed up for a particular event\n" +
                    "[3] Message a particular Attendee who signed up for a particular event\n" +
                    "[4] Respond to an attendee\n" +
                    "[e] exit");
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    break;
                case "1":   //See the messages that the speaker gave
                    getSentMessages();
                    reader.nextLine();
                    continue;
                case "2":  //send messages to all Attendees who signed up for a particular event
                    sendMessageToAll(speaker, "attendee");
                    reader.nextLine();
                    continue;
                case "3": //send messages to a particular Attendee who signed up for a particular event
                    sendMessageToSomeone(speaker);
                    continue;
                case "4": //respond to an Attendee
                    respondToAttendee();
                    reader.nextLine();
                    continue;
                default:
                    System.out.println("Please press the right key.");
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        //write.run();
    }

    @Override
    public void seeMessages(String speaker) {
        ArrayList<String> inbox = messagemanager.getInbox(speaker);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i));
        }
        System.out.println("Press enter to exit to main menu");
    }

    @Override
    public void sendMessageToAll(String speaker, String object) {
        if ("attendee".equals(object)) {
            System.out.println("Event Id that you want to send messages");
            String eventId = reader.nextLine();
            System.out.println("Messages that you are sending to all attendees");
            String messageToAllAttendees = reader.nextLine();
            try {
                ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                messagemanager.sendToList(speaker, attendeeList, messageToAllAttendees);
            } catch (Exception e) {
                System.out.println("Don't have that event, please check the availability of that event");
            }
            System.out.println("Success! Press something to continue");
        }
    }

    @Override
    public void sendMessageToSomeone(String speaker) {
        System.out.println("Which single person you want to send message?");
        ArrayList<String> contactList= usermanager.getContactList(speaker);
        for(int i = 0; i < contactList.size(); i++){
            System.out.println("[" + i + "] " + contactList.get(i));
        }
        System.out.println("[e] exit to main menu");
        String receive = reader.nextLine();
        if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
            String receiver = contactList.get(Integer.parseInt(receive));
            System.out.println("Now input your message. Hint: \\n and stuff.");
            String message = reader.nextLine();
            messagemanager.sendMessage(speaker, receiver, message);
            System.out.println("Success! Press anything to continue");
            reader.nextLine();
        } else {
            System.out.println("Invalid Input, exit to main menu and try again\n");
        }
    }

    private void getSentMessages(){
        ArrayList<String> messageList = messagemanager.getSent(speaker);
        for (int i = 0; i < messageList.size(); i++) {
            System.out.println("[" + i + "] " + messageList.get(i));
        }
        System.out.println("press any key to return to menu");
    }

    private void respondToAttendee(){
        System.out.println("Which message would you like to respond?");
        ArrayList<String> msgInbox = messagemanager.getInbox(speaker);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(speaker);
        if(msgInbox.isEmpty()){
            System.out.println("Your inbox is empty. Press enter to exit to main menu.");
            return;
        }
        for(int i = 0; i < msgInbox.size(); i++){
            System.out.println("[" + i + "] " + msgInbox.get(i));
        }
        System.out.println("[e] exit to main menu");
        String cmd = reader.next();
        if(!("e".equals(cmd))&&Integer.parseInt(cmd)<msgInbox.size()&&Integer.parseInt(cmd)>=0){
            String receiver = inboxSender.get(Integer.parseInt(cmd));
            System.out.println("Input your message");
            String message = reader.nextLine();
            messagemanager.sendMessage(speaker, receiver, message);
            System.out.println("Success");
            reader.next();
        } else if("e".equals(cmd)){
            System.out.println("Exiting\n");
        } else {
            System.out.println("Invalid Input, exit to main menu and try again\n");
        }
    }
}


