package system;

import event.*;
import presenter.Presenter;
import readWrite.*;
import user.DuplicateUserNameException;
import user.InvalidUsernameException;
import user.NoSuchUserException;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * <h1>Organizer System</h1>
 * The OrganizerSystem program implements the system of Organizer user.
 */
public class OrganizerSystem extends UserSystem{

    /**
     * Constructor for OrganizerSystem.
     *
     * @param myName A String, which is the username of organizer who logged in.
     */
    public OrganizerSystem(String myName) {
        super(myName);
    }

    /**
     * Run the Organizer System. Print out organizer's menu, and perform organizer's operations.
     */
    @Override
    public void run() {
        String command;
        while (true) {
            Presenter.name(myName);
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
                    usermanager.logout(myName);
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
     * Send messages to all users, either all speakers or all attendees.
     * @param user type of user, either the String "speaker" or "attendee".
     */
    private void sendMessageToAll(String user) {
        switch (user) {
            case "speaker":
                ArrayList<String> speakers = usermanager.getSpeakers();
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendToList(myName, speakers, message);
                Presenter.continuePrompt();
                break;
            case "attendee":
                ArrayList<String> attendees = usermanager.getAttendees();
                Presenter.inputPrompt("message");
                String message2 = reader.nextLine();
                messagemanager.sendToList(myName, attendees, message2);
                Presenter.continuePrompt();
                break;
        }

    }

    /*
     * Send message to a specific person.
     */
    @Override
    protected void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        Presenter.exitToMainMenuPrompt();
        String target = reader.nextLine();
        if ("e".equals(target)) {
            Presenter.exitingToMainMenu();
        } else {
            if (usermanager.getAllUsernames().contains(target)) {
                Presenter.inputPrompt("message");
                String msg = reader.nextLine();
                messagemanager.sendMessage(myName, target, msg);
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
        } catch (DuplicateRoomNumberException e) {
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
                } catch (DuplicateUserNameException e) {
                    Presenter.duplicateInvalid("username");
                    break;
                } catch (InvalidUsernameException e) {
                    Presenter.invalid("createUsername");
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
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            // ignored
        } catch (NoSuchUserException e) {
            Presenter.invalid("username");
        }
        try {
            eventmanager.becomeSpeaker(name);
            System.out.println("Successfully set " + name + " to be the speaker of the event.\n");
        } catch (AlreadyHasSpeakerException e) {
            System.out.println("addSpeaker");
        }
    }

    /*
     * Schedule a speaker to an existing event or to a new event.
     */
    private void scheduleSpeakers() {
        Presenter.inputPrompt("speakerName");
        String name = reader.nextLine();
        if (!usermanager.getUserType(name).equals("Speaker")) {
            Presenter.notASpeaker();
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

        } catch (RoomIsFullException e) {
                Presenter.invalid("addSpeaker");
                Presenter.defaultPrint("Room is full.");
            } catch (AlreadyHasSpeakerException e) {
                Presenter.invalid("addSpeaker");
                Presenter.defaultPrint("Already has speaker.");
            } catch (NoSpeakerException e) {
                Presenter.invalid("addSpeaker");
                Presenter.defaultPrint("not a speaker.");
            } catch (NoSuchEventException e) {
                Presenter.invalid("addSpeaker");
                Presenter.defaultPrint("event not found");
            } catch (InvalidUserException e) {
                Presenter.invalid("username");
            }
        }
        else {
            Presenter.invalid("eventId");}
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
        } catch (NotInOfficeHourException e) {
                Presenter.failureAddEvent("NotOfficeHour", room);
            } catch (InvalidActivityException e) {
            Presenter.failureAddEvent("InvalidRoomNum", room);
        } catch (TimeNotAvailableException e) {
            Presenter.failureAddEvent("TimeNotAvailable", room);
        }
        catch (Exception e){
            Presenter.invalid("addEventGeneral"); // Should not be called
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

}
