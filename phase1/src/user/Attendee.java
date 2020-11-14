package user;

import java.util.ArrayList;

public class Attendee extends User {
    private ArrayList<String> signedEvent = new ArrayList<>();

    public Attendee(String username, String password) {
        super(username, password);
        this.usertype = "Attendee";
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
