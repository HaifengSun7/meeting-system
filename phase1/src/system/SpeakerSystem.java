package system;

import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import readWrite.Write;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Speaker System</h1>
 * The SpeakerSystem program implements the system of Speaker user.
 */
public class SpeakerSystem {
    private final String speaker;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    /**
     * Constructor for SpeakerSystem
     *
     * @param speaker A String, which is the username of speaker who logged in.
     */
    public SpeakerSystem(String speaker) {
        this.speaker = speaker;
    }

    /**
     * Run the Speaker System. Print out speaker's menu, and perform speaker's operations.
     */
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
                    Presenter.wrongKeyReminder();
                    continue;
            }
            break;
        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    /*
     * See the messages that the speaker got from other users.
     */
    private void seeMessages() {
        ArrayList<String> inbox = messagemanager.getInbox(speaker);
        for (int i = 0; i < inbox.size(); i++) {
            System.out.println("[" + i + "] " + inbox.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Send messages to all attendees.
     */
    private void sendMessageToAll() {
        Presenter.inputPrompt("eventIdSendMessage");
        String eventId = reader.nextLine();
        if (eventmanager.getSpeakers(Integer.parseInt(eventId)).equals(speaker)) {
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

    /*
     * Send messages to a specific person.
     */
    private void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        ArrayList<String> contactList = usermanager.getContactList(speaker);
        for (int i = 0; i < contactList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try {
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(speaker, receiver, message);
            } else {
                Presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            Presenter.invalid("default");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Get the messages that the speaker has sent.
     */
    private void getSentMessages() {
        ArrayList<String> messageList = messagemanager.getSent(speaker);
        addAllToMessageList();
        for (int i = 0; i < messageList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + messageList.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Add all senders of the inbox messages to speaker's contact list.
     */
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(speaker);
        for (String sender : inboxSenders) {
            usermanager.addContactList(sender, speaker);
        }
        Presenter.autoAddToMessageList();
    }

    /*
     * Respond to an attendee who has sent message to the speaker.
     */
    private void respondToAttendee() {
        Presenter.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(speaker);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(speaker);
        if (msgInbox.isEmpty()) {
            Presenter.emptyInbox();
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        for (int i = 0; i < msgInbox.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + msgInbox.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String cmd = reader.nextLine();
        try {
            if (!("e".equals(cmd)) && Integer.parseInt(cmd) < msgInbox.size() && Integer.parseInt(cmd) >= 0) {
                String receiver = inboxSender.get(Integer.parseInt(cmd));
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(speaker, receiver, message);
                Presenter.success();
            } else if ("e".equals(cmd)) {
                Presenter.exitingToMainMenu();
            } else {
                Presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            Presenter.invalid("default");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Check the events that speaker gave.
     */
    private void checkTalkedEvent() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(speaker);
        for (String s : eventsList) {
            if (eventmanager.getSpeakers(Integer.valueOf(s)).equals(speaker)) {
                System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
            }
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }
}


