package system;

import ReadWrite.Write;
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
            System.out.println(Presenter.name() + speaker);
            System.out.println(Presenter.user("Speaker"));
            System.out.println(Presenter.speakerMenu());
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    usermanager.logout(speaker);
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
                    respondToAttendee(speaker);
                    reader.nextLine();
                    continue;
                case "5": //see message inbox
                    seeMessages(speaker);
                    reader.nextLine();
                    continue;
                default:
                    System.out.println(Presenter.wrongKeyRemainderInMenu());
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
        System.out.println(Presenter.pressEnterToMainMenu());
    }

    @Override
    public void sendMessageToAll(String speaker, String object) {
        if ("attendee".equals(object)) {
            System.out.println(Presenter.input("eventIdSendMessage"));
            String eventId = reader.nextLine();
            if(eventmanager.getSpeakers(Integer.parseInt(eventId)).equals(speaker)){
            System.out.println(Presenter.input("message"));
            String messageToAllAttendees = reader.nextLine();
            try {
                ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                messagemanager.sendToList(speaker, attendeeList, messageToAllAttendees);
            } catch (Exception e) {
                System.out.println(Presenter.invalid("eventId"));
            }
            System.out.println(Presenter.successPressEnter());
            } else {
                System.out.println("This is not your event. Please check your input. Exiting to main menu.");
            }
        }
    }

    @Override
    public void sendMessageToSomeone(String speaker) {
        System.out.println(Presenter.input("username"));
        ArrayList<String> contactList= usermanager.getContactList(speaker);
        for(int i = 0; i < contactList.size(); i++){
            System.out.println("[" + i + "] " + contactList.get(i));
        }
        System.out.println(Presenter.pressEtoMainMenu());
        String receive = reader.nextLine();
        try{
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                System.out.println(Presenter.input("message"));
                String message = reader.nextLine();
                messagemanager.sendMessage(speaker, receiver, message);
                System.out.println(Presenter.successPressEnter());
                reader.nextLine();
            } else {
                System.out.println(Presenter.inputOutOfRange());
            }
        } catch(Exception e) {
            System.out.println(Presenter.invalid("default"));
        }
    }

    private void getSentMessages(){
        ArrayList<String> messageList = messagemanager.getSent(speaker);
        for (int i = 0; i < messageList.size(); i++) {
            System.out.println("[" + i + "] " + messageList.get(i));
        }
        System.out.println(Presenter.pressEnterToMainMenu());
    }

    public void respondToAttendee(String speaker){
        System.out.println(Presenter.input("messageToRespond"));
        ArrayList<String> msgInbox = messagemanager.getInbox(speaker);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(speaker);
        if(msgInbox.isEmpty()){
            System.out.println(Presenter.emptyInbox());
            return;
        }
        for(int i = 0; i < msgInbox.size(); i++){
            System.out.println("[" + i + "] " + msgInbox.get(i));
        }
        System.out.println(Presenter.pressEtoMainMenu());
        String cmd = reader.nextLine();
        try{
        if(!("e".equals(cmd))&&Integer.parseInt(cmd)<msgInbox.size()&&Integer.parseInt(cmd)>=0){
            String receiver = inboxSender.get(Integer.parseInt(cmd));
            System.out.println(Presenter.input("message"));
            String message = reader.nextLine();
            messagemanager.sendMessage(speaker, receiver, message);
            System.out.println(Presenter.success());
        } else if("e".equals(cmd)){
            System.out.println(Presenter.exitToMainMenu());
        } else {
            System.out.println(Presenter.inputOutOfRange());
        }
        } catch(Exception e) {
            System.out.println(Presenter.invalid("default"));
        }
    }
}


