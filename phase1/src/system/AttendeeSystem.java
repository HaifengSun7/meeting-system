package system;

import event.*;
import message.MessageManager;
import readWrite.*;
import user.UserManager;
import presenter.Presenter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Attendee System</h1>
 * The AttendeeSystem program implements the system of Attendee user.
 */
public class AttendeeSystem {
    private final String attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    /**
     * Constructor of AttendeeSystem
     *
     * @param attendee A String, which is the username of attendee who is logged in.
     */
    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    /**
     * Run the Attendee System. Print out attendee's menu, and perform attendee's operations.
     */
    public void run() {
        initializeManagers(usermanager, eventmanager, messagemanager);
        String command;
        while (true) {
            Presenter.name(attendee);
            Presenter.userType("Attendee");
            Presenter.attendeeMenu();
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
                case "5":
                    cancelEnrollment();
                    continue;
                default:
                    Presenter.wrongKeyReminder();
                    Presenter.invalid("");
                    Presenter.continuePrompt();
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
        for (int i = 0; i < inbox.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Add all senders of the inbox messages to attendee's contact list.
     */
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(attendee);
        for (String sender : inboxSenders) {
            usermanager.addContactList(sender, attendee);
        }
        Presenter.autoAddToMessageList();
    }

    /**
     * Send messages to a specific person.
     */
    private void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        ArrayList<String> contactList = usermanager.getContactList(attendee);
        for (int i = 0; i < contactList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try {
            if (!("e".equals(receive))) {
                String receiver = contactList.get(Integer.parseInt(receive));
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(attendee, receiver, message);
                Presenter.success();
            } else {
                Presenter.exitingToMainMenu();
            }
        } catch (Exception e) {
            Presenter.invalid("");
        }
    }

    /**
     * Print the events that attendee hasn't signed up and choose one event to sign it up.
     */
    private void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(attendee);
        Presenter.inputPrompt("signUp");
        for (int i = 0; i < example_list.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("Attendee", attendee, Integer.parseInt(example_list.get(Integer.parseInt(command))));
            } catch (RoomIsFullException e) {
                Presenter.invalid("roomFull");
                return;
            } catch (InvalidUserException e) {
                Presenter.invalid("username");
                return;
            } catch (NoSuchEventException e) {
                Presenter.invalid("eventId");
                return;
            } catch (AlreadyHasSpeakerException e) {
                Presenter.invalid("addSpeaker");
                return;
            } catch (Exception e){
                Presenter.invalid("");
                return;
            }
            usermanager.addSignedEvent(command, attendee);
            Presenter.success();
        } else {
            Presenter.exitingToMainMenu();
        }
    }

    /**
     * Check the events that attendee has signed up.
     */
    private void checkSignedUp() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
        for (String s : eventsList) {
            System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Cancel the enrollment in an event that attendee has signed it up
     */
    private void cancelEnrollment() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
        for (int i = 0; i < eventsList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(eventsList.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        Presenter.inputPrompt("eventId");
        String eventId = reader.nextLine();
        if (!("e".equals(eventId))) {
            try {
                usermanager.deleteSignedEvent(eventId, attendee);
                eventmanager.signOut(eventId, attendee);
                Presenter.success();
            } catch (Exception e){
                Presenter.invalid("eventId");
            }
        } else {
            Presenter.exitingToMainMenu();
        }
    }

    private void initializeManagers(UserManager userManager, EventManager eventManager, MessageManager messageManager){
        initializeUserManager(userManager);
        initializeEventManager(eventManager);
        initializeMessageManager(messageManager);
    }

    private void initializeUserManager(UserManager userManager){
        Iterator userIterator = new UserIterator();
        Iterator eventIterator = new EventIterator();
        String[] temp;
        while (userIterator.hasNext()) {
            temp = userIterator.next();
            try {
                userManager.createUserAccount(temp[2], temp[0], temp[1]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        Iterator userIter = new UserIterator();
        while (userIter.hasNext()) {
            temp = userIter.next();
            for (int i = 3; i < temp.length; i++) {
                userManager.addContactList(temp[i], temp[0]);
            }
        }
        String[] temp2;
        int k = 0;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next();
            for (int j = 4; j < temp2.length; j++) {
                try {
                    userManager.addSignedEvent(String.valueOf(k), temp2[j]);
                } catch (Exception e) {
                    System.out.println("cannot add event (userManager). something went wrong.");
                }
            }
            k += 1;
        }
    }

    private void initializeEventManager(EventManager eventManager){
        int j;
        int k = 0;
        Iterator eventIterator = new EventIterator();
        Iterator roomIterator = new RoomIterator();
        UserManager usermanager = new UserManager();
        initializeUserManager(usermanager);
        String[] temp;
        System.out.println("loading existing events from file...");
        while (roomIterator.hasNext()) {
            temp = roomIterator.next();
            try {
                eventManager.addRoom(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            } catch (Exception e) {
                System.out.println("Failed to add room" + Integer.parseInt(temp[0]));
            }
        }
        String[] temp2;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next();
            try {
                eventManager.addEvent(temp2[0], Timestamp.valueOf(temp2[1]), Integer.parseInt(temp2[2]), temp2[3]);
            } catch (Exception e) {
                System.out.println("Failed to load event" + temp2[0] + "Invalid room number.");
            }
            for (j = 4; j < temp2.length; j++) {
                try {
                    eventManager.addUserToEvent(usermanager.getUserType(temp2[j]), temp2[j], k);
                } catch (Exception e) {
                    System.out.println("Failed to add User to Event, "+e.getMessage());
                }
            }
            k += 1;
        }
        System.out.println("\n Load complete. Welcome to the system. \n");
    }

    private void initializeMessageManager(MessageManager messageManager){
        int j;
        MessageIterator messageIterator = new MessageIterator();
        String[] temp;
        String temp_str;
        while (messageIterator.hasNext()) {
            temp = messageIterator.next();
            StringBuilder temp_strBuilder = new StringBuilder(temp[2]);
            for (j = 3; j < temp.length; j++) {
                temp_strBuilder.append(',').append(temp[j]);
            }
            temp_str = temp_strBuilder.toString();
            try {
                messageManager.sendMessage(temp[0], temp[1], temp_str);
            } catch (Exception e) {
                System.out.println("This shouldn't happen");
            }
        }
    }
}
