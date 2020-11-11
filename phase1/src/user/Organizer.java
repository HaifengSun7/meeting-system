package user;

import java.util.ArrayList;

public class Organizer extends User {
    private ArrayList<String> signedEvent = new ArrayList<>();
    public Organizer(String username, String password) {
        super(username, password);
        this.usertype = "Organizer";
    }

    @Override
    public ArrayList<String> getSignedEvent() {
        return this.signedEvent;
    }

    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {
        this.signedEvent = signedEvent;
    }
}