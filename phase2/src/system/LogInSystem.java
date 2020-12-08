package system;

import presenter.Presenter;
import readWrite.ManagerBuilder;
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

    UserManager usermanager;
    Presenter presenter;

    public LogInSystem() {
        this.presenter = new Presenter();
    }

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
            presenter.logInPrompt();
            String command = reader.nextLine();
            if ("e".equals(command)) {
                break;
            } else {
                for (int i = 0; i < 5; i++) {
                    presenter.trailsRemaining(5 - i);
                    presenter.name("");
                    username = reader.nextLine();
                    presenter.password("");
                    String password = reader.nextLine();
                    try {
                        user_type = usermanager.logIn(username, password);
                    } catch (WrongLogInException e) {
                        presenter.printErrorMessage(e);
                        continue;
                    }
                    logged_in = true;
                    break;
                }
                if (!logged_in) {
                    presenter.trailsRemaining(0);
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
                                presenter.printErrorMessage(e);
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

