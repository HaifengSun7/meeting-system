package user;

/**
 * The VIP class, representing a VIP attendee.
 */
public class VIP extends Attendee {
    /**
     * Constructor for an Attendee object.
     *
     * @param username the username of the attendee.
     * @param password the password for log in.
     */
    public VIP(String username, String password) {
        super(username, password);
        super.VIPstatus = true;
    }

}
