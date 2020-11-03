package system;
import user.UserManager;

import java.util.Scanner;

public class ConferenceSystem {
    /**
     * Yo I login tho. and send people to different UI.
     *
     */
    UserManager usermanager = new UserManager();
    public void run() {
        /*
          This method is in charge of logging in, separate the UI and log out.
         */
        //TODO: READ.
        boolean logged_in = false;
        user.User person = new user.User();
        user.UserManager manager = new UserManager();
        for (int i = 0; i < 5; i++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("You have" + (5-i) + "trials remaining \n");
            System.out.println("Username:");
            String username = reader.nextLine();
            System.out.println("Password:");
            String password = reader.nextLine();
            try {
                person = usermanager.logIn(username, password);
            } catch (exception e) //exception should be implemented in UserManager
            {
                //error handling code
                //...
                continue;
            }
            logged_in = true;
            break;
        }
        if (!logged_in) {
            //TODO: be sure to save lol, or not.
            return;
        }
        switch (getUserType(person)){
            case "Attendee":
                AttendeeSystem as = new AttendeeSystem(person);
                as.run();
            case "Organizer":
                OrganizerSystem os = new OrganizerSystem(person);
                os.run();


        }
        //TODO: after running, save.

    }
}

