package presenter;

/**
 * <h1>Presenter</h1>
 * The Presenter class is used to give out information. A textUI is included.
 *
 * @author Haifeng Sun, Wei Tao
 * @version 1.0.0
 */
public class Presenter {

    /**
     * Print out the login information to the user.
     */
    public static void logInPrompt() {
        System.out.println("Welcome to Group_0229 Conference System. Please Log in.\n" +
                "Press [e] to quit the system.\n" +
                "Press anything else to log in.");
    }

    /**
     * Print out the menu of speaker.
     */
    public static void speakerMenu() {
        System.out.println("[1] See a list of events the speaker gave.\n" +
                "[2] See a list of messages the speaker gave.\n" +
                "[3] Message all Attendees who signed up for a particular event\n" +
                "[4] Message a particular Attendee who signed up for a particular event\n" +
                "[5] Respond to an attendee\n" +
                "[6] Check your inbox\n" +
                "[e] Save and log out");
    }

    /**
     * Print out the menu of organizer.
     */
    public static void organizerMenu() {
        System.out.println("[1] see and manage rooms\n" +
                "[2] create/promote speaker account\n" +
                "[3] Schedule speakers and their events\n" +
                "[4] Send message to a particular person\n" +
                "[5] Send message to all speakers\n" +
                "[6] Send message to all attendees\n" +
                "[7] See messages\n" +
                "[e] Save and Log out");
    }

    /**
     * Print out the menu of attendee.
     */
    public static void attendeeMenu() {
        System.out.println("[1] sign up for an event.\n" +
                "[2] See events that I have signed up for\n" +
                "[3] Send a message\n" +
                "[4] See messages\n" +
                "[e] Save and log out.");
    }

    /**
     * Print out the name.
     *
     * @param username the username of user.
     */
    public static void name(String username) {
        System.out.println("Name: " + username);
    }

    /**
     * Print out the password.
     *
     * @param password the password of user.
     */
    public static void password(String password) {
        System.out.println("Password: " + password);
    }

    /**
     * Print out the type of user.
     *
     * @param usertype the usertype of user.
     */
    public static void userType(String usertype) {
        System.out.println(usertype);
    }

    /**
     * Print out the prompt of wrong key pressing.
     */
    public static void wrongKeyReminder() {
        System.out.println("Our program requires you to press down the right key on your keyboard, which is explained in README.txt");
    }

    /**
     * Print out the prompt of exiting to main menu.
     */
    public static void exitingToMainMenu() {
        System.out.println("Exiting to main menu.");
    }

    /**
     * Print out the prompt of press [e] to main menu.
     */
    public static void exitToMainMenuPrompt() {
        System.out.println("Press [e] to exit to main menu");
    }

    /**
     * Print out the prompt of successful operations.
     */
    public static void success() {
        System.out.println("Success!");
    }

    /**
     * Print out the prompt of pressing enter to continue operating system.
     */
    public static void continuePrompt() {
        System.out.println("Press enter to continue.");
    }

    /**
     * Print out the prompt of input
     *
     * @param input the type of input to the corresponding prompt.
     */
    public static void inputPrompt(String input) {
        switch (input) {
            case "message":
                System.out.println("Now input your message. Hint: Type \\n for changing of lines if you want.");
                break;
            case "receiver":
                System.out.println("Which person you want to send a message to?");
                break;
            case "eventId":
                System.out.println("Enter event id to choose the event:");
                break;
            case "roomNumber":
                System.out.println("Please enter a room number:");
                break;
            case "description":
                System.out.println("Please input a short description that does not have a comma");
                break;
            case "newRoomNumber":
                System.out.println("Please enter a new room number:");
                break;
            case "newUsername":
                System.out.println("Please enter a new username:");
                break;
            case "startTime":
                System.out.println("Please enter a start time. Format: yyyy-m[m]-d[d] hh:mm:ss");
                break;
            case "duration":
                System.out.println("Please enter a duration:");
                break;
            case "password":
                System.out.println("Please enter a password:");
                break;
            case "eventIdSendMessage":
                System.out.println("Please enter event Id that you want to send messages:");
                break;
            case "messageToRespond":
                System.out.println("Please enter the index of message that you would like to respond:");
                break;
            case "roomSize":
                System.out.println("Please enter the room size:");
                break;
            case "speakerName":
                System.out.println("Please enter the speaker's username:");
                break;
            case "signUp":
                System.out.println("Here are the events that you can sign up for. Enter serial number to choose the event.");
                break;
        }
    }

    /**
     * Print out the menus in SpeakerSystem.
     *
     * @param methodName the method name to the corresponding menu.
     */
    public static void menusInSpeaker(String methodName) {
        switch (methodName) {
            case "manageRooms":
                System.out.println("Manage rooms:\n" +
                        "[a] add a new room\n" +
                        "[b] see schedule of a certain room\n" +
                        "[c] add a new event\n" +
                        "[e] exit to main menu.");
                break;
            case "createSpeaker":
                System.out.println("You want a promotion or a creation?\n " +
                        "[a] promotion\n " +
                        "[b] creation");
                break;
            case "scheduleSpeakers1":
                System.out.println("Input the event number of an existing event to add speaker.\n");
                break;
            case "scheduleSpeakers2":
                System.out.println("Enter room number to check schedule of the room\n" +
                        "[a] to add new event");
                break;
        }
    }

    /**
     * Print out the title in SpeakerSystem.
     *
     * @param methodName the method name to the corresponding title.
     */
    public static void titlesInSpeaker(String methodName) {
        switch (methodName) {
            case "manageRooms":
                System.out.println("Here is a list of rooms");
                break;
            case "checkRoom":
                System.out.println("That's all events in this room.");
                break;
            case "promoteExistingSpeaker":
                System.out.println("Note that promotion will make that user the speaker of all their " +
                        "signed events.\n" +
                        "Enter a username to promote him/her a speaker.");
                break;
            case "scheduleSpeakers1":
                System.out.println("Showing all events:");
                break;
            case "scheduleSpeakers2":
                System.out.println("Here are the rooms.");
                break;
        }
    }

    /**
     * Print out the prompt of input out of range.
     */
    public static void inputOutOfRange() {
        System.out.println("Input out of range, exit to main menu and please try again");
    }

    /**
     * Print out the prompt of empty inbox.
     */
    public static void emptyInbox() {
        System.out.println("Your inbox is empty. Press enter to exit to main menu.");
    }

    /**
     * Print out the prompt of the user is not a speaker.
     */
    public static void notASpeaker() {
        System.out.println("This user is not a speaker.");
    }

    /**
     * Print out the prompt of no trials left as trying to login.
     */
    public static void noTrials() {
        System.out.println("Sorry, you don't have any trials left.");
    }

    /**
     * Print out the prompt of invalid input.
     *
     * @param input the type of input to the corresponding prompt.
     */
    public static void invalid(String input) {
        switch (input) {
            case "username":
                System.out.println("Username doesn't exist. Please enter a valid username.");
                break;
            case "roomNumber":
                System.out.println("Room number doesn't exist. Please enter a valid room number.");
                break;
            case "eventId":
                System.out.println("Event Id doesn't exist. Please enter a valid event Id.");
                break;
            case "addEventGeneral":
                System.out.println("Sorry, cannot add event!");
                break;
            case "getEventSchedule":
                System.out.println("Sorry, cannot get event schedule. Event unavailable.");
                break;
            case "addSpeaker":
                System.out.println("Cannot add speaker to the event.");
                break;
            case "login":
                System.out.println("Wrong username or password");
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    /**
     * Print out the prompt of duplicate invalid input.
     *
     * @param input the type of input to the corresponding prompt.
     */
    public static void duplicateInvalid(String input) {
        switch (input) {
            case "newRoom":
                System.out.println("There is a duplicated Room");
                break;
            case "username":
                System.out.println("Username already exists.");
                break;
        }
    }

    public static void autoAddToMessageList() {
        System.out.println("Added all senders to your contact list automatically.");
    }

    /**
     * Print out the default prompt.
     *
     * @param input the input that the method would print out.
     */
    public static void defaultPrint(String input) {
        System.out.println(input);
    }

    public static void failureAddEvent(String input, String room){
        if(input.equals("NotOfficeHour")){
            System.out.println("Invalid time. Please enter time between 9:00 to 16:00 to " +
                    "ensure meeting ends before 17:00");
        } else if(input.equals("TimeNotAvailable")){
            System.out.println("Failed to add event to room " + room + ": Time has been taken by other events.");
        } else{
            System.out.println("invalid room number");
        }
    }

    public static void loadEvent(String room, String time, String duration){
        System.out.println("Adding event to room " + room + ", time: " + time + " Duration: " + duration);
    }
}
