package user;

import java.util.ArrayList;

public class Organizer extends User{
    public Organizer(String username, String password) {
        super(username, password);
        this.usertype = "Organizer";
    }

    @Override
    public ArrayList<String> getSignedEvent() {
        return null;
    }
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent) {}
}
