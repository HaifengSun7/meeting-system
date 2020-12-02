package system;

import event.exceptions.*;
import presenter.*;
import readWrite.Write;
import request.NoSuchRequestException;

import javax.activity.InvalidActivityException;
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
        while (conference != null) {
            Presenter.name(myName);
            Presenter.userType("Attendee");
            AttendeePresenter.attendeeMenu();
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
                    sendMessageToSomeone();
                    continue;
                case "5":
                    seeMessages();
                    continue;
                case "6":
                    makeNewRequest();
                    continue;
                case "7":
                    seeMyRequests();
                    continue;
                case "8":
                    deleteRequests();
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

    /*
     * Print the events that attendee hasn't signed up and choose one event to sign it up.
     */
    protected void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName);
        Presenter.inputPrompt("signUp");
        Presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");

        for (int i = 0; i < example_list.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("Attendee", myName, Integer.parseInt(example_list.get(Integer.parseInt(command))));
            } catch (InvalidUserException | NoSuchEventException | TooManySpeakerException | EventIsFullException e) {
                Presenter.printErrorMessage(e);
                return;
            } catch (Exception e) {
                Presenter.invalid(""); // Should never be called
                return;
            }
            usermanager.addSignedEvent(example_list.get(Integer.parseInt(command)), myName);
            Presenter.success();
        } else {
            Presenter.exitingToMainMenu();
        }
    }

    /*
     * Check the events that attendee has signed up.
     */
    private void checkSignedUp() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        for (String s : eventsList) {
            Presenter.defaultPrint(eventmanager.findEventStr(Integer.valueOf(s)));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Cancel the enrollment in an event that attendee has signed it up
     */
    private void cancelEnrollment() {
        ArrayList<String> eventsList = usermanager.getSignedEventList(myName);
        for (int i = 0; i < eventsList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(eventsList.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        Presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
        String number = reader.nextLine();
        if (!("e".equals(number))) {
            try {
                eventmanager.signOut(eventsList.get(Integer.parseInt(number)), myName);
                usermanager.deleteSignedEvent(eventsList.get(Integer.parseInt(number)), myName);
                Presenter.success();
            } catch (InvalidActivityException | NoSuchEventException e) {
                Presenter.printErrorMessage(e); // This should never happen.
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                Presenter.invalid("");
            }
        } else {
            Presenter.exitingToMainMenu();
        }
    }

    protected void seeMyRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            Presenter.inputPrompt("NoRequests");
        } else {
            Presenter.inputPrompt("requestIntroduction");
            for (int i = 0; i < requestList.size(); i++) {
                Presenter.defaultPrint("[" + i + "] " + requestList.get(i)[0]);
            }
            Presenter.exitToMainMenuPrompt();
            Presenter.inputPrompt("readRequest");
            String number = reader.nextLine();
            if (!("e".equals(number))) {
                Presenter.defaultPrint(requestList.get(Integer.parseInt(number))[1]);
            } else {
                Presenter.exitingToMainMenu();
            }
        }
    }

    private void deleteRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            Presenter.inputPrompt("NoRequests");
        } else {
            Presenter.inputPrompt("requestIntroduction");
            for (int i = 0; i < requestList.size(); i++) {
                Presenter.defaultPrint("[" + i + "] " + requestList.get(i)[0]);
            }
            Presenter.inputPrompt("recallRequest");
            Presenter.exitToMainMenuPrompt();
            String number = reader.nextLine();
            if ("e".equals(number)) {
                Presenter.exitingToMainMenu();
            } else {
                Presenter.inputPrompt("recallRequestConfirm");
                String confirm = reader.nextLine();
                if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
                    if (!number.equals("Recall all")) {
                        try {
                            requestmanager.recallSingleRequest(requestList.get(Integer.parseInt(number))[0]);
                            Presenter.inputPrompt("deleteSuccess");
                        } catch (NoSuchRequestException e) {
                            Presenter.invalid("noSuchRequest");
                        }
                    } else {
                        try {
                            requestmanager.recallAllRequestsFrom(myName);
                            Presenter.inputPrompt("deleteSuccess");
                        } catch (NoSuchRequestException e) {
                            Presenter.invalid("noSuchRequest");
                        }
                    }
                }
                Presenter.exitingToMainMenu();
            }
        }
    }
}

