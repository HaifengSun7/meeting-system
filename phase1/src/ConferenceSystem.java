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
        for (int i = 0; i < 5; i++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("You have" + (5-i) + "tries remaining \n");
            System.out.println("Username:");
            String username = reader.nextLine();
            System.out.println("Password:");
            String password = reader.nextLine();
            try {
                User user = UserManager.logIn(username, password);
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
        if (user.UserManager.getUserType() == "Attendee"){
            return this.attendeeUI(user);
        }
    }
    public void attendeeUI(Attendee attendee){
        Scanner reader = new Scanner(System.in);
        System.out.println("Name:" + attendee.toString());
        System.out.println("Attendee");
        System.out.println("[1] Schedule of events that I can sign up for.\n[2] See events that I have signed up for\n[3] Send a message\n [e] exit");
        String command = reader.nextLine();
        switch (command){
            case "e":
                return;

        case "1":
            //TODO: Show a list of schedule which they can sign up for.
            example_list = new ArrayList<Event>(); // Just an example
            for(int i = 0; i < example_list.length(); i++){
                System.out.println("[" + i + "] " + example_list[i].toString());
            }
            command = reader.nextLine();

        }

    }

}
