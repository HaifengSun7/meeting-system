package event;

import java.util.ArrayList;
import java.util.HashMap;
import event.exceptions.*;

public class ConferenceManager {

    protected HashMap<String, Conference> map;

    protected ConferenceManager() {
        map = new HashMap<>();
    }

    protected void createConference(String name){
        Conference added = new Conference(name);
        map.put(name, added);
    }

    protected void addEvent(String conferenceName,int eventID) throws NoSuchConferenceException {
        try{
            map.get(conferenceName).addEvent(eventID);
        } catch (Exception e){
            throw new NoSuchConferenceException("Conference does not exist.");
        }
    }

    protected ArrayList<Integer> getEventOfConference(String conferenceName) throws NoSuchConferenceException {
        try{
            return map.get(conferenceName).getEvents();
        } catch (Exception e){
            throw new NoSuchConferenceException("Conference does not exist.");
        }
    }

    protected ArrayList<String> getAllConferences(){
        return new ArrayList<>(map.keySet());
    }

    protected boolean hasConference(String conferenceName){
        return map.containsKey(conferenceName);
    }

    protected String getConferenceOfEvent(int eventID){
        for(Conference c: map.values()){
            if(c.hasEvent(eventID)){
                return c.getName();
            }
        }
        return null;
    }
}
