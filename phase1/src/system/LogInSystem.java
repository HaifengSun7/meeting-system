package system;
import sun.rmi.runtime.Log;
import user.UserManager;

import java.util.Scanner;

public class LogInSystem {
    /**
     * Yo I login tho. and send people to different UI.
     *
     */

    UserManager usermanager = new UserManager();
    public void run() {
        /*
          This method is in charge of logging in, separate the system and log out.
         */
        boolean logged_in = false;
        String user_type = "";
        String username = "";
        for (int i = 0; i < 5; i++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("You have" + (5-i) + "trials remaining \n");
            System.out.println("Username:");
            username = reader.nextLine();
            System.out.println("Password:");
            String password = reader.nextLine();
            try {
                user_type = usermanager.logIn(username, password);
            } catch (Exception e) //exception should be implemented in UserManager.
            {
                //error handling code
                //...
                continue;
            }
            logged_in = true;
            break;
        }
        if (!logged_in) {
            //TODO: be sure to save, or not.
            return;
        }
        switch (user_type){
            case "Attendee":
                AttendeeSystem as = new AttendeeSystem(username);
                as.run();
            case "Organizer":
                OrganizerSystem os = new OrganizerSystem(username);
                os.run();
            case "Speaker":
                SpeakerSystem ss = new SpeakerSystem(username);
                ss.run();
        }
        //TODO: after running, save.

    }
}

