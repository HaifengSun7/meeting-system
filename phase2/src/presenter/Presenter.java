package presenter;

/**
 * <h1>Presenter</h1>
 * The Presenter class is used to give out information. A textUI is included.
 */
public class Presenter {

    /**
     * Print out the login information to the user.
     */
    public static void logInPrompt() {
        System.out.println("Welcome to Group_0229 Conference System. Please Log in.\n" +
                "Input [e] to quit the system.\n" +
                "Press enter to log in.");
    }

    /**
     * Print out the menu of speaker.
     */
    public static void speakerMenu() {
        System.out.println(
                "[1] See a list of events the speaker gave.\n" +
                "[2] See a list of messages the speaker gave.\n" +
                "[3] Message all Attendees who signed up for a particular event\n" +
                "[4] Message a particular Attendee who signed up for a particular event\n" +
                "[5] Respond to an attendee\n" +
                "[6] Send a message to someone\n" +
                "[7] Check your inbox\n" +
                "[e] Save and log out");
    }

    /**
     * Print out the menu of organizer.
     */
    public static void organizerMenu() {
        System.out.println(
                "[1] manage rooms and add events\n" +
                "[2] create/promote speaker account\n" +
                "[3] Schedule speakers and their events\n" +
                "[4] Send message to a particular person\n" +
                "[5] Send message to all speakers\n" +
                "[6] Send message to all attendees\n" +
                "[7] See messages\n" +
                "[8] create/promote VIP account\n" +
                "[e] Save and Log out");
    }

    /**
     * Print out the menu of attendee.
     */
    public static void attendeeMenu() {
        System.out.println(
                "[1] sign up for an event.\n" +
                "[2] See events that I have signed up for\n" +
                "[3] Cancel enrolment in an signed event\n" +
                "[4] Send a message\n" +
                "[5] See messages\n" +
                "[6] Make new request\n" +
                "[7] See my requests\n" +
                "[8] Delete my requests\n" +
                "[e] Save and log out.");
    }

    /**
     * Print out the name.
     *
     * @param username the username of user.
     */
    public static void name(String username) {
        System.out.println("UserName: " + username);
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
     * Print out the reminder of wrong key pressing.
     */
    public static void wrongKeyReminder() {
        System.out.println("Our program requires you to press down the right key on your keyboard, which is explained in Readme.txt");
    }

    /**
     * Print out the information of exiting to main menu.
     */
    public static void exitingToMainMenu() {
        System.out.println("Exiting to main menu.");
    }

    /**
     * Print out the prompt of Input [e] to main menu.
     */
    public static void exitToMainMenuPrompt() {
        System.out.println("Input [e] to exit to main menu");
    }

    /**
     * Print out the information of successful operations.
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
            case "vip" :
                System.out.println("Please input 'true' or 'false' to indicate whether the event is for VIP only");
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
                System.out.println("Here are the events that you can sign up for.");
                break;
            case "maximum people":
                System.out.println("Please enter the maximum number of Attendees in the event:");
                break;
            case "newMaxPeopleOfEvent":
                System.out.println("Please enter the new maximum people in the event:");
                break;
            case "enterNumberInSquareBracketsToChooseEvent":
                System.out.println("Enter number in square brackets to choose the event.");
                break;
            case "eventType":
                System.out.println("Available types: \n" +
                        "[Single] Single Speaker Events\n" +
                        "[Multi] Multi Speaker Events\n" +
                        "[Party] Party with no speakers\n" +
                        "[VIP] Event only for VIPs\n" +
                        "Please enter the type of event you want:");
                break;
            case "numSpeaker":
                System.out.println("Please enter the number of Speakers in the event:");
                break;
            case "enterEventIdToCancelEvent":
                System.out.println("Please enter the event id to cancel the event: ");
                break;
            case "requestIntroduction":
                System.out.println("Here is all requests you made before: ");
                break;
            case "makeRequestTitle":
                System.out.println("Please type in the title of request:");
                break;
            case "makeRequestContext":
                System.out.println("Please type in the content of request:");
                break;
            case "readRequest":
                System.out.println("Please enter the index of request you want to read: ");
                break;
            case "recallRequest":
                System.out.println("Please enter the index of request you want to recall. \n" +
                    "Or, type in 'Recall all' to recall all requests: ");
                break;
            case "recallRequestConfirm":
                System.out.println("Are you sure you want to recall it/them? \n" + "[Yes] \n" + "[No]");
                break;
            case "deleteSuccess":
                System.out.println("Delete success!");
                break;
        }
    }

    /**
     * Print out the menus in OrganizerSystem.
     *
     * @param methodName the method name to the corresponding menu.
     */
    public static void menusInOrganizer(String methodName) {
        switch (methodName) {
            case "manageRooms":
                System.out.println("Manage rooms:\n" +
                        "[a] add a new room\n" +
                        "[b] see schedule of a certain room\n" +
                        "[c] add a new event\n" +
                        "[c2] cancel an event\n" +
                        "[d] change an event's maximum number of people\n" +
                        "[e] exit to main menu.");
                break;
            case "createSpeaker":
            case "createVIP":
                System.out.println("You want a promotion or a creation?\n " +
                        "[a] promotion\n " +
                        "[b] creation");
                break;
            case "promoteExistingAttendee":
                System.out.println("Note that promotion will make that attendee to be the VIP attendee.\n" +
                        "Enter a username to promote him/her a VIP.");
                break;
            case "scheduleSpeakers1":
                System.out.println("Input the event number of an existing event to add speaker.\n" +
                        "[r] to check schedules of rooms.");
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
            case "AddEvents":
                System.out.println("Now please add the event.");
                break;
        }
    }

    /**
     * Print out the error message of input out of range.
     */
    public static void inputOutOfRange() {
        System.out.println("Input out of range, exit to main menu and please try again");
    }

    /**
     * Print out the information of empty inbox.
     */
    public static void emptyInbox() {
        System.out.println("Your inbox is empty.");
    }

    /**
     * Print out the error message of the user is not a speaker.
     */
    public static void notASpeaker() {
        System.out.println("This user is not a speaker.");
    }


    /**
     * Print out error messages of invalid input.
     *
     * @param input the type of input to the corresponding message.
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
            case "roomFull":
                System.out.println("Sorry, you cannot add this event due to the room capacity.");
                break;
            case "fileRead":
                System.out.println("The .csv files are corrupted.");
                break;
            case "createUsername":
                System.out.println("Username should not contain a comma.");
                break;
            case "invalidRequestTitle":
                System.out.println("This Request title already exist, please try another one!");
                break;
            case"noSuchRequest":
                System.out.println("Cannot find such request");
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

//    /**
//     * Print out the error message of duplicate invalid input.
//     *
//     * @param input the type of input to the corresponding prompt.
//     */
//    public static void duplicateInvalid(String input) {
//        switch (input) {
//            case "newRoom":
//                System.out.println("There is a duplicated Room");
//                break;
//            case "username":
//                System.out.println("Username already exists.");
//                break;
//        }
//    }

    public static void autoAddToMessageList() {
        System.out.println("Added all senders to your contact list automatically.");
    }

    /**
     * Print out the default.
     *
     * @param input the input that the method would print out.
     */
    public static void defaultPrint(String input) {
        System.out.println(input);
    }

//        /**
//         * Cases where the eventManager couldn't add event.
//         * @param input the case of the failure.
//         * @param room the room number that the user tries to add event.
//         */
//        public static void failureAddEvent(String input, String room){
//            if(input.equals("NotOfficeHour")){
//                System.out.println("Invalid time. Please enter time between 9:00 to 16:00 to " +
//                        "ensure meeting ends before 17:00");
//            } else if(input.equals("TimeNotAvailable")){
//                System.out.println("Failed to add event to room " + room + ": Time has been taken by other events.");
//            } else{
//                System.out.println("invalid room number");
//            }
//        }

    /**
     * Prints the process of loading an event.
     *
     * @param room     the room number of event.
     * @param time     the time of the event.
     * @param duration the length of the event.
     */
    public static void loadEvent(String room, String time, String duration) {
        System.out.println("Adding event to room " + room + ", time: " + time + " Duration: " + duration);
    }

    /**
     * Shows how many trails a user has.
     *
     * @param i number of trails.
     */
    public static void trailsRemaining(int i) {
        System.out.println("You have " + i + " trials remaining \n");
        if (i == 0) {
            System.out.println("See Readme.txt for some login information. See user.csv file for all login information. Bye bye.");
        }
    }

    /**
     * print error message.
     *
     * @param s error message
     */
    public static void printErrorMessage(String s) {
        System.out.println(s);
    }


}
