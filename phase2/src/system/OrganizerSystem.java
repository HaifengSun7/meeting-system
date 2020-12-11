package system;

import event.exceptions.*;
import presenter.OrganizerPresenter;
import user.DuplicateUserNameException;
import user.InvalidUsernameException;
import user.NoSuchUserException;
import user.NotAttendeeException;

import javax.activity.InvalidActivityException;
import java.util.ArrayList;

/**
 * The OrganizerSystem program implements the text interface of Organizer user. Extends from UserSystem
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
                    messageSystem.sendMessageFromOrganizerToSomeone();
                    continue;
                case "5":
                    messageSystem.sendMessageToAll("speaker");
                    continue;
                case "6":
                    messageSystem.sendMessageToAll("attendee");
                    continue;
                case "7":
                    messageSystem.seeMessages();
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
                    requestSystem.seeAllRequest();
                    continue;
                case "12":
                    requestSystem.seeUnsolvedRequest();
                    continue;
                case "13":
                    requestSystem.seeSolvedRequest();
                    continue;
                case "14":
                    promoteVIPEvent();
                    continue;
                case "15":
                    messageSystem.markUnreadMessages();
                    continue;
                case "16":
                    messageSystem.deleteMessage();
                    continue;
                case "17":
                    messageSystem.archiveMessage();
                    continue;
                case "18":
                    messageSystem.unArchiveMessage();
                    continue;
                case "19":
                    messageSystem.seeArchivedMessage();
                    continue;
                case "20":
                    createConference();
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

    private void createConference() {
        presenter.submenusInOrganizer("CreateConference");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                ArrayList<String> conferences = eventmanager.getAllConference();
                while(conferences.contains(command)){
                    presenter.defaultPrint("Conference exists, please re-enter a name.");
                    command = reader.nextLine();
                }
                presenter.submenusInOrganizer("ConferencePrompt");
                eventSystem.addingEvent(command);
            } catch (Exception e){
                presenter.printErrorMessage(e);
            }
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
                        String rejectText = "Sorry, event [" + eventId + "] has been promoted to a VIP event." +
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
                eventSystem.addNewRoom();
                presenter.continuePrompt();
                reader.nextLine();
                break;
            case "b":
                eventSystem.checkRoom();
                presenter.titlesInSpeaker("checkRoom");
                presenter.continuePrompt();
                reader.nextLine();
                break;
            case "c":
                presenter.titlesInSpeaker("AddEvents");
                eventSystem.addingEvent(conference);
                reader.nextLine();
                break;
            case "f":
                presenter.inputPrompt("enterEventIdToCancelEvent");
                eventSystem.cancelEvent();
                reader.nextLine();
                break;
            case "d":
                eventSystem.changeEventMaxNumberPeople();
                reader.nextLine();
                break;
            case "g":
                eventSystem.checkAllEvent();
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

    private void promoteExistingAttendee() {
        ArrayList<String> allAttendee = usermanager.getAttendees();
        try {
            for (String attendee : allAttendee) {
                if (!usermanager.isVIP(attendee)) {
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

    private void scheduleSpeakers() {
        try {
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
                    try{
                        addSpeakerToEvent(name, allEvents.get(Integer.parseInt(command)).substring(11,13));
                    } catch (Exception e){
                        presenter.defaultPrint("Please enter a valid number.");
                    }
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
                            presenter.titlesInSpeaker("AddEvents");
                            eventSystem.addingEvent(conference);
                            presenter.continuePrompt();
                            reader.nextLine();
                            break;
                        case "e":
                            presenter.exitingToMainMenu();
                            break;
                        default:
                            eventSystem.showEvents(command4);
                            presenter.continuePrompt();
                            reader.nextLine();
                            break;
                    }
                    break;
                case "e":
                    presenter.exitingToMainMenu();
                    break;
            }
        } catch (Exception e) {
            presenter.defaultPrint(e.getMessage());
        }
    }

    private void addSpeakerToEvent(String name, String command) {
        if (0 <= Integer.parseInt(command)) {
            try {
                eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command));
                presenter.success();
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
}