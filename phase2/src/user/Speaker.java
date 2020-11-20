package user;

import java.util.ArrayList;

/**
 * An Speaker User of the conference.
 */
public class Speaker extends User {
    private ArrayList<String> signedEvent = new ArrayList<>();

    /**
     * Constructor for a Speaker object.
     *
     * @param username the username of the user.
     * @param password the password for log in.
     */
    public Speaker(String username, String password) {
        super(username, password);
        this.usertype = "Speaker";
    }

    /**
     * Get all the events that the speaker will give speech for.
     *
     * @return a list of eventID in String.
     */
    @Override
    public ArrayList<String> getSignedEvent() {
        return this.signedEvent;
    }

    /**
     * Set the events that the speaker will give speech for.
     *
     * @param signedEvent a list of eventID in string.
     */
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {
        this.signedEvent = signedEvent;
    }

}
