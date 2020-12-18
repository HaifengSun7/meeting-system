package user;

import java.util.ArrayList;

/**
 * An Attendee User of the conference.
 */
public class Attendee extends User {
    protected ArrayList<String> signedEvent = new ArrayList<>();
    protected boolean VIPstatus = false;

    /**
     * Constructor for an Attendee object.
     *
     * @param username the username of the attendee.
     * @param password the password for log in.
     */
    public Attendee(String username, String password) {
        super(username, password);
        this.usertype = "Attendee";
    }

    /**
     * Get all the events that the attendee signed up for.
     *
     * @return a list of eventID in String.
     */
    @Override
    public ArrayList<String> getSignedEvent() {
        return this.signedEvent;
    }

    /**
     * Set the events that the attendee signed up for.
     *
     * @param signedEvent a list of eventID in string.
     */
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {
        this.signedEvent = signedEvent;
    }

    /**
     * Get if the user is VIP
     *
     * @return true for VIP, false for non-VIP.
     */
    public boolean getVIPstatus() {
        return this.VIPstatus;
    }
}
