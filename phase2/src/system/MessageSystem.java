package system;

import event.EventManager;
import event.exceptions.NoSuchConferenceException;
import message.MessageManager;
import presenter.Presenter;
import presenter.SpeakerPresenter;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The system controlling messages.
 */
public class MessageSystem {
    private final MessageManager messagemanager;
    private final EventManager eventmanager;
    private final UserManager usermanager;
    private final Presenter presenter;
    private final SpeakerPresenter speakerPresenter;
    protected Scanner reader = new Scanner(System.in);
    private final String myName;
    private final String conference;

    /**
     * Constructs the system.
     *
     * @param usermanager the userManager created when the user logged in.
     * @param eventmanager the eventManager created when the user logged in.
     * @param messagemanager the messageManager created when the user logged in.
     * @param myName the userName of the user logged in.
     * @param conference the conference the user chooses.
     */
    public MessageSystem(UserManager usermanager, EventManager eventmanager, MessageManager messagemanager, String myName, String conference) {
        this.messagemanager = messagemanager;
        this.usermanager = usermanager;
        this.eventmanager = eventmanager;
        this.presenter = new Presenter();
        this.speakerPresenter = new SpeakerPresenter();
        this.myName = myName;
        this.conference = conference;
    }

    /**
     * See the messages that the user got from other users.
     */
    protected void seeMessages() {
        addAllToMessageList();
        ArrayList<String> inbox = messagemanager.getInbox(myName);
        for (int i = 0; i < inbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Send messages to a specific person in the contact list.
     */
    protected void sendMessageToSomeone() {
        presenter.inputPrompt("receiver");
        ArrayList<String> contactList = usermanager.getContactList(myName);
        for (int i = 0; i < contactList.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try {
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(myName, receiver, message);
            } else if ("e".equals(receive)) {
                presenter.exitingToMainMenu();
                return;
            } else {
                presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            presenter.invalid("");
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Add all senders of the inbox messages to user's contact list.
     */
    protected void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(myName);
        for (String sender : inboxSenders) {
            usermanager.addContactList(sender, myName);
        }
        presenter.autoAddToMessageList();
    }

    /**
     * Choose and mark a message as unread.
     */
    protected void markUnreadMessages() {
        addAllToMessageList(); // Yes. I think we do need this.
        ArrayList<String> unreadInbox = messagemanager.getUnread(myName);
        presenter.inputPrompt("enter number in square bracket to mark message as read");
        for (int i = 0; i < unreadInbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + unreadInbox.get(i));
        }
        presenter.defaultPrint("[a] mark all as read");
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();

        switch (command) {
            case "e":
                presenter.exitingToMainMenu();
                break;
            case "a":
                try {
                    messagemanager.markAllAsRead(myName);
                    presenter.success();
                } catch (Exception e) {
                    presenter.printErrorMessage(e);
                }
                break;
            default:
                try {
                    messagemanager.markKthAsRead(myName, Integer.valueOf(command));
                } catch (Exception e) {
                    presenter.defaultPrint("Input out of range");
                    return;
                }
                presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Choose a message to delete.
     */
    protected void deleteMessage() {
        ArrayList<String> inbox = messagemanager.getAll(myName);
        presenter.inputPrompt("enter number in square bracket to delete message. Warning: you might mis-deleted messages you haven't read");
        for (int i = 0; i < inbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.deleteKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Choose a message to archive
     */
    protected void archiveMessage() {
        ArrayList<String> inbox = messagemanager.getAll(myName);
        presenter.inputPrompt("enter number in square bracket to archive message. " +
                "Warning: you might archive message that you have archived");
        for (int i = 0; i < inbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.archiveKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Choose a message and unarchive it.
     */
    protected void unArchiveMessage() {
        ArrayList<String> archivedInbox = messagemanager.getArchived(myName);
        presenter.inputPrompt("enter number in square bracket to unarchive message.");
        for (int i = 0; i < archivedInbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + archivedInbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.unArchiveKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * See all the archived messages.
     */
    protected void seeArchivedMessage() {
        ArrayList<String> archivedMessage = messagemanager.getArchived(myName);
        for (int i = 0; i < archivedMessage.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + archivedMessage.get(i));
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Organizer send message to a specific person.
     */
    protected void sendMessageFromOrganizerToSomeone() {
        presenter.inputPrompt("receiver");
        presenter.exitToMainMenuPrompt();
        String target = reader.nextLine();
        if ("e".equals(target)) {
            presenter.exitingToMainMenu();
        } else {
            if (usermanager.getAllUsernames().contains(target)) {
                presenter.inputPrompt("message");
                String msg = reader.nextLine();
                messagemanager.sendMessage(myName, target, msg);
                presenter.success();
            } else {
                presenter.invalid("username");
            }
        }
    }

    /**
     * Send messages to everyone.
     *
     * @param user The type of user that the message is sent to.
     */
    protected void sendMessageToAll(String user) {
        ArrayList<String> receivers = new ArrayList<>();
        switch (user) {
            case "speaker":
                receivers = usermanager.getSpeakers();
                break;
            case "attendee":
                receivers = usermanager.getAttendees();
                break;
        }
        presenter.inputPrompt("message");
        String message = reader.nextLine();
        messagemanager.sendToList(myName, receivers, message);
        presenter.continuePrompt();

    }

    /**
     * Send messages to one particular attendee in a particular event.
     */
    protected void sendMessageToOneAttendee() {
        try {
            ShowAllEvents();
            speakerPresenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
            for (String s : attendeeList) {
                speakerPresenter.defaultPrint(s);
            }
            speakerPresenter.inputPrompt("receiver");
            String receiver = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                speakerPresenter.inputPrompt("message");
                String messageToOneAttendee = reader.nextLine();
                try {
                    if (attendeeList.contains(receiver)) {
                        messagemanager.sendMessage(myName, receiver, messageToOneAttendee);
                    } else {
                        speakerPresenter.defaultPrint("There is no such user in that event.");
                    }
                } catch (NullPointerException e) {
                    speakerPresenter.printErrorMessage(e);
                }
                speakerPresenter.continuePrompt();
                reader.nextLine();
            } else {
                speakerPresenter.defaultPrint("This is not your event. Please check your input. " +
                        "Exiting to main menu.");
            }
        } catch (Exception e) {
            speakerPresenter.defaultPrint(e.getMessage());
        }
    }

    /**
     * Send messages to all attendees in a particular event.
     */
    protected void sendMessageToEvent() {
        try {
            ShowAllEvents();
            speakerPresenter.inputPrompt("eventIdSendMessage");
            String eventId = reader.nextLine();
            if (eventmanager.getSpeakers(Integer.parseInt(eventId)).contains(myName)) {
                speakerPresenter.inputPrompt("message");
                String messageToAllAttendees = reader.nextLine();
                try {
                    ArrayList<String> attendeeList = eventmanager.getAttendees(eventId);
                    messagemanager.sendToList(myName, attendeeList, messageToAllAttendees);
                } catch (NullPointerException e) {
                    speakerPresenter.printErrorMessage(e);
                }
                speakerPresenter.continuePrompt();
                reader.nextLine();
            } else {
                speakerPresenter.defaultPrint("This is not your event. Please check your input. " +
                        "Exiting to main menu.");
            }
        } catch (Exception e) {
            speakerPresenter.defaultPrint(e.getMessage());
        }
    }

    /**
     * Get the messages that the speaker has sent.
     */
    protected void getSentMessages() {
        ArrayList<String> messageList = messagemanager.getSent(myName);
        if (messageList.size() == 0) {
            speakerPresenter.noMessage();
        } else {
            for (int i = 0; i < messageList.size(); i++) {
                speakerPresenter.defaultPrint("[" + i + "] " + messageList.get(i));
            }
            speakerPresenter.continuePrompt();
            reader.nextLine();
        }
    }

    /**
     * Respond to an attendee who has sent message to the speaker.
     */
    protected void respondToAttendee() {
        speakerPresenter.inputPrompt("messageToRespond");
        ArrayList<String> msgInbox = messagemanager.getInbox(myName);
        ArrayList<String> inboxSender = messagemanager.getInboxSender(myName);
        if (msgInbox.isEmpty()) {
            speakerPresenter.emptyInbox();
            speakerPresenter.continuePrompt();
            reader.nextLine();
            return;
        }
        for (int i = 0; i < msgInbox.size(); i++) {
            speakerPresenter.defaultPrint("[" + i + "] " + msgInbox.get(i));
        }
        speakerPresenter.exitToMainMenuPrompt();
        String cmd = reader.nextLine();
        try {
            if (!("e".equals(cmd)) && Integer.parseInt(cmd) < msgInbox.size() && Integer.parseInt(cmd) >= 0) {
                String receiver = inboxSender.get(Integer.parseInt(cmd));
                speakerPresenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(myName, receiver, message);
                speakerPresenter.success();
            } else if ("e".equals(cmd)) {
                speakerPresenter.exitingToMainMenu();
            } else {
                speakerPresenter.inputOutOfRange();
            }
        } catch (Exception e) {
            speakerPresenter.printErrorMessage(e);
        }
        speakerPresenter.continuePrompt();
        reader.nextLine();
    }

    private void ShowAllEvents() throws NoSuchConferenceException {
        ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
        for (int i = 0; i < allEvents.size(); i++) {
            if (eventmanager.getSpeakers(Integer.parseInt(String.valueOf(i))).contains(myName)) {
                speakerPresenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
        }
    }
}

