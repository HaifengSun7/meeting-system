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


    private final OrganizerPresenter presenter;


    /**
     * Constructor for OrganizerSystem.
     *
     * @param myName A String, which is the username of organizer who logged in.
     */
    public OrganizerSystem(String myName) {
        super(myName);
        this.presenter = new OrganizerPresenter();
    }

    /**
     * Run the Organizer System. Print out organizer's menu, and perform organizer's operations.
     */
    @Override
    public void run() {
        String command;
        while (conference != null) {
            presenter.name(myName);
            presenter.userType("Organizer");
            presenter.organizerMenu();
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
                    presenter.wrongKeyReminder();
                    presenter.invalid("");
                    presenter.continuePrompt();
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
    This is a huge helper private method in order to print requests to user on the screen and let them choose.
     @param allRequests this is a arraylist of list of strings which include title, content of requests.
     @param presenterString cause this method may print our different requests depends on different input,
     so we need to input the message we are going to tell presenter.
     */
    private void seeRequests(ArrayList<String[]> allRequests, String presenterString) { // Huge helper function
        if (allRequests.size() == 0) {
            presenter.inputPrompt("NoRequests");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
        } else {
            presenter.submenusInOrganizer(presenterString);
            printRequests(allRequests); // Here are all requests printed on the screen!
            presenter.inputPrompt("readRequest");
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
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                } catch (NumberFormatException e) {
                    if ("e".equals(command)) {
                        validInput = true;
                    } else {
                        presenter.invalid("");
                        presenter.inputPrompt("readRequest");
                        command = reader.nextLine();
                    }
                }
            }
            if (!validNumber) {
                presenter.exitingToMainMenu();
            } else {
                presenter.defaultPrint(allRequests.get(input)[1]);
                changeRequestStatus(allRequests.get(input)[0]); // Include confirm of status change
            }
//                presenter.inputPrompt("anythingToGoBack");
//                reader.nextLine();
        }
    }

    /**
     Show all requests in the system to organizer.
     */
    private void seeAllRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllRequests();
        seeRequests(allRequests, "SeeAllRequestsInSystemIntroduction");
    }

    /**
     Show all unsloved/pending requests in the system to organizer.
     */
    private void seeUnsolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllUnsolvedRequests();
        seeRequests(allRequests, "SeeAllPendingRequestsInSystemIntroduction");
    }

    /**
     Show all solved/addressed requests in the system to organizer.
     */
    private void seeSolvedRequest() {
        ArrayList<String[]> allRequests = requestmanager.getAllSolvedRequests();
        seeRequests(allRequests, "SeeAllAddressedRequestsInSystemIntroduction");
    }

    /**
     change the status of a request.
     @param title the title of a specific request.
     */
    private void changeRequestStatus(String title) {
        boolean requestSolved = requestmanager.getRequestStatus(title);
        if (requestSolved){
            presenter.submenusInOrganizer("ChangeStatusAtoP");
        } else {
            presenter.submenusInOrganizer("ChangeStatusPtoA");
        }
        String confirm = reader.nextLine();
        if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
            requestmanager.changeStatus(title);
            presenter.submenusInOrganizer("ChangeStatusSuccess");
            presenter.inputPrompt("anythingToGoBack");
            reader.nextLine();
            presenter.exitingToMainMenu();
        }
    }

    private void promoteVIPEvent() {
        try {
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (String allEvent : allEvents) {
                presenter.defaultPrint(allEvent);
            }
            presenter.inputPrompt("promote event");
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
                presenter.success();
            } catch (NoSuchEventException | NoSuchUserException | NotAttendeeException | InvalidActivityException e) {
                presenter.printErrorMessage(e);
                presenter.continuePrompt();
                reader.nextLine();
            } catch (Exception e) {
                presenter.invalid("");
                presenter.continuePrompt();
                reader.nextLine();
            }
        } catch (NoSuchConferenceException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
        }
    }

    /*
     * Manage the rooms by creating a new room, or checking the existing rooms, or creating a new event
     * in a specific room.
     */
    private void manageRooms() {
        presenter.titlesInSpeaker("manageRooms");
        ArrayList<String> roomList = eventmanager.getAllRooms();
        for (String s : roomList) {
            presenter.defaultPrint(s);
        }
        presenter.submenusInOrganizer("manageRooms");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                addNewRoom();
                presenter.continuePrompt();
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
            case "f":
                cancelEvent();
                reader.nextLine();
                break;
            case "d":
                changeEventMaxNumberPeople();
                reader.nextLine();
                break;
            case "g":
                checkAllEvent();
                reader.nextLine();
                break;
            case "e":
                break;
            default:
                presenter.wrongKeyReminder();
                presenter.continuePrompt();
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
        presenter.inputPrompt("message");
        String message = reader.nextLine();
        messagemanager.sendToList(myName, receivers, message);
        presenter.continuePrompt();

    }

    /*
     * Check all the events.
     */
    private void checkAllEvent() {
        try {
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
            System.out.println("That's all.");
        } catch (NoSuchConferenceException e) {
            presenter.printErrorMessage(e);
        }
        presenter.continuePrompt();
    }

    /*
     * Cancel an event.
     */
    private void cancelEvent() {
        presenter.inputPrompt("enterEventIdToCancelEvent");
        String eventId = reader.nextLine();
        try {
            ArrayList<String> userList = new ArrayList<>(eventmanager.getAttendees(eventId));
            for (String username: userList) {
                usermanager.deleteSignedEvent(eventId, username);
            }
            eventmanager.cancelEvent(eventId, conference);
            presenter.success();
        } catch (NoSuchEventException | InvalidActivityException | NoSuchConferenceException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
        }
    }

    /*
     * set the maximum number of people in the selected event.
     */
    private void changeEventMaxNumberPeople() {
        presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer integer : schedule) {
                presenter.defaultPrint("[" + integer + "] " + eventmanager.findEventStr(integer));
            }
            presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
            presenter.exitToMainMenuPrompt();
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
            return;
        } catch (NumberFormatException e) {
            presenter.invalid("");
            presenter.continuePrompt();
            return;
        }
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
            presenter.continuePrompt();
        } else {
            try {
                presenter.inputPrompt("newMaxPeopleOfEvent");
                String newMax = reader.nextLine();
                eventmanager.setMaximumPeople(Integer.parseInt(roomNumber), Integer.parseInt(newMax),
                        Integer.parseInt(command));
                presenter.success();
                presenter.continuePrompt();
            } catch (NoSuchEventException | InvalidNewMaxNumberException | InvalidActivityException e) {
                presenter.printErrorMessage(e);
                presenter.continuePrompt();
            }
        }
    }

    /*
     * Create a new room.
     */
    private void addNewRoom() {
        presenter.inputPrompt("newRoomNumber");
        String roomNumber = reader.nextLine();
        presenter.inputPrompt("roomSize");
        String size = reader.nextLine();
        try {
            eventmanager.addRoom(Integer.parseInt(roomNumber), Integer.parseInt(size));
            presenter.success();
        } catch (DuplicateRoomNumberException | WrongRoomSizeException e) {
            presenter.printErrorMessage(e);
        }
        catch (Exception e) {
            presenter.invalid("");
        }
    }

    /*
     * Check all scheduled events in a specific room.
     */
    private void checkRoom() {
        presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer i : schedule) {
                presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
            return;
        } catch (Exception e){
            e.printStackTrace();
            presenter.defaultPrint("Wrong input.");
            presenter.continuePrompt();
            return;
        }
        presenter.titlesInSpeaker("checkRoom");
        presenter.continuePrompt();
    }

    /*
     * Create a new user to be the speaker.
     */
    private void createSpeaker() {
        presenter.submenusInOrganizer("createSpeaker");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                promoteExistingSpeaker();
                break;
            case "b":
                presenter.inputPrompt("newUsername");
                String username = reader.nextLine();
                presenter.inputPrompt("password");
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("Speaker", username, password);
                    presenter.success();
                } catch (DuplicateUserNameException e) {
                    presenter.printErrorMessage(e);
                    break;
                } catch (InvalidUsernameException e) {
                    presenter.printErrorMessage(e);
                }
                break;
        }
    }

    /*
     * Promote a user to be a speaker.
     */
    private void promoteExistingSpeaker() {
        presenter.titlesInSpeaker("promoteExistingSpeaker");
        String name = reader.nextLine();
        try {
            usermanager.becomeSpeaker(name);
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            // ignored
        } catch (NoSuchUserException e) {
            presenter.printErrorMessage(e);
        }
        try {
            eventmanager.becomeSpeaker(name);
            System.out.println("Successfully set " + name + " to be the speaker of the event.\n");
        } catch (TooManySpeakerException e) {
            presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new user to be the VIP attendee.
     */
    private void createVIP() {
        presenter.submenusInOrganizer("createVIP");
        String command = reader.nextLine();
        switch (command) {
            case "a":
                promoteExistingAttendee();
                break;
            case "b":
                presenter.inputPrompt("newUsername");
                String username = reader.nextLine();
                presenter.inputPrompt("password");
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("VIP", username, password);
                    presenter.success();
                } catch (DuplicateUserNameException e) {
                    presenter.printErrorMessage(e);
                    break;
                } catch (InvalidUsernameException e) {
                    presenter.printErrorMessage(e);
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
                    presenter.defaultPrint(attendee);
                }
            }
        } catch (NotAttendeeException | NoSuchUserException e) {
            presenter.printErrorMessage(e);
        }
        presenter.submenusInOrganizer("promoteExistingAttendee");
        String name = reader.nextLine();
        try {
            usermanager.becomeVIP(name);
            System.out.println("Successfully set " + name + " to be a VIP attendee.\n");
        } catch (NoSuchUserException e) {
            presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new attendee account.
     */
    private void createAttendee() {
        presenter.inputPrompt("newUsername");
        String username = reader.nextLine();
        presenter.inputPrompt("password");
        String password = reader.nextLine();
        try {
            usermanager.createUserAccount("Attendee", username, password);
            presenter.success();
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            presenter.printErrorMessage(e);
        }
    }

    /*
     * Create a new Organizer account.
     */
    private void createOrganizer() {
        presenter.inputPrompt("newUsername");
        String username = reader.nextLine();
        presenter.inputPrompt("password");
        String password = reader.nextLine();
        try {
            usermanager.createUserAccount("Organizer", username, password);
            presenter.success();
        } catch (DuplicateUserNameException | InvalidUsernameException e) {
            presenter.printErrorMessage(e);
        }
    }

    /*
     * Schedule a speaker to an existing event or to a new event.
     */
    private void scheduleSpeakers() {
        try{
            presenter.inputPrompt("speakerName");
            String name = reader.nextLine();
            if (!usermanager.getUserType(name).equals("Speaker")) {
                presenter.notASpeaker();
                return;
            }
            presenter.titlesInSpeaker("scheduleSpeakers1");
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
            presenter.submenusInOrganizer("scheduleSpeakers1");
            presenter.exitToMainMenuPrompt();
            String command = reader.nextLine();
            switch (command) {
                default:
                    addSpeakerToEvent(allEvents, name, command);
                    break;
                case "r":
                    presenter.titlesInSpeaker("scheduleSpeakers2");
                    ArrayList<String> roomLst = eventmanager.getAllRooms();
                    for (String s : roomLst) {
                        presenter.defaultPrint(s);
                    }
                    presenter.submenusInOrganizer("scheduleSpeakers2");
                    presenter.exitToMainMenuPrompt();
                    String command4 = reader.nextLine();
                    switch (command4) {
                        case "a":
                            addingEvent();
                            presenter.continuePrompt();
                            reader.nextLine();
                            break;
                        case "e":
                            presenter.exitingToMainMenu();
                            break;
                        default:
                            showEvents(command4);
                            addingEvent();
                            presenter.continuePrompt();
                            reader.nextLine();
                            break;
                    }
                    break;
                case "e":
                    presenter.exitingToMainMenu();
                    break;
            }
        } catch (Exception e){
            presenter.defaultPrint(e.getMessage());
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
                presenter.success();
//            } catch (RoomIsFullException e) {
//                presenter.printErrorMessage(e);
//                presenter.defaultPrint("Room is full.");
            } catch (NoSuchEventException e) {
                presenter.printErrorMessage(e);
                presenter.defaultPrint("event not found");
            } catch (EventIsFullException e) {
                presenter.defaultPrint("Event is full");
            } catch (InvalidUserException | TooManySpeakerException e) {
                presenter.printErrorMessage(e);
            }
        } else {
            presenter.invalid("eventId");
        }
    }

    /*
     * The action of adding an Event, with info from inputs.
     */
    private void addingEvent(){
        presenter.titlesInSpeaker("AddEvents");
        presenter.inputPrompt("roomNumber");
        String room = reader.nextLine();
        presenter.inputPrompt("startTime");
        String time1 = reader.nextLine();
        presenter.inputPrompt("duration");
        String duration = reader.nextLine();
        presenter.inputPrompt("description");
        String description = reader.nextLine();
        presenter.inputPrompt("eventType");
        String type = reader.nextLine();
        presenter.inputPrompt("vip");
        String vip = reader.nextLine();
        int maxSpeaker;
        int maxAttendee;
        try {
            switch (type){
                case ("Party"):
                    maxSpeaker = 0;
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case("Single"):
                    maxSpeaker = 1;
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case("Multi"):
                    presenter.inputPrompt("numSpeaker");
                    maxSpeaker = Integer.parseInt(reader.nextLine());
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                default:
                    throw new NoSuchTypeException("Incorrect Event Type");
            }
        } catch (Exception e){
            presenter.printErrorMessage(e);
            presenter.exitToMainMenuPrompt();
            return;
        }
        try {
            presenter.loadEvent(room, time1, duration);
            eventmanager.addEvent(room, maxSpeaker, maxAttendee, Timestamp.valueOf(time1), Integer.parseInt(duration), description, vip, conference);
            presenter.success();
            presenter.continuePrompt();
        } catch (NotInOfficeHourException | TimeNotAvailableException  | InvalidActivityException |
                RoomIsFullException e) {
            presenter.printErrorMessage(e);
            presenter.exitToMainMenuPrompt();
        } catch (Exception e) {
            presenter.invalid("addEventGeneral"); // Should not be called
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
                presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
        }
    }

}
