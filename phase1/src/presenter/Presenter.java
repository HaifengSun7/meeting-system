package presenter;

public class Presenter {

    public static void login() {
        System.out.println("Welcome to Group_0229 Conference System. Please Log in.\n" +
                "Press [e] to quit the system.\n" +
                "Press anything else to log in.");
    }

    public static void speakerMenu() {
        System.out.println("[1] See a list of events the speaker gave.\n" +
                "[2] See a list of messages the speaker gave.\n" +
                "[3] Message all Attendees who signed up for a particular event\n" +
                "[4] Message a particular Attendee who signed up for a particular event\n" +
                "[5] Respond to an attendee\n" +
                "[6] Check your inbox\n" +
                "[e] Save and log out");
    }

    public static void organizerMenu() {
        System.out.println("[1] see and manage rooms\n" +
                "[2] create/promote speaker account\n" +
                "[3] Schedule speakers and events\n" +
                "[4] Send message to a particular person\n" +
                "[5] Send message to all speakers\n" +
                "[6] Send message to all attendees\n" +
                "[7] See messages\n" +
                "[e] Save and Log out");
    }

    public static void attendeeMenu() {
        System.out.println("[1] sign up for an event.\n" +
                "[2] See events that I have signed up for\n" +
                "[3] Send a message\n" +
                "[4] See messages\n" +
                "[e] Save and log out.");
    }

    public static void name(String username) {
        System.out.println("Name: " + username);
    }

    public static void password(String password) {
        System.out.println("Password: " + password);
    }

    public static void userType(String usertype) {
        System.out.println(usertype);
    }

    public static void wrongKeyReminder() {
        System.out.println("Our program requires you to press down the right key on your keyboard, which is explained in README.txt");
    }

    public static void exitingToMainMenu() {
        System.out.println("Exiting to main menu.");
    }

    public static void exitToMainMenuPrompt() {
        System.out.println("Press [e] to exit to main menu");
    }

    public static void success() {
        System.out.println("Success!");
    }

    public static void continuePrompt() {
        System.out.println("Press enter to continue.");
    }

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
                System.out.println( "Here are the rooms.");
                break;
        }
    }

    public static void inputOutOfRange() {
        System.out.println("Input out of range, exit to main menu and please try again");
    }

    public static void emptyInbox() {
        System.out.println("Your inbox is empty. Press enter to exit to main menu.");
    }

    public static void notASpeaker() {
        System.out.println("This user is not a speaker.");
    }

    public static void noTrials() {
        System.out.println("Sorry, you don't have any trials left.");
    }

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
            case "addEvent":
                System.out.println("Sorry, cannot add event! Room unavailable or time not in valid spot.");
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



    public static void defaultPrint(String input){
        System.out.println(input);
    }
}
