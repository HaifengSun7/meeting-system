package user;

import java.util.ArrayList;

public class UserManager {

    public ArrayList<User> getAllUsers() {

    }

    public ArrayList<String> getContactList(String user) {
        //TODO: Return a list of people who user can send a message to.
        //TODO: Important. We can't use entities in controllers and other Use Cases. Must change the output type.

    }

    public String logIn(String username, String password) {
        //TODO: returns a string, representing the TYPE of user. e.g. Attendee.
    }

    public ArrayList<String> getSignedEventList(String user){
        //TODO: return a list of toString of events that the user has signed in.
    }
}
