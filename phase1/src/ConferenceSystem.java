import java.util.Scanner;

public class ConferenceSystem {
    /**
     * Yo I take user input and generate commands for managers. Also a text UI is included.
     *
     */
    public void run() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Username:");
        String username = reader.nextLine(); // Scans the next token of the input as an int.
//once finished
        System.out.println("Password:");
        String password = reader.nextLine();
        reader.close();
        try
        {
            user = userManager.logIn(username, password);
        }
        catch (exception(type) e) //exception should be implemented in userManager
        {
            //error handling code
        }
    }

}
