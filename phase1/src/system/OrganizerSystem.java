package system;

import readWrite.Write;
import event.DuplicateRoomNoException;
import event.EventManager;
import message.MessageManager;
import user.UserManager;
import presenter.*;

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
    String roomNumber;//roomNumber buffer
    String size;//size buffer
    String time1;
    String duration;
    String room;

    public OrganizerSystem(String organizer) {
        this.organizer = organizer;
    }

    public void run() {
        while(true){
            System.out.println(Presenter.name() + organizer);
            System.out.println(Presenter.user("Organizer"));
            System.out.println(Presenter.organizerMenu());
            command = reader.nextLine();
            switch (command){
                case "1":
                    manageRooms();
                    continue;
                case "2":
                    createSpeaker();
                    continue;
                case "3":
                    scheduleSpeakers();
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
                    usermanager.logout(organizer);
                    break;
                default:
                    System.out.println(Presenter.wrongKeyRemainderInMenu());
                    continue;
            }
            break;

        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    @Override
    public void seeMessages(String organizer) {
        ArrayList<String> inbox = messagemanager.getInbox(organizer);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i)+"\n");
        }
        System.out.println(Presenter.pressEnterToMainMenu());
    }

    // Send messages to all speakers or all attendees
    @Override
    public void sendMessageToAll(String organizer, String object) {
        switch (object) {
            case "speaker":
                ArrayList<String> speakers = usermanager.getSpeakers();
                System.out.println(Presenter.inputPrompt("message"));
                String message = reader.nextLine();
                messagemanager.sendToList(organizer, speakers, message);
                System.out.println(Presenter.successPressEnter());
                break;
            case "attendee":
                ArrayList<String> attendees = usermanager.getAttendees();
                System.out.println(Presenter.inputPrompt("message"));
                String message2 = reader.nextLine();
                messagemanager.sendToList(organizer, attendees, message2);
                System.out.println(Presenter.successPressEnter());
                break;
        }

    }

    //Send message to a particular person
    @Override
    public void sendMessageToSomeone(String organizer) {
        System.out.println(Presenter.inputPrompt("username"));
        System.out.println(Presenter.pressEtoMainMenu());
        String target = reader.nextLine();
        if("e".equals(target)){
            System.out.println(Presenter.pressEnterToMainMenu());
        } else {
            if(usermanager.getAllUsernames().contains(target)){
                System.out.println(Presenter.inputPrompt("message"));
                String msg = reader.nextLine();
                messagemanager.sendMessage(organizer, target, msg);
                System.out.println(Presenter.success());
            } else {
                System.out.println(Presenter.invalid("username"));
            }
        }
    }

    private void manageRooms(){
        System.out.println(Presenter.titlesInSpeaker("manageRooms"));
        ArrayList<String> roomList = eventmanager.getAllRooms();
        for (String s : roomList) {
            System.out.println(s);
        }
        System.out.println(Presenter.menusInSpeaker("manageRooms"));
        command = reader.nextLine();
        switch (command){
            case "a":
                addNewRoom();
                System.out.println(Presenter.successPressEnter());
                command = reader.nextLine();
                break;
            case "b":
                checkRoom();
                command = reader.nextLine();
                break;
            case "c":
                System.out.println(Presenter.inputPrompt("roomNumber"));
                room = reader.nextLine();
                System.out.println(Presenter.inputPrompt("startTime"));
                time1 = reader.nextLine();
                System.out.println(Presenter.inputPrompt("duration"));
                duration = reader.nextLine();
                try {
                    eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration));
                    System.out.println(Presenter.successPressEnter());
                } catch (Exception e) {
                    System.out.println(Presenter.invalid("addEvent"));
                        }
                reader.nextLine();
                break;
            case "e":
                break;
            default:
                System.out.println(Presenter.wrongKeyRemainderInMenu() + Presenter.pressEnterToMainMenu());
                command = reader.nextLine();
                break;
        }
    }

    private void addNewRoom(){
        System.out.println(Presenter.inputPrompt("newRoomNumber"));
        roomNumber = reader.nextLine();
        System.out.println(Presenter.inputPrompt("roomSize"));
        size = reader.nextLine();
        try {
            eventmanager.addRoom(Integer.parseInt(roomNumber), Integer.parseInt(size));
        } catch (DuplicateRoomNoException e) {
            System.out.println(Presenter.duplicateInvalid("newRoom"));
        }
    }

    private void checkRoom(){
        System.out.println(Presenter.inputPrompt("roomNumber"));
        roomNumber = reader.nextLine();
        try{
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for(Integer i: schedule){
                System.out.println(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            System.out.println(Presenter.invalid("roomNumber") + Presenter.pressEnterToMainMenu());
            return;
        }
        System.out.println(Presenter.titlesInSpeaker("checkRoom") + Presenter.pressEnterToMainMenu());
    }

    private void createSpeaker(){
        System.out.println(Presenter.menusInSpeaker("createSpeaker"));
        command = reader.nextLine();
        switch (command){
            case "a":
                promoteExistingSpeaker();
                break;
            case "b":
                System.out.println(Presenter.inputPrompt("newUsername"));
                String username = reader.nextLine();
                System.out.println(Presenter.inputPrompt("password"));
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("Speaker", username, password);
                    System.out.println(Presenter.success());
                } catch (Exception e) {
                    System.out.println(Presenter.duplicateInvalid("username"));
                    break;
                }
                break;
        }
    }

    private void promoteExistingSpeaker(){
        System.out.println(Presenter.titlesInSpeaker("promoteExistingSpeaker"));
        String name = reader.nextLine();
        try {
            usermanager.becomeSpeaker(name);
        } catch (Exception e) {
            System.out.println(Presenter.invalid("username") + Presenter.pressEnterToMainMenu());
            return;
        }
        eventmanager.becomeSpeaker(name);
    }

    private void scheduleSpeakers(){
        System.out.println(Presenter.inputPrompt("speakerName"));
        String name = reader.nextLine();
        try {
            if (!usermanager.getUserType(name).equals("Speaker")) {
                System.out.println(Presenter.notASpeaker());
                return;
            }
        } catch (Exception e) {
            System.out.println(Presenter.invalid("username"));
            return;
        }
        System.out.println(Presenter.titlesInSpeaker("scheduleSpeakers1"));
        ArrayList<String> allEvents = eventmanager.getAllEvents();
        for(int i = 0; i < allEvents.size(); i++){
            System.out.println("[" + i + "]" + allEvents.get(i));
        }
        System.out.println(Presenter.menusInSpeaker("scheduleSpeakers1") +
                Presenter.pressEtoMainMenu());
        command = reader.nextLine();
        switch (command){
            default:
                addSpeakerToEvent(allEvents, name);
                break;
            case "r":
                System.out.println(Presenter.titlesInSpeaker("scheduleSpeakers2"));
                ArrayList<String> roomLst = eventmanager.getAllRooms();
                for (String s : roomLst) {
                    System.out.println(s);
                }
                System.out.println(Presenter.menusInSpeaker("scheduleSpeakers2") +
                        Presenter.pressEtoMainMenu());
                String command4 = reader.nextLine();
                switch (command4){
                    case "a":
                        System.out.println(Presenter.inputPrompt("roomNumber"));
                        room = reader.nextLine();
                        System.out.println(Presenter.inputPrompt("startTime"));
                        time1 = reader.nextLine();
                        System.out.println(Presenter.inputPrompt("duration"));
                        duration = reader.nextLine();
                        try {
                            eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration));
                            System.out.println(Presenter.successPressEnter());
                        } catch (Exception e) {
                            System.out.println(Presenter.invalid("addEvent"));
                            break;
                        }
                        reader.nextLine();
                        break;
                    case "e":
                        System.out.println(Presenter.exitToMainMenu());
                        break;
                    default:
                        try{
                            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(command4));
                            for(Integer i: schedule){
                                System.out.println(eventmanager.findEventStr(i));
                            }
                        } catch (InvalidActivityException e) {
                            System.out.println(Presenter.invalid("getEventSchedule"));
                            break;
                        }
                        System.out.println(Presenter.inputPrompt("roomNumber"));
                        room = reader.nextLine();
                        System.out.println(Presenter.inputPrompt("startTime"));
                        time1 = reader.nextLine();
                        System.out.println(Presenter.inputPrompt("duration"));
                        duration = reader.nextLine();
                        try {
                            eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration));
                        } catch (Exception e) {
                            System.out.println(Presenter.invalid("addEvent"));
                            break;
                        }
                        System.out.println(Presenter.success());
                        break;
                }
                break;
            case "e":
                System.out.println(Presenter.pressEnterToMainMenu());
                break;
        }
    }

    private void addSpeakerToEvent(ArrayList<String> allEvents, String name){
        if(0 <= Integer.parseInt(command) && Integer.parseInt(command) < allEvents.size()){
            try {
                eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command));
                System.out.println(Presenter.success());
            } catch (Exception e) {
                System.out.println(Presenter.invalid("addSpeaker"));
            }
        } else {
            System.out.println(Presenter.invalid("eventId"));
        }
    }
}
