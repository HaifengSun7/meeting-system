package system;
import user.UserManager;
import textUI.*;

import java.util.Scanner;

/**
 *<h1>Login System</h1>
 *The LoginSystem program implements the system of login.
 * @author Haifeng Sun, Wei Tao
 * @version 1.0.0
 */
public class LogInSystem {

    UserManager usermanager;

    /**
     * Run the Login System. Print out login menu, and initialize users' systems.
     */
    public void run() {
        /*
          This method is in charge of logging in, separate the system and log out.
         */
        while(true){
            usermanager = new UserManager();
            boolean logged_in = false;
            String user_type = "";
            String username = "";
            Scanner reader = new Scanner(System.in);// Reading from System.in
            TextUI.login();
            String command = reader.nextLine();
            if ("e".equals(command)) {
                break;
            }else{
                for (int i = 0; i < 5; i++) {
                    TextUI.defaultPrint("You have " + (5 - i) + " trials remaining \n");
                    TextUI.name("");
                    username = reader.nextLine();
                    TextUI.password("");
                    String password = reader.nextLine();
                    try {
                        user_type = usermanager.logIn(username, password);
                    } catch (Exception e) {
                        TextUI.invalid("login");
                        continue;
                    }
                    logged_in = true;
                    break;
                }
                if (!logged_in) {
                    TextUI.noTrials();
                    return;
                }
                switch (user_type) {
                    case "Attendee":
                        AttendeeSystem as = new AttendeeSystem(username);
                        as.run();
                        break;
                    case "Organizer":
                        OrganizerSystem os = new OrganizerSystem(username);
                        os.run();
                        break;
                    case "Speaker":
                        SpeakerSystem ss = new SpeakerSystem(username);
                        ss.run();
                        break;
                }
            }
        }
    }
}

