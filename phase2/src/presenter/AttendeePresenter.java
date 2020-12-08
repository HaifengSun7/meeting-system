package presenter;

public class AttendeePresenter extends Presenter {
    /**
     * Print out the menu of attendee.
     */
    public void attendeeMenu() {
        System.out.println(
                "[1] sign up for an event.\n" +
                        "[2] See events that I have signed up for\n" +
                        "[3] Cancel enrolment in an signed event\n" +
                        "[4] Send a message\n" +
                        "[5] See messages\n" +
                        "[6] Make new request\n" +
                        "[7] Check my requests\n" +
                        "[8] Recall my requests\n" +
                        "[9] Mark unread messages\n" +
                        "[10] Delete messages from inbox\n" +
                        "[11] Archive messages\n" +
                        "[12] Unarchive messages\n" +
                        "[13] See archived inbox\n" +
                        "[save] Save.\n" +
                        "[e] Save and log out.");
    }
}
