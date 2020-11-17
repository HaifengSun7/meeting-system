package system;

import event.*;
import message.MessageManager;
import presenter.Presenter;
import readWrite.*;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Organizer System</h1>
 * The OrganizerSystem program implements the system of Organizer user.
 */
public class OrganizerSystem {

    private final String organizer;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    /**
     * Constructor for OrganizerSystem.
     *
     * @param organizer A String, which is the username of organizer who logged in.
     */
    public OrganizerSystem(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Run the Organizer System. Print out organizer's menu, and perform organizer's operations.
     */
    public void run() {
        initializeManagers(usermanager, eventmanager,messagemanager);
        String command;
        while (true) {
            Presenter.name(organizer);
            Presenter.userType("Organizer");
            Presenter.organizerMenu();
            command = reader.nextLine();
            switch (command) {
                case "1":
                    manageRooms();
                    continue;
                case "2":
                    createSpeaker();
                    continue;
                case "3":
                    scheduleSpeakers();
                    continue;
                case "4":
                    sendMessageToSomeone();
                    continue;
                case "5":
                    sendMessageToAll("speaker");
                    continue;
                case "6":
                    sendMessageToAll("attendee");
                    continue;
                case "7":
                    seeMessages();
                    continue;
                case "e":
                    usermanager.logout(organizer);
                    break;
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

    /*
     * See the messages that the organizer got from other users.
     */
    private void seeMessages() {
        ArrayList<String> inbox = messagemanager.getInbox(organizer);
        for (int i = 0; i < inbox.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + inbox.get(i) + "\n");
        }
        Presenter.exitToMainMenuPrompt();
        reader.nextLine();
    }

    /*
     * Send messages to all users, either all speakers or all attendees.
     * @param user type of user, either the String "speaker" or "attendee".
     */
    private void sendMessageToAll(String user) {
        switch (user) {
            case "speaker":
                ArrayList<String> speakers = usermanager.getSpeakers();
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendToList(organizer, speakers, message);
                Presenter.continuePrompt();
                break;
            case "attendee":
                ArrayList<String> attendees = usermanager.getAttendees();
                Presenter.inputPrompt("message");
                String message2 = reader.nextLine();
                messagemanager.sendToList(organizer, attendees, message2);
                Presenter.continuePrompt();
                break;
        }

    }

    /*
     * Send message to a specific person.
     */
    private void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        Presenter.exitToMainMenuPrompt();
        String target = reader.nextLine();
        if ("e".equals(target)) {
            Presenter.exitingToMainMenu();
        } else {
            if (usermanager.getAllUsernames().contains(target)) {
                Presenter.inputPrompt("message");
                String msg = reader.nextLine();
                messagemanager.sendMessage(organizer, target, msg);
                Presenter.success();
            } else {
                Presenter.invalid("username");
            }
        }
    }

    /*
     * Manage the rooms by creating a new room, or checking the existing rooms, or creating a new event
     * in a specific room.
     */
    private void manageRooms() {
        Presenter.titlesInSpeaker("manageRooms");
        ArrayList<String> roomList = eventmanager.getAllRooms();
        for (String s : roomList) {
            Presenter.defaultPrint(s);
        }
        Presenter.menusInSpeaker("manageRooms");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                addNewRoom();
                Presenter.continuePrompt();
                reader.nextLine();
                break;
            case "b":
                checkRoom();
                reader.nextLine();
                break;
            case "c":
                addingEvent();
                reader.nextLine();
                break;
            case "e":
                break;
            default:
                Presenter.wrongKeyReminder();
                Presenter.continuePrompt();
                reader.nextLine();
                break;
        }
    }

    /*
     * Create a new room.
     */
    private void addNewRoom() {
        Presenter.inputPrompt("newRoomNumber");
        String roomNumber = reader.nextLine();
        Presenter.inputPrompt("roomSize");
        String size = reader.nextLine();
        try {
            eventmanager.addRoom(Integer.parseInt(roomNumber), Integer.parseInt(size));
            Presenter.success();
        } catch (DuplicateRoomNoException e) {
            Presenter.duplicateInvalid("newRoom");
        }
    }

    /*
     * Check all scheduled events in a specific room.
     */
    private void checkRoom() {
        Presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer i : schedule) {
                Presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            Presenter.invalid("roomNumber");
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        Presenter.titlesInSpeaker("checkRoom");
        Presenter.continuePrompt();
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Create a new user to be the speaker.
     */
    private void createSpeaker() {
        Presenter.menusInSpeaker("createSpeaker");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                promoteExistingSpeaker();
                break;
            case "b":
                Presenter.inputPrompt("newUsername");
                String username = reader.nextLine();
                Presenter.inputPrompt("password");
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("Speaker", username, password);
                    Presenter.success();
                } catch (Exception e) {
                    Presenter.duplicateInvalid("username");
                    break;
                }
                break;
        }
    }

    /*
     * Promote a user to be a speaker.
     */
    private void promoteExistingSpeaker() {
        Presenter.titlesInSpeaker("promoteExistingSpeaker");
        String name = reader.nextLine();
        try {
            usermanager.becomeSpeaker(name);
        } catch (Exception e) {
            Presenter.invalid("username");
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        try {
            eventmanager.becomeSpeaker(name);
            System.out.println("Successfully set " + name + " to be the speaker of the event.\n");
        } catch (Exception e) {
            System.out.println("This event already has a speaker");
        }
    }

    /*
     * Schedule a speaker to an existing event or to a new event.
     */
    private void scheduleSpeakers() {
        Presenter.inputPrompt("speakerName");
        String name = reader.nextLine();
        try {
            if (!usermanager.getUserType(name).equals("Speaker")) {
                Presenter.notASpeaker();
                return;
            }
        } catch (Exception e) {
            Presenter.invalid("username");
            return;
        }
        Presenter.titlesInSpeaker("scheduleSpeakers1");
        ArrayList<String> allEvents = eventmanager.getAllEvents();
        for (int i = 0; i < allEvents.size(); i++) {
            Presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
        }
        Presenter.menusInSpeaker("scheduleSpeakers1");
        Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        switch (command) {
            default:
                addSpeakerToEvent(allEvents, name, command);
                break;
            case "r":
                Presenter.titlesInSpeaker("scheduleSpeakers2");
                ArrayList<String> roomLst = eventmanager.getAllRooms();
                for (String s : roomLst) {
                    Presenter.defaultPrint(s);
                }
                Presenter.menusInSpeaker("scheduleSpeakers2");
                Presenter.exitToMainMenuPrompt();
                String command4 = reader.nextLine();
                switch (command4) {
                    case "a":
                        addingEvent();
                        Presenter.continuePrompt();
                        reader.nextLine();
                        break;
                    case "e":
                        Presenter.exitingToMainMenu();
                        break;
                    default:
                        showEvents(command4);
                        addingEvent();
                        Presenter.continuePrompt();
                        reader.nextLine();
                        break;
                }
                break;
            case "e":
                Presenter.exitingToMainMenu();
                break;
        }
    }

    /*
     * Add a new speaker to an existing event.
     *
     * @param allEvents all existing events.
     * @param name      the name of user who would be a speaker.
     * @param command   command that organizer choose.
     */
    private void addSpeakerToEvent(ArrayList<String> allEvents, String name, String command) {
        if (0 <= Integer.parseInt(command) && Integer.parseInt(command) < allEvents.size()) {
            try {
                eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command));
                Presenter.success();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Presenter.invalid("addSpeaker");
            }
        } else {
            Presenter.invalid("eventId");
        }
    }

    /*
    The action of adding an Event, with info from inputs.
     */
    private void addingEvent(){
        Presenter.titlesInSpeaker("AddEvents");
        Presenter.inputPrompt("roomNumber");
        String room = reader.nextLine();
        Presenter.inputPrompt("startTime");
        String time1 = reader.nextLine();
        Presenter.inputPrompt("duration");
        String duration = reader.nextLine();
        Presenter.inputPrompt("description");
        String description = reader.nextLine();
        try {
            Presenter.loadEvent(room, time1, duration);
            eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration), description);
            Presenter.success();
            Presenter.continuePrompt();
        } catch (Exception e) {
            if(e instanceof NotInOfficeHourException){
                Presenter.failureAddEvent("NotOfficeHour", room);
            } else if(e instanceof TimeNotAvailableException){
                Presenter.failureAddEvent("TimeNotAvailable", room);
            } else if(e instanceof InvalidActivityException){
                Presenter.failureAddEvent("InvalidRoomNum", room);
            } else {
                Presenter.invalid("addEventGeneral");
            }
        }
    }

    private void showEvents(String command){
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(command));
            for (Integer i : schedule) {
                Presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            Presenter.invalid("getEventSchedule");
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
