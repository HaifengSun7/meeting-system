package system;

import readWrite.Write;
import event.EventManager;
import message.MessageManager;
import textUI.TextUI;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Attendee System</h1>
 * The AttendeeSystem program implements the system of Attendee user.
 * @author Haifeng Sun, Wei Tao
 * @version 1.0.0
 */
public class AttendeeSystem{
    private final String attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    /**
     * Run the Attendee System. Print out attendee's menu, and perform attendee's operations.
     */
    public void run() {
        String command;
        while (true){
            TextUI.name(attendee);
            TextUI.userType("Attendee");
            TextUI.attendeeMenu();
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
                    continue;
                case "3":
                    sendMessageToSomeone();
                    continue;
                case "4":
                    seeMessages();
                    continue;
                default:
                    TextUI.wrongKeyReminder();
                    TextUI.invalid("");
                    TextUI.continuePrompt();
                    reader.nextLine();
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    /**
     * See the messages that the attendee got from other users.
     */
    private void seeMessages() {
        addAllToMessageList();
        ArrayList<String> inbox = messagemanager.getInbox(attendee);
        for(int i = 0; i < inbox.size(); i++){
            TextUI.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }

    /**
     * Add all senders of the inbox messages to attendee's contact list.
     */
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(attendee);
        for(String sender: inboxSenders){
            usermanager.addContactList(sender, attendee);
        }
        TextUI.defaultPrint("Added all senders to your contact list automatically.");
    }

    /**
     * Send messages to a specific person.
     */
    private void sendMessageToSomeone(){
        TextUI.inputPrompt("receiver");
        ArrayList<String> contactList= usermanager.getContactList(attendee);
        for(int i = 0; i < contactList.size(); i++){
            TextUI.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        TextUI.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try{
        if (!("e".equals(receive))) {
            String receiver = contactList.get(Integer.parseInt(receive));
            TextUI.inputPrompt("message");
            String message = reader.nextLine();
            messagemanager.sendMessage(attendee, receiver, message);
            TextUI.success();
        } else {
            TextUI.exitingToMainMenu();
        }
        } catch(Exception e) {
            TextUI.invalid("");
        }
    }

    /**
     * Print the events that attendee haven't signed up and choose one event to sign it up.
     */
    private void SignUpForEvent(){
        ArrayList<String> example_list = eventmanager.canSignUp(attendee);
        TextUI.inputPrompt("signUp");
        for (int i = 0; i < example_list.size(); i++) {
            TextUI.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        TextUI.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("attendee", attendee, Integer.parseInt(example_list.get(Integer.parseInt(command))));
            } catch (Exception e) {
                TextUI.invalid("");
                return;
            }
            usermanager.addSignedEvent(command, attendee);
            TextUI.success();
        }
        else{
            TextUI.exitingToMainMenu();
        }
    }

    /**
     * Check the events that attendee have signed up.
     */
    private void checkSignedUp(){
        ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
        for (String s : eventsList) {
            System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }
}
