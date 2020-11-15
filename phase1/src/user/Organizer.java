package user;

import java.util.ArrayList;

public class Organizer extends User {
    private final ArrayList<String> signedEvent = new ArrayList<>();

    public Organizer(String username, String password) {
        super(username, password);
        this.usertype = "Organizer";
    }

    /**
     * Get a signedEvent list of organizer!
     *
     * @return signedEvent.
     */
    @Override
    public ArrayList<String> getSignedEvent() {
        return signedEvent;
    }

    /**
     * Set nothing! Seriously, nothing!
     *
     * @param signedEvent a list of eventID in string.
     */
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {
    }
}
