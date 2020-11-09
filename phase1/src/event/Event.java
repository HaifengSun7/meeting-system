package event;

import com.sun.scenario.animation.shared.TimerReceiver;
import user.User;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores properties of events, with default 1 hour as length of the event. getAttendees and toString
 * @author Dechen Han, Shaohong Chen.
 * @version 1.0.3
 *
 */
//TODO: REMEMBER TO CHANGE THE AUTHOR AND REMOVE THE TODOS.
public class Event {

    private Timestamp time;
    private int length = 1;
    private int id;
    private static int eventNumber = 0;
    private ArrayList<String> user_list;
    private String speaker;
    private String description;

    /**
     * Initiates the Meeting, with its time and a default length of 1 hour.
     * @param time: The time the meeting begins.
     */
    public Event(Timestamp time){
        //TODO: Constructor, The input of time is an input made by users in string.
        //The input time needs to be in the form of 05:00, which means with length 5.
        this.time = time;
        this.id = eventNumber; //To be used in other useCases and entities.
        eventNumber += 1;
        this.user_list = new ArrayList<>();
    }

//    public ArrayList<String> getAttendees(){
//        //TODO: We need an arraylist of all attendees of this event, in usernames.
//        ArrayList<String> name_list = new ArrayList<>();
//        for (User u : this.user_list){
//            name_list.add(u.getUserName());
//        }
//        return name_list;
//    }

    public ArrayList<String> getAttendees(){
        return user_list;
    }

    public void addAttendees(String attendee) {
        this.user_list.add(attendee);
    }

    public void removeAttendees(String attendee) {
        this.user_list.remove(attendee);
    }

    public void setSpeaker(String u) {this.speaker = u; }

    public String getSpeaker() {return speaker; }

    public void setDescription(String description) {this.description = description; }

    public String getDescription() {return description; }


    public String toString(){
        String t = this.time.toString();
        return "Event{" + "Id: " + this.getId() + ", Description: " + this.description + ", Time:" + t +
                ", Attendees:" + this.getAttendees() + "}";
    }


    /**
     * Get the id of the event.
     * @return the id of event in int.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the hour of the event time, in 24 hours, in int.
     * @return the hour part of the time, in int.
     */
    public String getTime(){
        String t = this.time.toString();
        return "The meeting will begin on:"+t+".";
    }

    /**
     * Get the length of the Event.
     * @return length of the Event lasts this hour(s), in int.
     */
    public int getMeetingLength(){
        return length;
    }

    /**
     * @param n: The wanted length of the Event.
     */
    public void setMeetingLength(int n){
        this.length = n;
    }

    /**
     * Check if the time is in proper working time so that attendees can attend.
     * @return a boolean of whether the time slot is in working time.
     */
    public boolean inOfficeHour(){
        int hour = this.getHour();
        int min = this.getMin();
        if(hour >=9){
            if(hour ==16 && min == 0){
                return true;
            }else{
                return hour < 16;
            }
        }
        return false;
    }

    /**
     * Check if the event contradicts the other event in time.
     * @param start: start time, length: length add in the start time.
     * @return A boolean showing if the two events contradicts. true for contradict.
     */
    public boolean contradicts(Timestamp start, int length) {
        ArrayList<Integer> endTime = new ArrayList<>();
        endTime.add(this.getHour()+length);
        endTime.add(this.getMin());
        ArrayList<Integer> t_endTime = new ArrayList<>();
        String t = start.toString();
        ArrayList<Integer> time_list = new ArrayList<>();
        for(int i = 0; i < t.length(); i++){
            char y = ':';
            char x = t.charAt(i);
            if(x == y){
                String t1 = String.valueOf(t.charAt(i-2));
                String t2 = String.valueOf(t.charAt(i-1));
                String time = t1 + t2;
                int tt = Integer.parseInt(time);
                time_list.add(tt);
            }
        }
        int t_hour = time_list.get(0);
        int t_min = time_list.get(1);
        t_endTime.add(t_hour);
        t_endTime.add(t_min);
        if(this.getHour() == t_endTime.get(0)) {
            return !(this.getMin() >= t_endTime.get(1));
        }else if(endTime.get(0) == t_hour){
            return !(endTime.get(1) < t_min);
        }else return t_endTime.get(0) >= this.getHour() && t_hour <= endTime.get(0);
    }

    private int getHour(){
        String t = this.time.toString();
        ArrayList<Integer> time_list = new ArrayList<>();
        for(int i = 0; i < t.length(); i++){
            char y = ':';
            char x = t.charAt(i);
            if(x == y){
                String t1 = String.valueOf(t.charAt(i-2));
                String t2 = String.valueOf(t.charAt(i-1));
                String time = t1 + t2;
                int tt = Integer.parseInt(time);
                time_list.add(tt);
            }
        }
        return time_list.get(0);
    }

    private int getMin(){
        String t = this.time.toString();
        ArrayList<Integer> time_list = new ArrayList<>();
        for(int i = 0; i < t.length(); i++){
            char y = ':';
            char x = t.charAt(i);
            if(x == y){
                String t1 = String.valueOf(t.charAt(i-2));
                String t2 = String.valueOf(t.charAt(i-1));
                String time = t1 + t2;
                int tt = Integer.parseInt(time);
                time_list.add(tt);
            }
        }
        return time_list.get(1);
    }

    private int getLength(){return this.length;}

}
