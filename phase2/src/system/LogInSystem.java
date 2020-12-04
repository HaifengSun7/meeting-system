package system;

import event.EventManager;
import presenter.Presenter;
import user.NoSuchUserException;
import user.NotAttendeeException;
import readWrite.ManagerBuilder;
import user.UserManager;
import user.WrongLogInException;

import java.util.Scanner;

/**
 * <h1>Login System</h1>
 * The LoginSystem program implements the system of login.
 */
public class LogInSystem {

    UserManager usermanager;

    /**
     * Run the Login System. Print out login menu, initialize users' systems.
     * It also does the job for logging out.
     */
    public void run() {
        while (true) {
            usermanager = new UserManager();
            getAccounts();
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
                        user_type = usermanager.logIn(username, password);
                    } catch (WrongLogInException e) {
                        Presenter.printErrorMessage(e);
                        continue;
                    }
                    logged_in = true;
                    break;
                }
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
                                if (usermanager.isVIP(username)) {
                                    system = new VIPSystem(username);
                                } else {
                                    system = new AttendeeSystem(username);
                                }
                            } catch (NotAttendeeException | NoSuchUserException e) {
                                Presenter.printErrorMessage(e);
                                continue;
                            }
                            break;
                    }
                    system.run();
                }
            }
        }
    }

    private void getAccounts() {
        ManagerBuilder managerBuilder = new ManagerBuilder();
        managerBuilder.build();
        usermanager = managerBuilder.getUserManager();
    }
}

