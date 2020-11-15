package user;

import readWrite.EventIterator;
import readWrite.UserIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class UserManager {

    private final Map<String, User> userMapping;

    public UserManager() {
        this.userMapping = new HashMap<>();
        UserIterator userIterator = new UserIterator();
        EventIterator eventIterator = new EventIterator();
        String[] temp;
        while (userIterator.hasNext()) {
            temp = userIterator.next(); //do something
            try {
                this.createUserAccount(temp[2], temp[0], temp[1]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        UserIterator userIter = new UserIterator();
        while (userIter.hasNext()) {
            temp = userIter.next(); //do something
            for (int i = 3; i < temp.length; i++) {
                this.addContactList(temp[i], temp[0]);
            }
        }
        String[] temp2;
        int k = 0;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next(); //do something
            for (int j = 4; j < temp2.length; j++) {
                try {
                    this.addSignedEvent(String.valueOf(k), temp2[j]);
                } catch (Exception e) {
                    System.out.println("cannot add event (userManager). something went wrong.");
                }
            }
            k += 1;
        }
    }

    /**
     * Create a new user account.
     * @param usertype: User's type.
     * @param username: User's name.
     * @param password: User's password.
     * @exception InvalidUsernameException throw this exception if usernsme is not qualified.
     * @exception DuplicateUserNameException throw this exception if username has exsited.
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

    private void addUser(User user) {
        userMapping.put(user.getUserName(), user);
    }

    private void deleteUser(String username) {
        userMapping.remove(username);
    }

    /**
     * Get a new user's signed event list
     * @param username: a User's username.
     * @return signedEvnet: a ArrayList of string.
     */
    public ArrayList<String> getSignedEventList(String username) {
        return userMapping.get(username).getSignedEvent();
    }

    /**
     * Get a new user's user type
     * @param username: a User's username.
     * @return userType: a string, uesr's type.
     */
    public String getUserType(String username) {
        return userMapping.get(username).getUserType();
    }

    /**
     * Get a new user's contact list
     * @param username: a User's username.
     * @return contactList: a ArrayList of string.
     */
    public ArrayList<String> getContactList(String username) {
        return userMapping.get(username).getContactList();
    }

    /**
     * Get a new user's status
     * @param username: a User's username.
     * @return boolean: User's status.
     */
    public boolean getStatus(String username) {
        return userMapping.get(username).getStatus();
    }

    /**
     * Get a new user's password
     * @param username: a User's username.
     * @return password: User's password.
     */
    public String getPassword(String username) {
        return userMapping.get(username).password;
    }

    /**
     * Log in a user, change the status of a user if he login successfully.
     * @param username: a User's username.
     * @param password: a User's password.
     * @exception Exception, throw it when log in failed.
     */
    public String logIn(String username, String password) throws Exception {
        User user = userMapping.get(username);
        if (user.password.equals(password)) {
            user.setStatus(true);
            return user.getUserType();
        } else {
            throw new Exception();
        }
    }

    /**
     * Log out a user, change the status of a user if he logout successfully.
     *
     * @param username: a User's username.
     * @exception Exception, throw it when log out failed.
     */
    public void logout(String username) {
        try {
            User user = findUser(username);
            user.setStatus(false);
        } catch (Exception e) {
            System.out.println("The user doesn't exist.\n");
        }
    }

    /**
     * make attendee become Speaker
     *
     * @param attendeeName Attendee username in String.
     * @return a list of String which is the signed evnet list of this attendee.
     * @throws Exception throw an exception when necessary.
     */

    public ArrayList<String> becomeSpeaker(String attendeeName) throws Exception {
        if (!userMapping.containsKey(attendeeName)) {
            throw new NoSuchUserException("NoSuchUser: " + attendeeName);
        } else {
            User attendee = userMapping.get(attendeeName);
            ArrayList<String> contactlist = attendee.getContactList();
            ArrayList<String> signedEvent = attendee.getSignedEvent();
            boolean status = attendee.getStatus();
            deleteUser(attendeeName);
            createUserAccount("Speaker", attendee.getUserName(), attendee.getPassword());
            User speaker = userMapping.get(attendeeName);
            speaker.setContactList(contactlist);
            speaker.setStatus(status);
            speaker.setSignedEvent(signedEvent);
            return signedEvent; //TODO:Is this necessary?
        }
    }

    /**
     * get all usernames
     *
     * @return a collection of all username.
     */
    public Collection<String> getAllUsernames() {
        return userMapping.keySet();
    }

    /**
     * get all speakers!
     *
     * @return a Arraylist of all Speaker's name.
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
     * @return a Arraylist of all Attendee's name.
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
     * @param eventId: a event's id.
     * @param username: a User's username.
     */
    public void deleteSignedEvent(String eventId, String username) {
        ArrayList<String> lst = userMapping.get(username).getSignedEvent();
        lst.remove(eventId);
        userMapping.get(username).setSignedEvent(lst);
    }

    /**
     * Add a new contact to a user's contect list.
     *
     * @param contactName: another user's username.
     * @param username: a User's username.
     */
    public void addContactList(String contactName, String username) {
        ArrayList<String> lst = userMapping.get(username).getContactList();
        if (!(lst.contains(username))) {
            lst.add(contactName);
        }
        userMapping.get(username).setContactList(lst);
    }

    /**
     * Delete a exist contact from a user's contect list.
     *
     * @param contactName: another user's username.
     * @param username: a User's username.
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
}
