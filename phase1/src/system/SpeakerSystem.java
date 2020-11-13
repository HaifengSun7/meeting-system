package system;

import readWrite.Write;
import event.EventManager;
import message.MessageManager;
import user.UserManager;
import presenter.*;

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
            Presenter.name(speaker);
            Presenter.userType("Speaker");
            Presenter.speakerMenu();
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    usermanager.logout(speaker);
                    break;
                case "1":   //See the messages that the speaker gave
                    getSentMessages();
                    continue;
                case "2":  //send messages to all Attendees who signed up for a particular event
                    sendMessageToAll(speaker, "attendee");
                    continue;
                case "3": //send messages to a particular Attendee who signed up for a particular event
                    sendMessageToSomeone(speaker);
                    continue;
                case "4": //respond to an Attendee
                    respondToAttendee(speaker);
                    continue;
                case "5": //see message inbox
                    seeMessages(speaker);
                    continue;
                default:
                    Presenter.wrongKeyReminder();
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    @Override
    public void seeMessages(String speaker) {
        ArrayList<String> inbox = messagemanager.getInbox(speaker);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    @Override
    public void sendMessageToAll(String speaker, String object) {
        if ("attendee".equals(object)) {
            Presenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            if(eventmanager.getSpeakers(Integer.parseInt(eventId)).equals(speaker)){
            Presenter.inputPrompt("message");
            String messageToAllAttendees = reader.nextLine();
            try {
                ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                messagemanager.sendToList(speaker, attendeeList, messageToAllAttendees);
            } catch (Exception e) {
                Presenter.invalid("eventId");
            }
            Presenter.continuePrompt();
            reader.nextLine();
            } else {
                Presenter.defaultPrint("This is not your event. Please check your input. Exiting to main menu.");
            }
        }
    }

    @Override
    public void sendMessageToSomeone(String speaker) {
        Presenter.inputPrompt("receiver");
        ArrayList<String> contactList= usermanager.getContactList(speaker);
        for(int i = 0; i < contactList.size(); i++){
            Presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try{
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(speaker, receiver, message);
            } else {
                Presenter.inputOutOfRange();
            }
        } catch(Exception e) {
            Presenter.invalid("default");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    private void getSentMessages(){
        ArrayList<String> messageList = messagemanager.getSent(speaker);
        addAllToMessageList();
        for (int i = 0; i < messageList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + messageList.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(speaker);
        for(String sender: inboxSenders){
            usermanager.addContactList(sender, speaker);
        }
        Presenter.defaultPrint("Added all senders to your contact list automatically.");
    }

    public void respondToAttendee(String speaker){
        Presenter.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(speaker);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(speaker);
        if(msgInbox.isEmpty()){
            Presenter.emptyInbox();
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        for(int i = 0; i < msgInbox.size(); i++){
            Presenter.defaultPrint("[" + i + "] " + msgInbox.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String cmd = reader.nextLine();
        try{
        if(!("e".equals(cmd))&&Integer.parseInt(cmd)<msgInbox.size()&&Integer.parseInt(cmd)>=0){
            String receiver = inboxSender.get(Integer.parseInt(cmd));
            Presenter.inputPrompt("message");
            String message = reader.nextLine();
            messagemanager.sendMessage(speaker, receiver, message);
            Presenter.success();
        } else if("e".equals(cmd)){
            Presenter.exitingToMainMenu();
        } else {
            Presenter.inputOutOfRange();
        }
        } catch(Exception e) {
            Presenter.invalid("default");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }
}


