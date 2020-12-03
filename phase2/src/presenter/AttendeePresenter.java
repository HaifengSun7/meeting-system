package presenter;

public class AttendeePresenter extends Presenter{
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
                        "[7] Check my requests\n" +
                        "[8] Recall my requests\n" +
                        "[save] Save.\n" +
                        "[e] Save and log out.");
    }
}
