package presenter;

public class Presenter {

    public static String login() {
        return "Welcome to Group_0229 Conference System. Please Log in.\n" +
                "Press [e] to quit the system.\n" +
                "Press anything else to log in.";
    }

    public static String speakerMenu() {
        return "[1] See a list of messages the speaker gave.\n" +
                "[2] Message all Attendees who signed up for a particular event\n" +
                "[3] Message a particular Attendee who signed up for a particular event\n" +
                "[4] Respond to an attendee\n" +
                "[5] Check your inbox\n" +
                "[e] Save and log out";
    }

    public static String organizerMenu() {
        return "[1] see and manage rooms\n" +
                "[2] create/promote speaker account\n" +
                "[3] Schedule speakers and events\n" +
                "[4] Send message to a particular person\n" +
                "[5] Send message to all speakers\n" +
                "[6] Send message to all attendees\n" +
                "[7] See messages\n" +
                "[e] Save and Log out";
    }

    public static String attendeeMenu() {
        return "[1] sign up for an event.\n" +
                "[2] See events that I have signed up for\n" +
                "[3] Send a message\n" +
                "[4] See messages\n" +
                "[e] Save and log out.";
    }

    public static String name() {
        return "Name:";
    }

    public static String user(String user) {
        switch (user) {
            case "Organizer":
                return "Organizer";
            case "Speaker":
                return "Speaker";
            case "Attendee":
                return "Attendee";
            default:
                return null;
        }
    }

    public static String wrongKeyRemainderInMenu() {
        return "Please press the right key.\n";
    }

    public static String exitToMainMenu() {
        return "Return to main menu.\n";
    }

    public static String pressEnterToMainMenu() {
        return "Press enter to exit to main menu";
    }

    public static String pressEtoMainMenu() {
        return "[e] exit to main menu";
    }

    public static String success() {
        return "success!\n";
    }

    public static String successPressEnter() {
        return "Success! Press enter to continue.\n";
    }

    public static String inputPrompt(String input) {
        switch (input) {
            case "message":
                return "Now input your message. Hint: Type \\n for changing of lines if you want.";
            case "username":
                return "Which person you want to send message?";
            case "eventId":
                return "Enter event id to choose the event:";
            case "roomNumber":
                return "Please enter a room number:";
            case "newRoomNumber":
                return "Please enter a new room number:";
            case "newUsername":
                return "Please enter a new username:";
            case "startTime":
                return "Please enter a start time. Format: yyyy-m[m]-d[d] hh:mm:ss[.f…]";
            case "duration":
                return "Please enter a duration:";
            case "password":
                return "Please enter a password:";
            case "eventIdSendMessage":
                return "Please enter event Id that you want to send messages:";
            case "messageToRespond":
                return "Please enter the index of message that you would like to respond:";
            case "roomSize":
                return "Please enter the room size:";
            case "speakerName":
                return "Please enter the speaker's username:";
            default:
                return null;
        }
    }

    public static String menusInSpeaker(String methodName) {
        switch (methodName) {
            case "manageRooms":
                return "Manage rooms:\n" +
                        "[a] add a new room\n" +
                        "[b] see schedule of a certain room\n" +
                        "[c] add a new event\n" +
                        "[e] exit to main menu.";
            case "createSpeaker":
                return "You want a promotion or a creation?\n " +
                        "[a] promotion\n " +
                        "[b] creation";
            case "scheduleSpeakers1":
                return "Input the event number of an existing event to add speaker.\n";
            case "scheduleSpeakers2":
                return "Enter room number to check schedule of the room\n" +
                        "[a] to add new event\n";
            default:
                return null;
        }
    }

    public static String titlesInSpeaker(String methodName) {
        switch (methodName) {
            case "manageRooms":
                return "Here is a list of rooms";
            case "checkRoom":
                return "That's every room.";
            case "promoteExistingSpeaker":
                return "Note that promotion will make that user the speaker of all their " +
                        "signed events.\n" +
                        "Enter a username to promote him/her a speaker.";
            case "scheduleSpeakers1":
                return "Showing all events:";
            case "scheduleSpeakers2":
                return "Here are the rooms.";
            default:
                return null;
        }
    }

    public static String inputOutOfRange() {
        return "Input out of range, exit to main menu and please try again\n";
    }

    public static String emptyInbox() {
        return "Your inbox is empty. Press enter to exit to main menu.";
    }

    public static String notASpeaker() {
        return "This user is not a speaker.";
    }

    public static String noTrials() {
        return "Sorry, you don't have trials left.";
    }

    public static String invalid(String input) {
        switch (input) {
            case "username":
                return "Username doesn't exist. Please enter a valid username.\n";
            case "roomNumber":
                return "Room number doesn't exist. Please enter a valid room number.\n";
            case "eventId":
                return "Event Id doesn't exist. Please enter a valid event Id.\n";
            case "addEvent":
                return "Sorry, cannot add event! Room unavailable or time not in valid spot.\n";
            case "getEventSchedule":
                return "Sorry, cannot get event schedule. Event unavailable.\n";
            case "addSpeaker":
                return "Cannot add speaker to the event.\n";
            case "login":
                return "wrong username or password";
            default:
                return "Please enter a valid input.\n";
        }
    }

    public static String duplicateInvalid(String input) {
        switch (input) {
            case "newRoom":
                return "You added a duplicate Room";
            case "username":
                return "Username already exists.";
            default:
                return null;
        }
    }
}
