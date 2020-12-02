package system;

import event.exceptions.*;
import presenter.Presenter;

import java.util.ArrayList;

public class VIPSystem extends AttendeeSystem{
    public VIPSystem(String myName) {
        super(myName);
    }

    @Override
    protected void SignUpForEvent() {
        ArrayList<String> example_list = eventmanager.canSignUp(myName, true);
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
}
