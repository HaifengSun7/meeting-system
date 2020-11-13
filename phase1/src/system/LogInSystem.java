package system;
import user.UserManager;
import presenter.*;

import java.util.Scanner;

public class LogInSystem {

    UserManager usermanager;
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
            Presenter.login();
            String command = reader.nextLine();
            if ("e".equals(command)) {
                break;
            }else{
                for (int i = 0; i < 5; i++) {
                    Presenter.defaultPrint("You have " + (5 - i) + " trials remaining \n");
                    Presenter.name("");
                    username = reader.nextLine();
                    Presenter.password("");
                    String password = reader.nextLine();
                    try {
                        user_type = usermanager.logIn(username, password);
                    } catch (Exception e) {
                        Presenter.invalid("login");
                        continue;
                    }
                    logged_in = true;
                    break;
                }
                if (!logged_in) {
                    Presenter.noTrials();
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

