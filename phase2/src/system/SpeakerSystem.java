package system;

import presenter.*;
import readWrite.Write;

import java.util.ArrayList;

/**
 * <h1>Speaker System</h1>
 * The SpeakerSystem program implements the system of Speaker user.
 */
public class SpeakerSystem extends UserSystem {

    /**
     * Constructor for SpeakerSystem
     *
     * @param myName A String, which is the username of speaker who logged in.
     */
    public SpeakerSystem(String myName) {
        super(myName);
    }

    /**
     * Run the Speaker System. Print out speaker's menu, and perform speaker's operations.
     */
    @Override
    public void run() {

        while (conference != null) {
            Presenter.name(myName);
            Presenter.userType("Speaker");
            SpeakerPresenter.speakerMenu();
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    usermanager.logout(myName);
                    break;
                case "1":   //See the events that the speaker gave
                    checkTalkedEvent();
                    continue;
                case "2":   //See the messages that the speaker gave
                    getSentMessages();
                    continue;
                case "3":  //send messages to all Attendees who signed up for a particular event
                    sendMessageToEvent();
                    continue;
                case "4": //send messages to a particular Attendee who signed up for a particular event
                    sendMessageToOneAttendee();
                    continue;
                case "5": //respond to an Attendee
                    respondToAttendee();
                    continue;
                case "6": // send message (regular)
                    sendMessageToSomeone();
                case "7": //see message inbox
                    seeMessages();
                    continue;
                case "8":
                    makeNewRequest();
                    continue;
                case "9":
                    seeMyRequests();
                    continue;
                case "10":
                    deleteRequests();
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
     * Send messages to all attendees in a particular event.
     */
    private void sendMessageToEvent() {
        try{
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                if (eventmanager.getSpeakers(Integer.parseInt(String.valueOf(i))).contains(myName)) {
                    Presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
                }
            }
            Presenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                Presenter.inputPrompt("message");
                String messageToAllAttendees = reader.nextLine();
                try {
                    ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                    messagemanager.sendToList(myName, attendeeList, messageToAllAttendees);
                } catch (NullPointerException e) {
                    Presenter.printErrorMessage(e);
                }
                Presenter.continuePrompt();
                reader.nextLine();
            } else {
                Presenter.defaultPrint("This is not your event. Please check your input. Exiting to main menu.");
            }
        } catch (Exception e){
            Presenter.defaultPrint(e.getMessage());
        }
    }

    /*
     * Send messages to one particular attendee in a particular event.
     */
    private void sendMessageToOneAttendee() {
        try {
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                if (eventmanager.getSpeakers(Integer.parseInt(String.valueOf(i))).contains(myName)) {
                    Presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
                }
            }
            Presenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
            for (String s : attendeeList) {
                Presenter.defaultPrint(s);
            }
            Presenter.inputPrompt("receiver");
            String receiver = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                Presenter.inputPrompt("message");
                String messageToOneAttendee = reader.nextLine();
                try {
                    if (attendeeList.contains(receiver)) {
                        messagemanager.sendMessage(myName, receiver, messageToOneAttendee);
                    } else {
                        Presenter.defaultPrint("There is no such user in that event.");
                    }
                } catch (NullPointerException e) {
                    Presenter.printErrorMessage(e);
                }
                Presenter.continuePrompt();
                reader.nextLine();
            } else {
                Presenter.defaultPrint("This is not your event. Please check your input. Exiting to main menu.");
            }
        } catch (Exception e){
            Presenter.defaultPrint(e.getMessage());
        }
    }

    /*
     * Get the messages that the speaker has sent.
     */
    private void getSentMessages() {
        ArrayList<String> messageList = messagemanager.getSent(myName);
        for (int i = 0; i < messageList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + messageList.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }


    /*
     * Respond to an attendee who has sent message to the speaker.
     */
    private void respondToAttendee() {
        Presenter.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(myName);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(myName);
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
                messagemanager.sendMessage(myName, receiver, message);
                Presenter.success();
            } else if ("e".equals(cmd)) {
                Presenter.exitingToMainMenu();
            } else {
                Presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            Presenter.printErrorMessage(e);
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Check the events that speaker gave.
     */
    private void checkTalkedEvent() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        for (String s : eventsList) {
            if (eventmanager.getSpeakers(Integer.valueOf(s)).contains(myName)) {
                System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
            }
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

}


