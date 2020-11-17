package system;

import event.*;
import readWrite.*;
import presenter.Presenter;
import java.util.ArrayList;

/**
 * <h1>Attendee System</h1>
 * The AttendeeSystem program implements the system of Attendee user.
 */
public class AttendeeSystem extends UserSystem {

    /**
     * Constructor of AttendeeSystem
     *
     * @param myName A String, which is the username of attendee who is logged in.
     */
    public AttendeeSystem(String myName) {
        super(myName);
    }

    /**
     * Run the Attendee System. Print out attendee's menu, and perform attendee's operations.
     */
    @Override
    public void run() {
        String command;
        while (true) {
            Presenter.name(myName);
            Presenter.userType("Attendee");
            Presenter.attendeeMenu();
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
     * Print the events that attendee hasn't signed up and choose one event to sign it up.
     */
    private void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName);
        Presenter.inputPrompt("signUp");
        for (int i = 0; i < example_list.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("Attendee", myName, Integer.parseInt(example_list.get(Integer.parseInt(command))));
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
            usermanager.addSignedEvent(command, myName);
            Presenter.success();
        } else {
            Presenter.exitingToMainMenu();
        }
    }

    /**
     * Check the events that attendee has signed up.
     */
    private void checkSignedUp() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
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
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        for (int i = 0; i < eventsList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(eventsList.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        Presenter.inputPrompt("eventId");
        String eventId = reader.nextLine();
        if (!("e".equals(eventId))) {
            try {
                usermanager.deleteSignedEvent(eventId, myName);
                eventmanager.signOut(eventId, myName);
                Presenter.success();
            } catch (Exception e){
                Presenter.invalid("eventId");
            }
        } else {
            Presenter.exitingToMainMenu();
        }
    }

}
