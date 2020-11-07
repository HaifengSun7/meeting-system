package user;

import event.Event;

import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<Event> signedEvent;

    public Attendee(String username, String password) {
        super(username, password);
        this.usertype = "Attendee";
    }

    @Override
    public ArrayList<Event> getSignedEvent(){
        return this.signedEvent;
    }

    public void setSignedEvent(ArrayList<Event> signedEvent){
        this.signedEvent = signedEvent;
    }

}
