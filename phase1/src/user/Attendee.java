package user;

import java.util.ArrayList;

public class Attendee extends User {
    private ArrayList<String> signedEvent = new ArrayList<>();

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

}
