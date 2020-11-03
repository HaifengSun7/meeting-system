package system;
import user.User;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class ConferenceSystem {
    /**
     * Yo I take user input and generate commands for managers. Also a simple text UI is included.
     *
     */
    public void run() {
        /*
          This method is in charge of logging in, separate the UI and log out.
         */
        boolean logged_in = false;
        user.User person = new user.User();
        user.UserManager manager = new UserManager();
        for (int i = 0; i < 5; i++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("You have" + (5-i) + "tries remaining \n");
            System.out.println("Username:");
            String username = reader.nextLine();
            System.out.println("Password:");
            String password = reader.nextLine();
            try {
                person = user.UserManager.logIn(username, password);
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
            return;
        }
        if (manager.getUserType(person) == "Attendee"){
            AttendeeUI aui = new AttendeeUI(person);
            aui.run();
        }
    }
}

