package event;

public class EventManager {

//TODO: For Room system, you need to check if the time are available here, then add to Room with Event ID.
//TODO: We are doing like this because entities can't use each other. Only the corresponding UseCases can use Entities.





    public String findEventStr(Integer id){
        /**
         *Returns a Event based on its id, but with toString();
         */
        //TODO: implement this. Haifeng.
    }

    public ArrayList<Attendee> getAttendees(Event event){
        //TODO: get the list of attendees that have signed up a particular event
    }
}
