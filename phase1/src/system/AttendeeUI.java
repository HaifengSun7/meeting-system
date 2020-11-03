package system;

import event.Event;
import user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeUI{
    user.User attendee;
    public AttendeeUI(User person) {
    }

    public void run() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Name:" + attendee.toString());
        System.out.println("Attendee");
        System.out.println("[1] Schedule of events that I can sign up for.\n[2] See events that I have signed up for\n[3] Send a message\n [e] exit");
        String command = reader.nextLine();
        switch (command) {
            case "e":
                return;

            case "1":
                //TODO: Show a list of schedule which they can sign up for.
                ArrayList<Event> example_list = new ArrayList<Event>(); // Just an example
                for (int i = 0; i < example_list.size(); i++) {
                    System.out.println("[" + i + "] " + example_list.get(i).toString());
                }
                command = reader.nextLine();
        }
    }
}
