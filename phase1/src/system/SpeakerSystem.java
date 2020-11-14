package system;

import readWrite.Write;
import event.EventManager;
import message.MessageManager;
import user.UserManager;
import textUI.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Speaker System</h1>
 * The SpeakerSystem program implements the system of Speaker user.
 * @author Haifeng Sun, Wei Tao
 * @version 1.0.0
 */
public class SpeakerSystem{
    private final String speaker;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public SpeakerSystem(String speaker) {
        this.speaker = speaker;
    }

    /**
     * Run the Speaker System. Print out speaker's menu, and perform speaker's operations.
     */
    public void run() {
        while (true) {
            TextUI.name(speaker);
            TextUI.userType("Speaker");
            TextUI.speakerMenu();
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    usermanager.logout(speaker);
                    break;
                case "1":   //See the events that the speaker gave
                    checkTalkedEvent();
                    continue;
                case "2":   //See the messages that the speaker gave
                    getSentMessages();
                    continue;
                case "3":  //send messages to all Attendees who signed up for a particular event
                    sendMessageToAll();
                    continue;
                case "4": //send messages to a particular Attendee who signed up for a particular event
                    sendMessageToSomeone();
                    continue;
                case "5": //respond to an Attendee
                    respondToAttendee();
                    continue;
                case "6": //see message inbox
                    seeMessages();
                    continue;
                default:
                    TextUI.wrongKeyReminder();
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    /**
     * See the messages that the speaker got from other users.
     */
    private void seeMessages() {
        ArrayList<String> inbox = messagemanager.getInbox(speaker);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i));
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }

    /**
     * Send messages to all attendees.
     */
    private void sendMessageToAll() {
        TextUI.inputPrompt("eventIdSendMessage");
        String eventId = reader.nextLine();
        if(eventmanager.getSpeakers(Integer.parseInt(eventId)).equals(speaker)){
            TextUI.inputPrompt("message");
            String messageToAllAttendees = reader.nextLine();
            try {
                ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                messagemanager.sendToList(speaker, attendeeList, messageToAllAttendees);
            } catch (Exception e) {
                TextUI.invalid("eventId");
            }
            TextUI.continuePrompt();
            reader.nextLine();
        } else {
            TextUI.defaultPrint("This is not your event. Please check your input. Exiting to main menu.");
        }
    }

    /**
     * Send messages to a specific person.
     */
    private void sendMessageToSomeone(){
        TextUI.inputPrompt("receiver");
        ArrayList<String> contactList= usermanager.getContactList(speaker);
        for(int i = 0; i < contactList.size(); i++){
            TextUI.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        TextUI.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try{
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                TextUI.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(speaker, receiver, message);
            } else {
                TextUI.inputOutOfRange();
            }
        } catch(Exception e) {
            TextUI.invalid("default");
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }

    /**
     * Get the messages that the speaker has sent.
     */
    private void getSentMessages(){
        ArrayList<String> messageList = messagemanager.getSent(speaker);
        addAllToMessageList();
        for (int i = 0; i < messageList.size(); i++) {
            TextUI.defaultPrint("[" + i + "] " + messageList.get(i));
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }

    /**
     * Add all senders of the inbox messages to speaker's contact list.
     */
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(speaker);
        for(String sender: inboxSenders){
            usermanager.addContactList(sender, speaker);
        }
        TextUI.defaultPrint("Added all senders to your contact list automatically.");
    }

    /**
     * Respond to an attendee who has sent message to the speaker.
     */
    private void respondToAttendee(){
        TextUI.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(speaker);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(speaker);
        if(msgInbox.isEmpty()){
            TextUI.emptyInbox();
            TextUI.continuePrompt();
            reader.nextLine();
            return;
        }
        for(int i = 0; i < msgInbox.size(); i++){
            TextUI.defaultPrint("[" + i + "] " + msgInbox.get(i));
        }
        TextUI.exitToMainMenuPrompt();
        String cmd = reader.nextLine();
        try{
        if(!("e".equals(cmd))&&Integer.parseInt(cmd)<msgInbox.size()&&Integer.parseInt(cmd)>=0){
            String receiver = inboxSender.get(Integer.parseInt(cmd));
            TextUI.inputPrompt("message");
            String message = reader.nextLine();
            messagemanager.sendMessage(speaker, receiver, message);
            TextUI.success();
        } else if("e".equals(cmd)){
            TextUI.exitingToMainMenu();
        } else {
            TextUI.inputOutOfRange();
        }
        } catch(Exception e) {
            TextUI.invalid("default");
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }

    /**
     * Check the events that speaker gave.
     */
    private void checkTalkedEvent(){
        ArrayList<String> eventsList = usermanager.getSignedEventList(speaker);
        for (String s : eventsList) {
            if (eventmanager.getSpeakers(Integer.valueOf(s)).equals(speaker)){
                System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
            }
        }
        TextUI.continuePrompt();
        reader.nextLine();
    }
}


