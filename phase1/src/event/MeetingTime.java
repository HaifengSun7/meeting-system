package event;

/**
 * Stores meeting Times in hours and minutes, with 1 hour as length of the meeting.
 * @author Shaohong Chen
 * @version 1.0.0
 *
 */
public class MeetingTime {

    private int hour;
    private int min;
    private int length = 1;

    /**
     * Initiates the MeetingTime, with default length of 1 hour.
     * @param h the hour part of the time, in 24 hours, in int.
     * @param m the minute part of the time, rounded to nearest minute, in int.
     */
    public MeetingTime(int h, int m){
        this.hour = h;
        this.min = m;
    }

    /**
     * Get the hour of the meeting time, in 24 hours, in int.
     * @return the hour part of the time, in int.
     */
    public int getHour(){
        return this.hour;
    }

    /**
     * Get the minutes of the meeting time, in int.
     * @return the minutes part of the time, in int.
     */
    public int getMin(){
        return this.min;
    }

    /**
     * Get the length of the Event with this specific MeetingTime.
     * @return length of the Event using this time, in int.
     */
    public int getMeetingLength(){
        return length;
    }

    /**
     * Check if the time is in proper working time so that attendees can attend.
     * @return a boolean of whether the time slot is in working time.
     */
    public boolean canAddEvent(){
        if(this.hour>=9){
            if(this.hour==16 && this.min == 0){
                return true;
            }else{
                return this.hour < 16;
            }
        }
        return false;
    }

    /**
     * Check if the current time slot contradicts the time slot t
     * @param t the time slot that may contradict with current time slot or not.
     * @return a boolean that shows if this planned time slot contradicts with the other time slot.
     */
    public boolean contradicts(MeetingTime t){
        MeetingTime endTime = new MeetingTime(this.hour+length, this.min);
        MeetingTime t_endTime = new MeetingTime(t.getHour()+length, t.getMin());
        if(this.hour == t_endTime.getHour()) {
            return !(this.min >= t_endTime.getMin());
        }else if(endTime.getHour() == t.getHour()){
            return !(endTime.getMin() < t.getMin());
        }else return t_endTime.getHour() >= this.hour && t.getHour() <= endTime.getHour();
    }
}
