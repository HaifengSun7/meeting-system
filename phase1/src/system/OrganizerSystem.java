package system;

import event.EventManager;
import message.MessageManager;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerSystem {
    private final String organizer;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();
    public OrganizerSystem(String organizer) {
        this.organizer = organizer;
    }
    public void run() {
        while(true){
            System.out.println("Name:" + organizer.toString());
            System.out.println("Organizer");
            System.out.println("[1] see and manage rooms\n[2] create speaker account\n[3] Schedule speakers\n [4] Send a message\n [5] See messages \n[e] exit");
            String command = reader.nextLine();
            switch (command){
                case "1":
                    System.out.println("Here is a list of rooms");
                    ArrayList<String> roomList = eventmanager.getAllRooms();
                    for(int i = 0; i < roomList.size(); i++){
                        System.out.println("[" + i + "]" + roomList.get(i));
                    }
                    System.out.println("See and manage rooms: \n [a] add a new room \n [b] see schedule of a certain room\n [e] exit to main menu.");
                    String command2 = reader.nextLine();
                    switch (command2){
                        case "a":
                            System.out.println("room number?");
                            String roomnumber = reader.nextLine();
                            System.out.println("size?");
                            String size = reader.nextLine();
                            eventmanager.addRoom(Integer.parseInt(roomnumber), Integer.parseInt(size));
                            System.out.println("done. Press enter to continue.");
                            command = reader.nextLine();
                            break;
                        case "b":
                            System.out.println("room number?");
                            String roomno = reader.nextLine();
                            try{
                                ArrayList<Integer> schedule = new ArrayList<Integer>();
                                schedule = eventmanager.getSchedule(Integer.parseInt(roomno));
                                for(Integer i: schedule){
                                    System.out.println(eventmanager.findEventStr(i));
                                }
                            } catch (InvalidActivityException e) {
                                System.out.println("dumb.");
                                break;
                            }
                            System.out.println("That's everything. Press enter to continue.");
                            command = reader.nextLine();
                            break;
                        case "e":
                            break;
                        default:
                            System.out.println("Invalid input. Press enter to continue");
                            command = reader.nextLine();
                            break;
                    }
                    continue;
                case "2":
                    System.out.println("You want a promotion or a creation? \n [a] promotion \n [b] creation");
                    String command3 = reader.nextLine();
                    switch (command3){
                        case "a":
                            System.out.println("but promote who? give me their username.");
                            command3 = reader.nextLine();
                            usermanager.becomeSpeaker(command3);
                            //TODO: and double check all their task, so that no 2 speaker ended up in a same event.
                            break;
                        case "b":
                            System.out.println("username?");
                            String username = reader.nextLine();
                            System.out.println("password?");
                            String password = reader.nextLine();
                            usermanager.creatUserAccount("Speaker", username, password);
                            break;
                    }
                    continue;
                case "3":
                    System.out.println("Give me the username");
                    String name = reader.nextLine();
                    // check if they are speaker.
                    if (!usermanager.getUserType(name).equals("Speaker")) {
                        System.out.println("not a speaker.");
                        continue;
                    }
                    System.out.println("Showing all events:");
                    ArrayList<String> allevents = eventmanager.getAllEvents();
                    for(int i = 0; i < allevents.size(); i++){
                        System.out.println("[" + i + "]" + allevents.get(i));
                    }
                    System.out.println("Input event number to add. \n[r] show rooms and add new event. \n[e] to exit.");
                    command3 = reader.nextLine();
                    switch (command3){
                        default:
                            if(0 <= Integer.parseInt(command3) && Integer.parseInt(command3) <= allevents.size()){
                                eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command3));
                            }
                            break;
                            //TODO: fix UI.
                        case "r":
                            System.out.println("Here are the rooms.");
                            ArrayList<String> roomLst = eventmanager.getAllRooms();
                            for(int i = 0; i < roomLst.size(); i++){
                                System.out.println("[" + i + "]" + roomLst.get(i));
                            }
                            System.out.println("[a] to add new event \n room number to choose an event in some room \n [e] to exit");
                            String command4 = reader.nextLine();
                            switch (command4){
                                case "a":
                                    System.out.println("Room?");
                                    String room = reader.nextLine();
                                    System.out.println("StartTime");
                                    String time = reader.nextLine();
                                    System.out.println("EndTime");
                                    String command5 = reader.nextLine();
                                    //TODO: input time and add and stuff.
                                    //TODO: double check if available.
                                    System.out.println("done. Press enter to continue.");
                                    reader.nextLine();
                                    break;
                                case "e":
                                    System.out.println("bye bye. Press enter to continue.");
                                    reader.nextLine();
                                    break;
                                default:
                                    try{
                                        ArrayList<Integer> schedule = new ArrayList<Integer>();
                                        schedule = eventmanager.getSchedule(Integer.parseInt(command));
                                        for(Integer i: schedule){
                                            System.out.println(eventmanager.findEventStr(i));
                                        }
                                    } catch (InvalidActivityException e) {
                                        System.out.println("dumb.");
                                        break;
                                    }
                                    System.out.println("give me your event ID");
                                    String eventID = reader.nextLine();
                                    //TODO: add to event.
                                    System.out.println("done. Press enter to continue.");
                                    command = reader.next();
                                    break;
                            }
                            break;
                    }
                    continue;
                case "4":
                    System.out.println("To Who?");
                    ArrayList<String> msglst= usermanager.getContactList(organizer);
                    for(int i = 0; i < msglst.size(); i++){
                        System.out.println("[" + i + "] " + msglst.get(i));
                    }
                    System.out.println("[all speaker] to send to all speaker\n[all attendee] to send to all attendee\n[e] exit to main menu");
                    String command4 = reader.nextLine();
                    switch (command4){
                        case "all speaker":
                            //TODO: send to all speaker.
                            break;
                        case "all attendee":
                            //TODO: send to all attendee.
                            break;
                        default:
                            String receiver = msglst.get(Integer.parseInt(command4)); // TODO: what if input wrong?
                            System.out.println("Yo, now input your message. Hint: \\n and stuff."); // TODO: String.
                            command = reader.nextLine();
                            messagemanager.sendMessage(organizer, receiver, command);
                            System.out.println("Success! Press enter to continue");
                            reader.nextLine();
                            break;
                    }
                    continue;
                case "5":
                    ArrayList<String> inbox = messagemanager.getInbox(organizer);
                    for(int i = 0; i < inbox.size(); i++){
                        System.out.println("[" + i + "] " + inbox.get(i)+"\n");
                    }
                    System.out.println("[e] exit to main menu");
                    continue;
                case "e":
                    //TODO: Save.
                    break;
                default:
                    System.out.println("Please press the right key.");
                    continue;
            }
            break;

        }


    }
}
