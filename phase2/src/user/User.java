package user;

import java.util.ArrayList;

/**
 * The Abstract class representing all users.
 */
public abstract class  User{
    protected String username;
    protected String password;
    protected String usertype = "attendee";
    protected ArrayList<String> contactList;
    protected boolean status;

    /**
     * The superclass constructor for User. Used in constructor of subclasses. Sets username, password
     * log in status, and contact list.
     *
     * @param username the username of the User.
     * @param password the password of the User.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = false;
        this.contactList = new ArrayList<>();
    }

    /**
     * Get the username of the user.
     *
     * @return username of the user in String.
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Set the username of the user.
     *
     * @param username username in string.
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Get the password of the user.
     *
     * @return password of the user in String.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the password of the user.
     *
     * @param password password in string.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the contact list of the user.
     *
     * @return a list of username in String.
     */
    public ArrayList<String> getContactList() {
        return contactList;
    }

    /**
     * Set the contact list for the user.
     *
     * @param contactList a list of strings of usernames.
     */
    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }

    /**
     * Get the login status of the user.
     *
     * @return a boolean value representing the login status.
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Set the login status of the user.
     *
     * @param status login status in boolean.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Get the usertype of the user.
     *
     * @return a string of usertype.
     */
    public String getUserType() {
        return usertype;
    }

    /**
     * Get all the signed events of the user.
     *
     * @return a list of eventID in String.
     */
    public abstract ArrayList<String> getSignedEvent();

    /**
     * Set the signed events for the user.
     *
     * @param signedEvent a list of eventID in string.
     */
    public abstract void setSignedEvent(ArrayList<String> signedEvent);
}
