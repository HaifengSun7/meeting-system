package user;

import event.Event;

import java.util.ArrayList;

public class Speaker extends User{
    private ArrayList<Event> signedEvent;

    public Speaker(String username, String password) {
        super(username, password);
        this.usertype = "Speaker";
    }

    @Override
    public ArrayList<Event> getSignedEvent(){
        return this.signedEvent;
    }
    @Override
    public void setSignedEvent(ArrayList<Event> signedEvent){
        this.signedEvent = signedEvent;
    }

}
