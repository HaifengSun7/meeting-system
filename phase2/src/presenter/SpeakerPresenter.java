package presenter;

public class SpeakerPresenter extends Presenter{
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
                        "[8] Make new request\n" +
                        "[9] Check my requests\n" +
                        "[10] Delete my requests\n" +
                        "[save] Save.\n" +
                        "[e] Save and log out");
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
}
