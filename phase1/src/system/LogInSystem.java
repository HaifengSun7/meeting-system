package system;
import ReadWrite.UserManagerInitializer;
import sun.rmi.runtime.Log;
import user.UserManager;

import java.util.Scanner;

public class LogInSystem {

    UserManager usermanager = new UserManager();
    public void run() {
        /*
          This method is in charge of logging in, separate the system and log out.
         */
        UserManagerInitializer userManagerInitializer = new UserManagerInitializer();
        usermanager = userManagerInitializer.run();

        boolean logged_in = false;
        String user_type = "";
        String username = "";
        for (int i = 0; i < 5; i++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("You have " + (5-i) + " trials remaining \n");
            System.out.println("Username:");
            username = reader.nextLine();
            System.out.println("Password:");
            String password = reader.nextLine();
            try {
                user_type = usermanager.logIn(username, password);
            } catch (Exception e)
            {
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
        switch (user_type){
            case "Attendee":
                AttendeeSystem as = new AttendeeSystem(username);
                as.run();
            case "Organizer":
                OrganizerSystem os = new OrganizerSystem(username);
                os.run();
        }
    }
}

