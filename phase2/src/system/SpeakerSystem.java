package system;

import event.exceptions.NoSuchConferenceException;
import presenter.SpeakerPresenter;

import java.util.ArrayList;

/**
 * <h1>Speaker System</h1>
 * The SpeakerSystem program implements the system of Speaker user.
 */
public class SpeakerSystem extends UserSystem {

    private final SpeakerPresenter presenter;


    /**
     * Constructor for SpeakerSystem
     *
     * @param myName A String, which is the username of speaker who logged in.
     */
    public SpeakerSystem(String myName) {
        super(myName);
        this.presenter = new SpeakerPresenter();
    }

    /**
     * Run the Speaker System. Print out speaker's menu, and perform speaker's operations.
     */
    @Override
    public void run() {

        while (conference != null) {
            presenter.name(myName);
            presenter.userType("Speaker");
            presenter.speakerMenu();
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
                case "11":
                    markUnreadMessages();
                    continue;
                case "12":
                    deleteMessage();
                    continue;
                case "13":
                    archiveMessage();
                    continue;
                case "14":
                    unArchiveMessage();
                    continue;
                case "15":
                    seeArchivedMessage();
                    continue;
                case "save":
                    save();
                    continue;
                default:
                    presenter.wrongKeyReminder();
                    continue;
            }
            break;
        }
        save();
    }

    /*
     * Send messages to all attendees in a particular event.
     */
    private void sendMessageToEvent() {
        try{
            ShowAllEvents();
            presenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                presenter.inputPrompt("message");
                String messageToAllAttendees = reader.nextLine();
                try {
                    ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                    messagemanager.sendToList(myName, attendeeList, messageToAllAttendees);
                } catch (NullPointerException e) {
                    presenter.printErrorMessage(e);
                }
                presenter.continuePrompt();
                reader.nextLine();
            } else {
                presenter.defaultPrint("This is not your event. Please check your input. " +
                        "Exiting to main menu.");
            }
        } catch (Exception e){
            presenter.defaultPrint(e.getMessage());
        }
    }

    /*
     * Send messages to one particular attendee in a particular event.
     */
    private void sendMessageToOneAttendee() {
        try {
            ShowAllEvents();
            presenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
            for (String s : attendeeList) {
                presenter.defaultPrint(s);
            }
            presenter.inputPrompt("receiver");
            String receiver = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                presenter.inputPrompt("message");
                String messageToOneAttendee = reader.nextLine();
                try {
                    if (attendeeList.contains(receiver)) {
                        messagemanager.sendMessage(myName, receiver, messageToOneAttendee);
                    } else {
                        presenter.defaultPrint("There is no such user in that event.");
                    }
                } catch (NullPointerException e) {
                    presenter.printErrorMessage(e);
                }
                presenter.continuePrompt();
                reader.nextLine();
            } else {
                presenter.defaultPrint("This is not your event. Please check your input. " +
                        "Exiting to main menu.");
            }
        } catch (Exception e){
            presenter.defaultPrint(e.getMessage());
        }
    }

    private void ShowAllEvents() throws NoSuchConferenceException {
        ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
        for (int i = 0; i < allEvents.size(); i++) {
            if (eventmanager.getSpeakers(Integer.parseInt(String.valueOf(i))).contains(myName)) {
                presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
        }
    }

    /*
     * Get the messages that the speaker has sent.
     */
    private void getSentMessages() {
        ArrayList<String> messageList = messagemanager.getSent(myName);
        if (messageList.size() == 0) {
            presenter.noMessage();
        } else {
            for (int i = 0; i < messageList.size(); i++) {
                presenter.defaultPrint("[" + i + "] " + messageList.get(i));
            }
            presenter.continuePrompt();
            reader.nextLine();
        }
    }


    /*
     * Respond to an attendee who has sent message to the speaker.
     */
    private void respondToAttendee() {
        presenter.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(myName);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(myName);
        if (msgInbox.isEmpty()) {
            presenter.emptyInbox();
            presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        for (int i = 0; i < msgInbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + msgInbox.get(i));
        }
        presenter.exitToMainMenuPrompt();
        String cmd = reader.nextLine();
        try {
            if (!("e".equals(cmd)) && Integer.parseInt(cmd) < msgInbox.size() && Integer.parseInt(cmd) >= 0) {
                String receiver = inboxSender.get(Integer.parseInt(cmd));
                presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(myName, receiver, message);
                presenter.success();
            } else if ("e".equals(cmd)) {
                presenter.exitingToMainMenu();
            } else {
                presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            presenter.printErrorMessage(e);
        }
        presenter.continuePrompt();
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
        presenter.continuePrompt();
        reader.nextLine();
    }

}


