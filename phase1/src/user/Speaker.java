package user;

import java.util.ArrayList;

public class Speaker extends User {
    private ArrayList<String> signedEvent = new ArrayList<>();

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
