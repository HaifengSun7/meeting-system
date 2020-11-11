package system;

import ReadWrite.MessageManagerInitializer;
import ReadWrite.Write;
import event.EventManager;
import message.MessageManager;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class OrganizerSystem implements SeeMessages, SendMessageToSomeone, SendMessageToAll{
    private final String organizer;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();
    String command; //command buffer
    String roomnumber;//roomnumber buffer
    String size;//size buffer
    String time1;
    String duration;
    String room;
    String receiver;

    public OrganizerSystem(String organizer) {
        this.organizer = organizer;
    }

    public void run() {
        MessageManagerInitializer messageManagerInitializer = new MessageManagerInitializer();
        messagemanager = messageManagerInitializer.run();
        while(true){
            System.out.println("Name:" + organizer.toString());
            System.out.println("Organizer");
            System.out.println("[1] see and manage rooms\n" +
                    "[2] create speaker account\n" +
                    "[3] Schedule speakers\n" +
                    "[4] Send message to a particular person\n" +
                    "[5] Send message to all speakers\n" +
                    "[6] Send message to all attendees\n" +
                    "[7] See messages\n" +
                    "[e] exit");
            command = reader.nextLine();
            switch (command){
                case "1":
                    System.out.println("Here is a list of rooms");
                    ArrayList<String> roomList = eventmanager.getAllRooms();
                    for(int i = 0; i < roomList.size(); i++){
                        System.out.println(roomList.get(i));
                    }
                    System.out.println("See and manage rooms:\n" +
                            "[a] add a new room\n" +
                            "[b] see schedule of a certain room\n" +
                            "[e] exit to main menu.");
                    command = reader.nextLine();
                    switch (command){
                        case "a":
                            System.out.println("room number?");
                            roomnumber = reader.nextLine();
                            System.out.println("size?");
                            size = reader.nextLine();
                            eventmanager.addRoom(Integer.parseInt(roomnumber), Integer.parseInt(size));
                            System.out.println("done. Press enter to continue.");
                            command = reader.nextLine();
                            break;
                        case "b":
                            System.out.println("room number?");
                            roomnumber = reader.nextLine();
                            try{
                                ArrayList<Integer> schedule = new ArrayList<Integer>();
                                schedule = eventmanager.getSchedule(Integer.parseInt(roomnumber));
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
                    System.out.println("You want a promotion or a creation?\n " +
                            "[a] promotion\n " +
                            "[b] creation");
                    command = reader.nextLine();
                    switch (command){
                        case "a":
                            System.out.println("but promote who? give me his or her username.");
                            String name = reader.nextLine();
                            try {
                                usermanager.becomeSpeaker(name);
                            } catch (Exception e) {
                                System.out.println("userManager doesn't want you to be speaker");
                                break;
                            }
                            try {
                                eventmanager.becomeSpeaker(name);
                            } catch (Exception e) {
                                System.out.println("eventManager doesn't want you to be speaker");
                                break;
                            }
                            break;
                        case "b":
                            System.out.println("username?");
                            String username = reader.nextLine();
                            System.out.println("password?");
                            String password = reader.nextLine();
                            try {
                                usermanager.createUserAccount("Speaker", username, password);
                            } catch (Exception e) {
                                System.out.println("give me a usable name you dumb dumb.");
                                break;
                            }
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
                    System.out.println("Input event number to add.\n" +
                            "[r] show rooms and add new event.\n" +
                            "[e] to exit.");
                    command = reader.nextLine();
                    switch (command){
                        default:
                            if(0 <= Integer.parseInt(command) && Integer.parseInt(command) <= allevents.size()){
                                try {
                                    eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command));
                                } catch (Exception e) {
                                    System.out.println("Cannot add speaker to the event");
                                }
                            }
                            break;
                            //TODO: fix UI.
                        case "r":
                            System.out.println("Here are the rooms.");
                            ArrayList<String> roomLst = eventmanager.getAllRooms();
                            for(int i = 0; i < roomLst.size(); i++){
                                System.out.println("[" + i + "]" + roomLst.get(i));
                            }
                            System.out.println("[a] to add new event\n" +
                                    "room number to choose an event in some room\n" +
                                    "[e] to exit");
                            String command4 = reader.nextLine();
                            switch (command4){
                                case "a":
                                    System.out.println("Room?");
                                    room = reader.nextLine();
                                    System.out.println("StartTime. Format: yyyy-m[m]-d[d] hh:mm:ss[.f…]");
                                    time1 = reader.nextLine();
                                    System.out.println("Duration");
                                    duration = reader.nextLine();
                                    try {
                                        eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration));
                                    } catch (Exception e) {
                                        System.out.println("Sorry, cannot add event!");
                                        break;
                                    }
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
                                        System.out.println("Sorry, cannot get event schedule");
                                        break;
                                    }
                                    System.out.println("give me your room number");
                                    time1 = reader.nextLine();
                                    System.out.println("Duration");
                                    duration = reader.nextLine();
                                    try {
                                        eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration));
                                    } catch (Exception e) {
                                        System.out.println("Sorry, cannot add event");
                                        break;
                                    }
                                    System.out.println("done. Press enter to continue.");
                                    command = reader.next();
                                    continue;
                            }
                            break;
                        case "e":
                            System.out.println("Press enter to exit to main menu");
                            reader.nextLine();
                            continue;
                    }
                    continue;
                case "4":
                    sendMessageToSomeone(organizer);
                    reader.nextLine();
                    continue;
                case "5":
                    sendMessageToAll(organizer, "speaker");
                    reader.nextLine();
                    continue;
                case "6":
                    sendMessageToAll(organizer, "attendee");
                    reader.nextLine();
                    continue;
                case "7":
                    seeMessages(organizer);
                    reader.nextLine();
                    continue;
                case "e":
                    break;
                default:
                    System.out.println("Please press the right key.");
                    continue;
            }
            break;

        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        //write.run();
    }

    @Override
    public void seeMessages(String organizer) {
        ArrayList<String> inbox = messagemanager.getInbox(organizer);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i)+"\n");
        }
        System.out.println("Press enter to exit to main menu");
    }

    // Send messages to all speakers or all attendees
    @Override
    public void sendMessageToAll(String organizer, String object) {
        switch (object) {
            case "speaker":
                ArrayList<String> speakers = usermanager.getSpeakers();
                System.out.println("Now input your message. Hint: \\n and stuff.");
                String message = reader.nextLine();
                messagemanager.sendToList(organizer, speakers, message);
                System.out.println("Success! Press enter to continue");
                break;
            case "attendee":
                ArrayList<String> attendees = usermanager.getAttendees();
                System.out.println("Now input your message. Hint: \\n and stuff.");
                String message2 = reader.nextLine();
                messagemanager.sendToList(organizer, attendees, message2);
                System.out.println("Success! Press enter to continue");
                break;
        }

    }

    //Send message to a particular person
    @Override
    public void sendMessageToSomeone(String organizer) {
        System.out.println("Which single person you want to send message?");
        ArrayList<String> contactList= usermanager.getContactList(organizer);
        for(int i = 0; i < contactList.size(); i++){
            System.out.println("[" + i + "] " + contactList.get(i));
        }
        System.out.println("[e] exit to main menu");
        String receive = reader.nextLine();
        if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
            String receiver = contactList.get(Integer.parseInt(receive));
            System.out.println("Now input your message. Hint: \\n and stuff.");
            String message = reader.nextLine();
            messagemanager.sendMessage(organizer, receiver, message);
            System.out.println("Success! Press enter to continue");
        } else {
            System.out.println("Press enter to exit to main menu");
        }
    }
}
