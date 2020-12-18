package system;

import event.exceptions.EventIsFullException;
import event.exceptions.InvalidUserException;
import event.exceptions.NoSuchEventException;
import event.exceptions.TooManySpeakerException;
import presenter.AttendeePresenter;

import javax.activity.InvalidActivityException;
import java.util.ArrayList;

/**
 * The AttendeeSystem program implements the text interface of Attendee user. Extends UserSystem.
 */
public class AttendeeSystem extends UserSystem {

    private final AttendeePresenter presenter;

    /**
     * Constructor of AttendeeSystem
     *
     * @param myName A String, which is the username of attendee who is logged in.
     */
    public AttendeeSystem(String myName) {
        super(myName);
        this.presenter = new AttendeePresenter();
    }

    /**
     * Run the Attendee System. Print out attendee's menu, and perform attendee's operations.
     */
    @Override
    public void run() {
        String command;
        while (conference != null) {
            presenter.name(myName);
            presenter.userType("Attendee");
            presenter.attendeeMenu();
            command = reader.nextLine();
            switch (command) {
                case "e":
                    usermanager.logout(myName);
                    break;
                case "1":
                    SignUpForEvent();
                    continue;
                case "2":
                    checkSignedUp();
                    continue;
                case "3":
                    cancelEnrollment();
                    continue;
                case "4":
                    messageSystem.sendMessageToSomeone();
                    continue;
                case "5":
                    messageSystem.seeMessages();
                    continue;
                case "6":
                    requestSystem.makeNewRequest();
                    continue;
                case "7":
                    requestSystem.seeMyRequests();
                    continue;
                case "8":
                    requestSystem.deleteRequests();
                    continue;
                case "9":
                    messageSystem.markUnreadMessages();
                    continue;
                case "10":
                    messageSystem.deleteMessage();
                    continue;
                case "11":
                    messageSystem.archiveMessage();
                    continue;
                case "12":
                    messageSystem.unArchiveMessage();
                    continue;
                case "13":
                    messageSystem.seeArchivedMessage();
                    continue;
                case "save":
                    save();
                    continue;
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
     * User signs up for an event within the given list of events.
     *
     * @param example_list the list of event in String, that the user can sign-up for.
     */
    protected void SignUp(ArrayList<String> example_list) {
        presenter.inputPrompt("signUp");
        presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
        for (int i = 0; i < example_list.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("Attendee", myName, Integer.parseInt(example_list.get(Integer.parseInt(command))));
            } catch (InvalidUserException | NoSuchEventException | TooManySpeakerException | EventIsFullException e) {
                presenter.printErrorMessage(e);
                return;
            } catch (Exception e) {
                presenter.invalid(""); // Should never be called
                return;
            }
            usermanager.addSignedEvent(example_list.get(Integer.parseInt(command)), myName);
            presenter.success();
            presenter.continuePrompt();
            reader.nextLine();
        } else {
            presenter.exitingToMainMenu();
        }
    }

    /**
     * Print the events that attendee hasn't signed up and choose one event to sign it up.
     */
    protected void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName, conference);
        this.SignUp(example_list);
    }

    private void checkSignedUp() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        if (eventsList.size() == 0) {
            presenter.noEvent();
        } else {
            for (String s : eventsList) {
                presenter.defaultPrint(eventmanager.findEventStr(Integer.valueOf(s)));
            }
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    private void cancelEnrollment() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        if (eventsList.size() == 0) {
            presenter.noEvent();
        } else {
            for (int i = 0; i < eventsList.size(); i++) {
                presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(eventsList.get(i))));
            }
            presenter.exitToMainMenuPrompt();
            presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
            String number = reader.nextLine();
            if (!("e".equals(number))) {
                try {
                    eventmanager.signOut(eventsList.get(Integer.parseInt(number)), myName);
                    usermanager.deleteSignedEvent(eventsList.get(Integer.parseInt(number)), myName);
                    presenter.success();
                    presenter.continuePrompt();
                    reader.nextLine();
                } catch (InvalidActivityException | NoSuchEventException e) {
                    presenter.printErrorMessage(e); // This should never happen.
                } catch (Exception e) {
                    presenter.invalid("");
                }
            } else {
                presenter.exitingToMainMenu();
            }
        }
    }
}


