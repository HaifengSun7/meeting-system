package user;

import java.util.ArrayList;

public abstract class User {
    protected String username;
    protected String password;
    protected String usertype = "attendee";
    protected ArrayList<String> contactList;
    protected boolean status;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.status = false;
        this.contactList = new ArrayList<>();
    }

    public String getUserName(){
        return this.username;
    }
    public void setUserName(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public ArrayList<String> getContactList(){
        return contactList;
    }
    public void setContactList(ArrayList<String> contactList){
        this.contactList = contactList;
    }

    public boolean getStatus(){
        return this.status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }

    public String getUserType(){
        return usertype;
    }

    public abstract ArrayList<String> getSignedEvent();
    public abstract void setSignedEvent(ArrayList<String> signedEvent);
}
