package system;

import event.EventManager;
import presenter.Presenter;
import readWrite.Iterator;
import readWrite.Read;
import readWrite.UserIterator;
import user.NoSuchUserException;
import user.NotAttendeeException;
import user.UserManager;
import user.WrongLogInException;

import java.util.Scanner;

/**
 * <h1>Login System</h1>
 * The LoginSystem program implements the system of login.
 */
public class LogInSystem {

    UserManager userManager;
    EventManager eventManager;

    /**
     * Run the Login System. Print out login menu, and initialize users' systems.
     */
    public void run() {
        /*
          This method is in charge of logging in, separate the system and log out.
         */
        while (true) {
            userManager = new UserManager();
            getAccountsAndConference();
            boolean logged_in = false;
            String user_type = "";
            String username = "";
            Scanner reader = new Scanner(System.in);// Reading from System.in
            Presenter.logInPrompt();
            String command = reader.nextLine();
            if ("e".equals(command)) {
                break;
            } else {
                for (int i = 0; i < 5; i++) {
                    Presenter.trailsRemaining(5 - i);
                    Presenter.name("");
                    username = reader.nextLine();
                    Presenter.password("");
                    String password = reader.nextLine();
                    try {
                        user_type = userManager.logIn(username, password);
                    } catch (WrongLogInException e) {
                        Presenter.printErrorMessage(e.getMessage());
                        continue;
                    }
                    logged_in = true;
                    break;
                }
                Presenter.conferenceChoose();
                Presenter.inputPrompt("enterNumberInSquareBracketsToChooseConference");

                if (!logged_in) {
                    Presenter.trailsRemaining(0);
                    return;
                } else {
                    UserSystem system;
                    switch (user_type) {
                        case "Organizer":
                            system = new OrganizerSystem(username);
                            break;
                        case "Speaker":
                            system = new SpeakerSystem(username);
                            break;
                        default:
                            try {
                                if (userManager.isVIP(username)) {
                                    system = new VIPSystem(username);
                                } else {
                                    system = new AttendeeSystem(username);
                                }
                            } catch (NotAttendeeException | NoSuchUserException e) {
                                Presenter.printErrorMessage(e.getMessage());
                                continue;
                            }
                            break;
                    }
                    system.run();
                }
            }
        }
    }

    private void getAccountsAndConference() {
        Read read = new Read();
        read.run();
        userManager = read.usermanager;
        eventManager = read.eventmanager;
    }
}

