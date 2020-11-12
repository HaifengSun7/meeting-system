package system;
import user.UserManager;

import java.util.Scanner;

public class LogInSystem {

    UserManager usermanager = new UserManager();
    public void run() {
        /*
          This method is in charge of logging in, separate the system and log out.
         */
        while(true){
            boolean logged_in = false;
            String user_type = "";
            String username = "";
            Scanner reader = new Scanner(System.in);// Reading from System.in
            System.out.println("Welcome to Group_0229 Conference System. Please Log in.\n" +
                    "Press [e] to quit the system.\n" +
                    "Press anything else to log in.");
            String command = reader.nextLine();
            if (!"e".equals(command)) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("You have " + (5 - i) + " trials remaining \n");
                    System.out.println("Username:");
                    username = reader.nextLine();
                    System.out.println("Password:");
                    String password = reader.nextLine();
                    try {
                        user_type = usermanager.logIn(username, password);
                    } catch (Exception e) {
                        System.out.println("wrong username or password");
                        continue;
                    }
                    logged_in = true;
                    break;
                }
                if (!logged_in) {
                    System.out.println("Sorry, you don't have trials left.");
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

