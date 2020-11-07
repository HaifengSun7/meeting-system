package user;

import java.util.ArrayList;

public class Speaker extends User{
    private ArrayList<String> signedEvent;

    public Speaker(String username, String password) {
        super(username, password);
        this.usertype = "Speaker";
    }

    @Override
    public ArrayList<String> getSignedEvent(){
        return this.signedEvent;
    }
    @Override
    public void setSignedEvent(ArrayList<String> signedEvent){
        this.signedEvent = signedEvent;
    }

}
