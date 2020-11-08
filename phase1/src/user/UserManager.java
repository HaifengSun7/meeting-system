package user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserManager {
    public Map<String, User> userMapping;

    public void creatUserAccount(String userType, String username, String password){
        if (userMapping.containsKey(username)) {
            System.out.println("This username has been used! Try another one!"); //TODO: throw. exception. don't println. You are not system.
        } else {
            if (userType.equals("Speaker")) {
                Speaker newuser = new Speaker(username, password);
                addUser(newuser);
            } else if (userType.equals("Organizer")){
                Organizer newuser = new Organizer(username, password);
                addUser(newuser);
            } else {
                Attendee newuser = new Attendee(username, password);
                addUser(newuser);
            }
        }
    }

    private void addUser(User user){
        userMapping.put(user.username, user);
    }

    public Collection<String> getAllUsers(){
        return userMapping.keySet();
    }

    public ArrayList<String> getSignedEventList(String username){
        return userMapping.get(username).getSignedEvent();
    }

    public String getUserType(String username){
        return userMapping.get(username).usertype;
    }

    public ArrayList<String> getContactList(String username){
        return userMapping.get(username).contactList;
    }

    public boolean getStatus(String username){
        return userMapping.get(username).getStatus();
    }

    public String logIn(String username, String password) {
        User user = userMapping.get(username);
        if (user.password.equals(password)){
            user.status = true;
            return user.usertype;
        } else {
            return "Login failed!"; //TODO: Please throw an exception. PLEASE. I won't write a if condition specifically for "Login failed!"
        }
    }

    public void logout(User user){
        user.status = false;
    }

    /**
     * make attendee become Speaker
     * @param attendee Attendee but with String.
     * @exception Exception throw an exception when necessary.
     */
    public void becomeSpeaker(String attendee) {
        //TODO: Remember to update their events or whatever.
    }
}
