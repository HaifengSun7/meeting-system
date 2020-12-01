package event;

import java.util.ArrayList;

public class Conference {

    private final String name;
    private final ArrayList<Integer> events;
    public Conference(String conferenceName){
        this.name = conferenceName;
        this.events = new ArrayList<>();
    }

    protected void addEvent(int eventID){
        this.events.add(eventID);
    }

    protected ArrayList<Integer> getEvents(){
        return this.events;
    }

    protected boolean hasEvent(int eventID){
        return events.contains(eventID);
    }

    protected String getName(){
        return name;
    }

}
