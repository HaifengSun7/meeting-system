package user;

import readWrite.EventIterator;
import readWrite.UserIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class UserManager {

    private final Map<String, User> userMapping;

    /**
     * Initializes the user manager, reads from save file user.csv
     */
    public UserManager() {
        this.userMapping = new HashMap<>();
    }

    /**
     * Create a new user account.
     * @param usertype: User's type in string.
     * @param username: User's name in string.
     * @param password: User's password in string.
     * @exception InvalidUsernameException throw this exception if username is not qualified.
     * @exception DuplicateUserNameException throw this exception if username has existed.
     */
    public void createUserAccount(String usertype, String username, String password) throws Exception {
        if (username.length() < 3) {
            throw new InvalidUsernameException("length of username should be at least 3");
        }
        if (userMapping.containsKey(username)) {
            throw new DuplicateUserNameException("DuplicateUserName : " + username);
        } else {
            if (usertype.equals("Speaker")) {
                Speaker newUser = new Speaker(username, password);
                addUser(newUser);
            } else if (usertype.equals("Organizer")) {
                Organizer newUser = new Organizer(username, password);
                addUser(newUser);
            } else {
                Attendee newUser = new Attendee(username, password);
                addUser(newUser);
            }
        }
    }

    /**
     * Get a new user's signed event list
     * @param username: a User's username in string.
     * @return a ArrayList of signed events in string.
     */
    public ArrayList<String> getSignedEventList(String username) {
        return userMapping.get(username).getSignedEvent();
    }

    /**
     * Get a new user's user type
     * @param username: a User's username in string.
     * @return userType: a string, user's type in string.
     */
    public String getUserType(String username) {
        return userMapping.get(username).getUserType();
    }

    /**
     * Get a new user's contact list
     * @param username: a User's username in string.
     * @return a ArrayList of contact list in string.
     */
    public ArrayList<String> getContactList(String username) {
        return userMapping.get(username).getContactList();
    }

/*    /**
     * Get a new user's status
     * @param username: a User's username in string.
     * @return User's status in boolean.
     * /
    public boolean getStatus(String username) {
        return userMapping.get(username).getStatus();
    }
*/
    /**
     * Get a new user's password
     * @param username: a User's username in string.
     * @return password: User's password in string.
     */
    public String getPassword(String username) {
        return userMapping.get(username).password;
    }

    /**
     * Log in a user, change the status of a user if he login successfully.
     * @param username: a User's username in string.
     * @param password: a User's password in string.
     * @return the user's type
     * @throws WrongLogInException when log in failed.
     */
    public String logIn(String username, String password) throws WrongLogInException {
        User user = userMapping.get(username);
        if (user.password.equals(password)) {
            user.setStatus(true);
            return user.getUserType();
        } else {
            throw new WrongLogInException("Wrong Username or Password");
        }
    }

    /**
     * Log out a user, change the status of a user if he logout successfully.
     *
     * @param username: a User's username in string.
     */
    public void logout(String username){
        try {
            User user = findUser(username);
            user.setStatus(false);
        } catch (NoSuchUserException ignored){
        }
    }

    /**
     * make attendee become Speaker
     *
     * @param attendeeName Attendee username in String.
     * @throws Exception throw an exception when necessary.
     */
    //* @return a list of signed event list of this attendee in string.

    public void becomeSpeaker(String attendeeName) throws Exception {
        if (!userMapping.containsKey(attendeeName)) {
            throw new NoSuchUserException("NoSuchUser: " + attendeeName);
        } else {
            User attendee = userMapping.get(attendeeName);
            ArrayList<String> contactList = attendee.getContactList();
            ArrayList<String> signedEvent = attendee.getSignedEvent();
            boolean status = attendee.getStatus();
            deleteUser(attendeeName);
            createUserAccount("Speaker", attendee.getUserName(), attendee.getPassword());
            User speaker = userMapping.get(attendeeName);
            speaker.setContactList(contactList);
            speaker.setStatus(status);
            speaker.setSignedEvent(signedEvent);
            //return signedEvent;
        }
    }

    /**
     * get all usernames
     *
     * @return a collection of all username in string.
     */
    public Collection<String> getAllUsernames() {
        return userMapping.keySet();
    }

    /**
     * get all speakers!
     *
     * @return a list of all Speaker's name in string.
     */
    public ArrayList<String> getSpeakers() {
        ArrayList<String> speaker = new ArrayList<>();
        for (String username : userMapping.keySet()) {
            if (userMapping.get(username).getUserType().equals("Speaker")) {
                speaker.add(username);
            }
        }
        return speaker;
    }

    /**
     * get all attendees!
     *
     * @return a list of all Attendee's name in string.
     */
    public ArrayList<String> getAttendees() {
        ArrayList<String> attendee = new ArrayList<>();
        for (String username : userMapping.keySet()) {
            if (userMapping.get(username).getUserType().equals("Attendee")) {
                attendee.add(username);
            }
        }
        return attendee;
    }

    /**
     * Add a new event to a user's signed event list.
     *
     * @param eventId: a event's id.
     * @param username: a User's username.
     */
    public void addSignedEvent(String eventId, String username) {
        ArrayList<String> lst = userMapping.get(username).getSignedEvent();
        lst.add(eventId);
        userMapping.get(username).setSignedEvent(lst);
    }

    /**
     * Delete a event from a user's signed event list.
     *
     * @param eventId: a event's id in string.
     * @param username: a User's username in string.
     */
    public void deleteSignedEvent(String eventId, String username) {
        ArrayList<String> lst = userMapping.get(username).getSignedEvent();
        lst.remove(eventId);
        userMapping.get(username).setSignedEvent(lst);
    }

    /**
     * Add a new contact to a user's contact list.
     *
     * @param contactName: another user's username in string.
     * @param username: a User's username in string.
     */
    public void addContactList(String contactName, String username) {
        ArrayList<String> lst = userMapping.get(username).getContactList();
        if (!(lst.contains(username))) {
            lst.add(contactName);
        }
        userMapping.get(username).setContactList(lst);
    }

    /**
     * Delete a exist contact from a user's contact list.
     *
     * @param contactName: another user's username in string.
     * @param username: a User's username in string.
     */
    public void deleteContactList(String contactName, String username) {
        ArrayList<String> lst = userMapping.get(username).getContactList();
        lst.remove(contactName);
        userMapping.get(username).setContactList(lst);
    }

    private User findUser(String userName) throws NoSuchUserException {
        for (String name : userMapping.keySet()) {
            if (userName.equals(name)) {
                return userMapping.get(name);
            }
        }
        throw new NoSuchUserException("the user does not exist.\n");
    }

    private void addUser(User user) {
        userMapping.put(user.getUserName(), user);
    }

    private void deleteUser(String username) {
        userMapping.remove(username);
    }

}
