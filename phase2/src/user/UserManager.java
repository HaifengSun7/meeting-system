package user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The manager of users. Arrange users with all the actions related to them.
 */
public class UserManager {

    private final Map<String, User> userMapping;

    /**
     * Initializes the user manager, reads from database.db.
     */
    public UserManager() {
        this.userMapping = new HashMap<>();
    }

    /**
     * Create a new user account.
     *
     * @param usertype: User's type in string.
     * @param username: User's name in string.
     * @param password: User's password in string.
     * @throws InvalidUsernameException   throw this exception if username is not qualified.
     * @throws DuplicateUserNameException throw this exception if username has existed.
     */
    public void createUserAccount(String usertype, String username, String password) throws InvalidUsernameException, DuplicateUserNameException {
        if (username.contains(",")) {
            throw new InvalidUsernameException("no comma allowed in username");
        }
        if (userMapping.containsKey(username)) {
            throw new DuplicateUserNameException("Duplicate User Name : " + username);
        } else {
            switch (usertype) {
                case "Speaker": {
                    Speaker newUser = new Speaker(username, password);
                    addUser(newUser);
                    break;
                }
                case "Organizer": {
                    Organizer newUser = new Organizer(username, password);
                    addUser(newUser);
                    break;
                }
                case "VIP": {
                    VIP newUser = new VIP(username, password);
                    addUser(newUser);
                    break;
                }
                case "Attendee": {
                    Attendee newUser = new Attendee(username, password);
                    addUser(newUser);
                    break;
                }
            }
        }
    }

    /**
     * Get a new user's signed event list
     *
     * @param username: a User's username in string.
     * @return a ArrayList of signed events in string.
     */
    public ArrayList<String> getSignedEventList(String username) {
        return userMapping.get(username).getSignedEvent();
    }

    /**
     * Get a new user's user type
     *
     * @param username: a User's username in string.
     * @return userType: a string, user's type in string.
     */
    public String getUserType(String username) {
        try {
            if (isVIP(username)) {
                return "VIP";
            }
        } catch (NotAttendeeException | NoSuchUserException e) {
            //
        }
        return userMapping.get(username).getUserType();
    }

    /**
     * Get a new user's contact list
     *
     * @param username: a User's username in string.
     * @return a ArrayList of contact list in string.
     */
    public ArrayList<String> getContactList(String username) {
        return userMapping.get(username).getContactList();
    }

    /**
     * Get a new user's password
     *
     * @param username: a User's username in string.
     * @return password: User's password in string.
     */
    public String getPassword(String username) {
        return userMapping.get(username).password;
    }

    public boolean isVIP(String username) throws NotAttendeeException, NoSuchUserException {
        if (!findUser(username).getUserType().equals("Attendee")) {
            throw new NotAttendeeException("this is not a valid attendee");
        } else {
            Attendee attendee = (Attendee) userMapping.get(username);
            return attendee.getVIPstatus();
        }
    }

    /**
     * Log in a user, change the status of a user if he login successfully.
     *
     * @param username: a User's username in string.
     * @param password: a User's password in string.
     * @return the user's type
     * @throws WrongLogInException when log in failed.
     */
    public String logIn(String username, String password) throws WrongLogInException {
        try {
            User user = userMapping.get(username);

            if (user.password.equals(password)) {
                user.setStatus(true);
                return user.getUserType();
            } else {
                throw new WrongLogInException("Wrong Username or Password");
            }
        } catch (NullPointerException e) {
            throw new WrongLogInException("Wrong Username or Password");
        }
    }

    /**
     * Log out a user, change the status of a user if he logout successfully.
     *
     * @param username: a User's username in string.
     */
    public void logout(String username) {
        try {
            User user = findUser(username);
            user.setStatus(false);
        } catch (NoSuchUserException ignored) {
        }
    }

    /**
     * make attendee become Speaker
     *
     * @param attendeeName Attendee username in String.
     * @throws NoSuchUserException        throw an exception when necessary.
     * @throws InvalidUsernameException   throw an exception when necessary.
     * @throws DuplicateUserNameException throw an exception when necessary.
     */
    public void becomeSpeaker(String attendeeName) throws NoSuchUserException, InvalidUsernameException, DuplicateUserNameException {
        if (!userMapping.containsKey(attendeeName)) {
            throw new NoSuchUserException("This user does not exist: " + attendeeName);
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
        }
    }

    /**
     * Make an attendee a VIP.
     *
     * @param attendeeName the attendee that we are going to promote.
     * @throws NoSuchUserException When the input attendee Name is not a valid username.
     */
    public void becomeVIP(String attendeeName) throws NoSuchUserException {
        if (!userMapping.containsKey(attendeeName)) {
            throw new NoSuchUserException("User " + attendeeName + " does not exist.");
        } else {
            User attendee = userMapping.get(attendeeName);
            ArrayList<String> contactList = attendee.getContactList();
            ArrayList<String> signedEvent = attendee.getSignedEvent();
            boolean status = attendee.getStatus();
            deleteUser(attendeeName);
            try {
                createUserAccount("VIP", attendee.getUserName(), attendee.getPassword());
            } catch (InvalidUsernameException | DuplicateUserNameException ignored) {
            }
            User attendeeVIP = userMapping.get(attendeeName);
            attendeeVIP.setContactList(contactList);
            attendeeVIP.setStatus(status);
            attendeeVIP.setSignedEvent(signedEvent);
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
     * @param eventId:  a event's id.
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
     * @param eventId:  a event's id in string.
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
     * @param username:    a User's username in string.
     */
    public void addContactList(String contactName, String username) {
        ArrayList<String> lst = userMapping.get(username).getContactList();
        if (!(lst.contains(contactName))) {
            lst.add(contactName);
        }
        userMapping.get(username).setContactList(lst);
    }

    /**
     * Delete a exist contact from a user's contact list.
     *
     * @param contactName: another user's username in string.
     * @param username:    a User's username in string.
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
