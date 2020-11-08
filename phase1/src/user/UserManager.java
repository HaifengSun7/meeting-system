package user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.lang.Exception;


public class UserManager {
    private Map<String, User> userMapping;

    public void createUserAccount(String usertype, String username, String password) throws Exception{
        if (userMapping.containsKey(username)) {
            throw new Exception(); //TODO: new exception!!!!!!!!!!!!!!!!!!!!!!!!!!
        } else {
            if (usertype.equals("Speaker")) {
                Speaker newuser = new Speaker(username, password);
                addUser(newuser);
            } else if (usertype.equals("Organizer")){
                Organizer newuser = new Organizer(username, password);
                addUser(newuser);
            } else {
                Attendee newuser = new Attendee(username, password);
                addUser(newuser);
            }
        }
    }

    private void addUser(User user){
        userMapping.put(user.getUserName(), user);
    }

    private void deleteUser(String username){
        userMapping.remove(username);
    }

    public ArrayList<String> getSignedEventList(String username){
        return userMapping.get(username).getSignedEvent();
    }

    public String getUserType(String username){
        return userMapping.get(username).getUserType();
    }

    public ArrayList<String> getContactList(String username){
        return userMapping.get(username).getContactList();
    }

    public boolean getStatus(String username){
        return userMapping.get(username).getStatus();
    }

    public String logIn(String username, String password) throws Exception {
        User user = userMapping.get(username);
        if (user.password.equals(password)){
            user.setStatus(true);
            return user.getUserType();
        } else {
            throw new Exception();
        }
    }

    public void logout(User user){
        user.setStatus(false);
    }

    /**
     * make attendee become Speaker
     * @param attendeeName Attendee but with String.
     * @exception Exception throw an exception when necessary.
     */

    public void becomeSpeaker(String attendeeName) throws Exception{
        User attendee = userMapping.get(attendeeName); //If we cannot find such attendee, throw exception //TODO: new exception!!!!!!!!!!!!!!!!!!!!!!!!!!
        ArrayList<String> contactlist = attendee.getContactList();
        ArrayList<String> signedEvent = attendee.getSignedEvent();
        boolean status =  attendee.getStatus();
        deleteUser(attendeeName);
        createUserAccount("speacker", attendee.getUserName(), attendee.getPassword());
        //TODO: It is possible to throw a new exception!!!!!!!!!!!!!!!!!!!!!!!!!!
        User speaker = userMapping.get(attendeeName);
        speaker.setContactList(contactlist);
        speaker.setStatus(status);
        speaker.setSignedEvent(signedEvent);
    }

    public Collection<String> getAllUsernames() {
        return userMapping.keySet();
    }

    public Map<String, User> getUserMapping() {
        return userMapping;
    }

    /**
     *
     * @return a list of speakers
     */
    public ArrayList<String> getSpeakers() {
        ArrayList<String> speaker = new ArrayList<>();
        for (String username: userMapping.keySet()){
            if (userMapping.get(username).getUserType().equals("Speaker")){
                speaker.add(username);
            }
        }
        return speaker;
    }

    /**
     *
     * @return a list of Attendees
     */
    public ArrayList<String> getAttendees() {
        ArrayList<String> attendee = new ArrayList<>();
        for (String username: userMapping.keySet()){
            if (userMapping.get(username).getUserType().equals("Attendee")){
                attendee.add(username);
            }
        }
        return attendee;
    }
}
