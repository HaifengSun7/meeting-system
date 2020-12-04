package system;

import event.exceptions.*;
import presenter.*;
import user.*;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * <h1>Organizer System</h1>
 * The OrganizerSystem program implements the system of Organizer user.
 */
public class OrganizerSystem extends UserSystem {

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
        while (conference != null) {
            Presenter.name(myName);
            Presenter.userType("Organizer");
            OrganizerPresenter.organizerMenu();
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
                case "8":
                    createVIP();
                    continue;
                case "9":
                    createAttendee();
                    continue;
                case "10":
                    createOrganizer();
                    continue;
                case "11":
                    seeAllRequest();
                    continue;
                case "12":
                    seeUnsolvedRequest();
                    continue;
                case "13":
                    seeSolvedRequest();
                    continue;
                case "14":
                    promoteVIPEvent();
                    continue;
                case "15":
                    markUnreadMessages();
                    continue;
                case "16":
                    deleteMessage();
                    continue;
                case "17":
                    archiveMessage();
                    continue;
                case "18":
                    unArchiveMessage();
                    continue;
                case "19":
                    seeArchivedMessage();
                    continue;
                case "save":
                    save();
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
        save();
    }

    /**
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

    private void seeRequests(ArrayList<String[]> allRequests, String presenterString) { // Huge helper function
        if (allRequests.size() == 0) {
            Presenter.inputPrompt("NoRequests");
            Presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            Presenter.exitingToMainMenu();
        } else {
            OrganizerPresenter.menusInOrganizer(presenterString);
            printRequests(allRequests); // Here are all requests printed on the screen!
            Presenter.inputPrompt("readRequest");
            String command = reader.nextLine();
            boolean validInput = false;
            boolean validNumber = false;
            int input = 0;
            while (!validInput) {
                try {
                    input = Integer.parseInt(command);
                    if ((0 <= input) && (input < allRequests.size())) {
                        validInput = true;
                        validNumber = true;
                    } else {
                        Presenter.invalid("");
                        Presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                } catch (NumberFormatException e) {
                    if ("e".equals(command)) {
                        validInput = true;
                    } else {
                        Presenter.invalid("");
                        Presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                }
            }
            if (!validNumber) {
                Presenter.exitingToMainMenu();
            } else {
                Presenter.defaultPrint(allRequests.get(input)[1]);
                changeRequestStatus(allRequests.get(input)[0]); // Include confirm of status change
            }
//                Presenter.inputPrompt("anythingToGoBack");
//                reader.nextLine();
        }
    }

    private void seeAllRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllRequests();
        seeRequests(allRequests, "SeeAllRequestsInSystemIntroduction");
    }

    private void seeUnsolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllUnsolvedRequests();
        seeRequests(allRequests, "SeeAllPendingRequestsInSystemIntroduction");
    }

    private void seeSolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllSolvedRequests();
        seeRequests(allRequests, "SeeAllAddressedRequestsInSystemIntroduction");
    }

    private void changeRequestStatus(String title) {
        boolean requestSolved = requestmanager.getRequestStatus(title);
        if (requestSolved){
            OrganizerPresenter.menusInOrganizer("ChangeStatusAtoP");
        } else {
            OrganizerPresenter.menusInOrganizer("ChangeStatusPtoA");
        }
        String confirm = reader.nextLine();
        if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
            requestmanager.changeStatus(title);
            OrganizerPresenter.menusInOrganizer("ChangeStatusSuccess");
            Presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            Presenter.exitingToMainMenu();
        }
    }

    private void promoteVIPEvent() {
        try {
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (String allEvent : allEvents) {
                Presenter.defaultPrint(allEvent);
            }
            Presenter.inputPrompt("promote event");
            String eventId = reader.nextLine();
            try {
                ArrayList<String> userList = new ArrayList<>(eventmanager.getAttendees(eventId));
                for (String username : userList) {
                    if (!usermanager.isVIP(username)) {
                        String rejectText = "Sorry, event ["+ eventId + "] has been promoted to a VIP event." +
                                "You have been automatically signed out from the event.";
                        usermanager.deleteSignedEvent(eventId, username);
                        eventmanager.signOut(eventId, username);
                        messagemanager.sendMessage(this.myName, username, rejectText);
                    }
                }
                eventmanager.switchVipEvent(eventId, true);
                Presenter.success();
            } catch (NoSuchEventException | NoSuchUserException | NotAttendeeException | InvalidActivityException e) {
                Presenter.printErrorMessage(e);
                Presenter.continuePrompt();
                reader.nextLine();
            }
        } catch (NoSuchConferenceException e) {
            Presenter.printErrorMessage(e);
            Presenter.continuePrompt();
            reader.nextLine();
        }
    }

    /*
     * Manage the rooms by creating a new room, or checking the existing rooms, or creating a new event
     * in a specific room.
     */
    private void manageRooms() {
        SpeakerPresenter.titlesInSpeaker("manageRooms");
        ArrayList<String> roomList = eventmanager.getAllRooms();
        for (String s : roomList) {
            Presenter.defaultPrint(s);
        }
        OrganizerPresenter.menusInOrganizer("manageRooms");
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
            case "c2":
                cancelEvent();
                reader.nextLine();
                break;
            case "d":
                changeEventMaxNumberPeople();
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
     * Send messages to all users, either all speakers or all attendees.
     * @param user type of user, either the String "speaker" or "attendee".
     */
    private void sendMessageToAll(String user) {
        ArrayList<String> receivers = new ArrayList<>();
        switch (user) {
            case "speaker":
                receivers = usermanager.getSpeakers();
                break;
            case "attendee":
                receivers = usermanager.getAttendees();
                break;
        }
        Presenter.inputPrompt("message");
        String message = reader.nextLine();
        messagemanager.sendToList(myName, receivers, message);
        Presenter.continuePrompt();

    }

    /*
     * Cancel an event.
     */
    private void cancelEvent() {
        Presenter.inputPrompt("enterEventIdToCancelEvent");
        String eventId = reader.nextLine();
        try {
            ArrayList<String> userList = new ArrayList<>(eventmanager.getAttendees(eventId));
            for (String username: userList) {
                usermanager.deleteSignedEvent(eventId, username);
            }
            eventmanager.cancelEvent(eventId);
            Presenter.success();
        } catch (NoSuchEventException | InvalidActivityException e) {
            Presenter.printErrorMessage(e);
            Presenter.continuePrompt();
            reader.nextLine();
        } catch (Exception e) {
            Presenter.invalid("");
        }
    }

    /*
     * set the maximum number of people in the selected event.
     */
    private void changeEventMaxNumberPeople() {
        Presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer integer : schedule) {
                Presenter.defaultPrint("[" + integer + "] " + eventmanager.findEventStr(integer));
            }
            Presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
            Presenter.exitToMainMenuPrompt();
        } catch (InvalidActivityException e) {
            Presenter.printErrorMessage(e);
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        } catch (NumberFormatException e) {
            Presenter.invalid("");
            Presenter.continuePrompt();
            return;
        }
        String command = reader.nextLine();
        if ("e".equals(command)) {
            Presenter.exitingToMainMenu();
            Presenter.continuePrompt();
        } else {
            try {
                Presenter.inputPrompt("newMaxPeopleOfEvent");
                String newMax = reader.nextLine();
                eventmanager.setMaximumPeople(Integer.parseInt(roomNumber), Integer.parseInt(newMax),
                        Integer.parseInt(command));
                Presenter.success();
                Presenter.continuePrompt();
            } catch (NoSuchEventException | InvalidNewMaxNumberException | InvalidActivityException e) {
                Presenter.printErrorMessage(e);
                Presenter.continuePrompt();
            }
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
            Presenter.printErrorMessage(e);
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
            Presenter.printErrorMessage(e);
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        } catch (Exception e){
            e.printStackTrace();
            Presenter.defaultPrint("Wrong input.");
            Presenter.continuePrompt();
            return;
        }
        SpeakerPresenter.titlesInSpeaker("checkRoom");
        Presenter.continuePrompt();
    }

    /*
     * Create a new user to be the speaker.
     */
    private void createSpeaker() {
        OrganizerPresenter.menusInOrganizer("createSpeaker");
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
                    Presenter.printErrorMessage(e);
                    break;
                } catch (InvalidUsernameException e) {
                    Presenter.printErrorMessage(e);
                }
                break;
        }
    }

    /*
     * Promote a user to be a speaker.
     */
    private void promoteExistingSpeaker() {
        SpeakerPresenter.titlesInSpeaker("promoteExistingSpeaker");
        String name = reader.nextLine();
        try {
            usermanager.becomeSpeaker(name);
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            // ignored
        } catch (NoSuchUserException e) {
            Presenter.printErrorMessage(e);
        }
        try {
            eventmanager.becomeSpeaker(name);
            System.out.println("Successfully set " + name + " to be the speaker of the event.\n");
        } catch (TooManySpeakerException e) {
            Presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new user to be the VIP attendee.
     */
    private void createVIP() {
        OrganizerPresenter.menusInOrganizer("createVIP");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                promoteExistingAttendee();
                break;
            case "b":
                Presenter.inputPrompt("newUsername");
                String username = reader.nextLine();
                Presenter.inputPrompt("password");
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("VIP", username, password);
                    Presenter.success();
                } catch (DuplicateUserNameException e) {
                    Presenter.printErrorMessage(e);
                    break;
                } catch (InvalidUsernameException e) {
                    Presenter.printErrorMessage(e);
                }
                break;
        }
    }

    /*
     * Promote a attendee to be a VIP.
     */
    private void promoteExistingAttendee() {
        ArrayList<String> allAttendee = usermanager.getAttendees();
        try {
            for (String attendee : allAttendee) {
                if (! usermanager.isVIP(attendee)) {
                    Presenter.defaultPrint(attendee);
                }
            }
        } catch (NotAttendeeException | NoSuchUserException e) {
            Presenter.printErrorMessage(e);
        }
        OrganizerPresenter.menusInOrganizer("promoteExistingAttendee");
        String name = reader.nextLine();
        try {
            usermanager.becomeVIP(name);
            System.out.println("Successfully set " + name + " to be a VIP attendee.\n");
        } catch (NoSuchUserException e) {
            Presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new attendee account.
     */
    private void createAttendee() {
        Presenter.inputPrompt("newUsername");
        String username = reader.nextLine();
        Presenter.inputPrompt("password");
        String password = reader.nextLine();
        try {
            usermanager.createUserAccount("Attendee", username, password);
            Presenter.success();
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            Presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new Organizer account.
     */
    private void createOrganizer() {
        Presenter.inputPrompt("newUsername");
        String username = reader.nextLine();
        Presenter.inputPrompt("password");
        String password = reader.nextLine();
        try {
            usermanager.createUserAccount("Organizer", username, password);
            Presenter.success();
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            Presenter.printErrorMessage(e);
        }
    }

    /*
     * Schedule a speaker to an existing event or to a new event.
     */
    private void scheduleSpeakers() {
        try{
            Presenter.inputPrompt("speakerName");
            String name = reader.nextLine();
            if (!usermanager.getUserType(name).equals("Speaker")) {
                Presenter.notASpeaker();
                return;
            }
            SpeakerPresenter.titlesInSpeaker("scheduleSpeakers1");
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                Presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
            OrganizerPresenter.menusInOrganizer("scheduleSpeakers1");
            Presenter.exitToMainMenuPrompt();
            String command = reader.nextLine();
            switch (command) {
                default:
                    addSpeakerToEvent(allEvents, name, command);
                    break;
                case "r":
                    SpeakerPresenter.titlesInSpeaker("scheduleSpeakers2");
                    ArrayList<String> roomLst = eventmanager.getAllRooms();
                    for (String s : roomLst) {
                        Presenter.defaultPrint(s);
                    }
                    OrganizerPresenter.menusInOrganizer("scheduleSpeakers2");
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
        } catch (Exception e){
            Presenter.defaultPrint(e.getMessage());
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
//            } catch (RoomIsFullException e) {
//                Presenter.printErrorMessage(e);
//                Presenter.defaultPrint("Room is full.");
            } catch (NoSuchEventException e) {
                Presenter.printErrorMessage(e);
                Presenter.defaultPrint("event not found");
            } catch (EventIsFullException e) {
                Presenter.defaultPrint("Event is full");
            } catch (InvalidUserException | TooManySpeakerException e) {
                Presenter.printErrorMessage(e);
            }
        } else {
            Presenter.invalid("eventId");
        }
    }

    /*
     * The action of adding an Event, with info from inputs.
     */
    private void addingEvent(){
        SpeakerPresenter.titlesInSpeaker("AddEvents");
        Presenter.inputPrompt("roomNumber");
        String room = reader.nextLine();
        Presenter.inputPrompt("startTime");
        String time1 = reader.nextLine();
        Presenter.inputPrompt("duration");
        String duration = reader.nextLine();
        Presenter.inputPrompt("description");
        String description = reader.nextLine();
        Presenter.inputPrompt("eventType");
        String type = reader.nextLine();
        Presenter.inputPrompt("vip");
        String vip = reader.nextLine();
        int maxSpeaker;
        int maxAttendee;
        try {
            switch (type){
                case ("Party"):
                    maxSpeaker = 0;
                    Presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case("Single"):
                    maxSpeaker = 1;
                    Presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case("Multi"):
                    Presenter.inputPrompt("numSpeaker");
                    maxSpeaker = Integer.parseInt(reader.nextLine());
                    Presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                default:
                    throw new NoSuchTypeException("Incorrect Event Type");
            }
        } catch (Exception e){
            Presenter.printErrorMessage(e);
            Presenter.exitToMainMenuPrompt();
            return;
        }
        try {
            Presenter.loadEvent(room, time1, duration);
            eventmanager.addEvent(room, maxSpeaker, maxAttendee, Timestamp.valueOf(time1), Integer.parseInt(duration), description, vip, conference);
            Presenter.success();
            Presenter.continuePrompt();
        } catch (NotInOfficeHourException | TimeNotAvailableException | InvalidActivityException |
                RoomIsFullException e) {
            Presenter.printErrorMessage(e);
            Presenter.exitToMainMenuPrompt();
        } catch (Exception e) {
            Presenter.invalid("addEventGeneral"); // Should not be called
        }
    }

    /*
     * show the events in a selected room
     * @param command the room number
     */
    private void showEvents(String command) {
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(command));
            for (Integer i : schedule) {
                Presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            Presenter.printErrorMessage(e);
        }
    }

}
