package user;

import java.util.ArrayList;

public class Organizer extends User {
    public Organizer(String username, String password) {
        super(username, password);
        this.usertype = "Organizer";
    }

    /**
     * Get nothing!
     *
     * @return null.
     */
    @Override
    public ArrayList<String> getSignedEvent() {
        return null;
    }

    /**
     * Set nothing!
     *
     * @param signedEvent a list of eventID in string.
     */
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {
    }
}
