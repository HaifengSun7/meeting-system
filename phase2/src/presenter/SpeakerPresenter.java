package presenter;

public class SpeakerPresenter extends Presenter {
    /**
     * Print out the menu of speaker.
     */
    public void speakerMenu() {
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
                        "[11] Mark unread messages\n" +
                        "[12] Delete messages from inbox\n" +
                        "[13] Archive messages\n" +
                        "[14] Unarchive messages\n" +
                        "[15] See archived inbox\n" +
                        "[save] Save.\n" +
                        "[e] Save and log out");
    }


}
