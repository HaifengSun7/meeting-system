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

/**
 * <h1>Organizer System</h1>
 * The OrganizerSystem program implements the system of Organizer user.
 * @author Haifeng Sun, Wei Tao
 * @version 1.0.0
 */
public class OrganizerSystem{

    private final String organizer;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public OrganizerSystem(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Run the Organizer System. Print out organizer's menu, and perform organizer's operations
     */
    public void run() {
        String command;
        while(true){
            Presenter.name(organizer);
            Presenter.userType("Organizer");
            Presenter.organizerMenu();
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
                    sendMessageToSomeone();
                    continue;
                case "5":
                    sendMessageToAll("speaker");
                    continue;
                case "6":
                    sendMessageToAll("attendee");
                    continue;
                case "7":
                    seeMessages();
                    continue;
                case "e":
                    usermanager.logout(organizer);
                    break;
                default:
                    Presenter.wrongKeyReminder();
                    Presenter.invalid("");
                    Presenter.continuePrompt();
                    reader.nextLine();
                    continue;
            }
            break;

        }
        Write write = new Write(usermanager, eventmanager, messagemanager);
        write.run();
    }

    /**
     * See the messages that the organizer got from other users
     */
    private void seeMessages() {
        ArrayList<String> inbox = messagemanager.getInbox(organizer);
        for(int i = 0; i < inbox.size(); i++){
            System.out.println("[" + i + "] " + inbox.get(i)+"\n");
        }
        Presenter.exitToMainMenuPrompt();
        reader.nextLine();
    }

    /**
     * Send messages to all users, either all speakers or all attendees
     * @param user user, either the String "speaker" or "attendee"
     */
    private void sendMessageToAll(String user) {
        switch (user) {
            case "speaker":
                ArrayList<String> speakers = usermanager.getSpeakers();
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendToList(organizer, speakers, message);
                Presenter.continuePrompt();
                break;
            case "attendee":
                ArrayList<String> attendees = usermanager.getAttendees();
                Presenter.inputPrompt("message");
                String message2 = reader.nextLine();
                messagemanager.sendToList(organizer, attendees, message2);
                Presenter.continuePrompt();
                break;
        }

    }

    /**
     * Send message to a specific person
     */
    private void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        Presenter.exitToMainMenuPrompt();
        String target = reader.nextLine();
        if("e".equals(target)){
            Presenter.exitingToMainMenu();
        } else {
            if(usermanager.getAllUsernames().contains(target)){
                Presenter.inputPrompt("message");
                String msg = reader.nextLine();
                messagemanager.sendMessage(organizer, target, msg);
                Presenter.success();
            } else {
                Presenter.invalid("username");
            }
        }
    }

    /**
     * Manage the rooms by creating a new room, or checking the existing rooms, or creating a new event
     * in a specific room
     */
    private void manageRooms(){
        Presenter.titlesInSpeaker("manageRooms");
        ArrayList<String> roomList = eventmanager.getAllRooms();
        for (String s : roomList) {
            Presenter.defaultPrint(s);
        }
        Presenter.menusInSpeaker("manageRooms");
        String command = reader.nextLine();
        switch (command){
            case "a":
                addNewRoom();
                Presenter.continuePrompt();
                reader.nextLine();
                break;
            case "b":
                checkRoom();
                reader.nextLine();
                break;
            case "c":
                Presenter.inputPrompt("roomNumber");
                String room = reader.nextLine();
                Presenter.inputPrompt("startTime");
                String time1 = reader.nextLine();
                Presenter.inputPrompt("duration");
                String duration = reader.nextLine();
                Presenter.inputPrompt("description");
                String description = reader.nextLine();
                try {
                    eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration), description);
                    Presenter.continuePrompt();
                } catch (Exception e) {
                    Presenter.invalid("addEvent");
                        }
                reader.nextLine();
                break;
            case "e":
                break;
            default:
                Presenter.wrongKeyReminder();
                Presenter.continuePrompt();
                reader.nextLine();
                break;
        }
    }

    /**
     * Create a new room
     */
    private void addNewRoom(){
        Presenter.inputPrompt("newRoomNumber");
        String roomNumber = reader.nextLine();
        Presenter.inputPrompt("roomSize");
        String size = reader.nextLine();
        try {
            eventmanager.addRoom(Integer.parseInt(roomNumber), Integer.parseInt(size));
            Presenter.success();
        } catch (DuplicateRoomNoException e) {
            Presenter.duplicateInvalid("newRoom");
        }
    }

    /**
     * Check all scheduled events in a specific room
     */
    private void checkRoom(){
        Presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try{
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for(Integer i: schedule){
                System.out.println(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            Presenter.invalid("roomNumber");
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        Presenter.titlesInSpeaker("checkRoom");
        Presenter.continuePrompt();
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Create a new user to be the speaker
     */
    private void createSpeaker(){
        Presenter.menusInSpeaker("createSpeaker");
        String command = reader.nextLine();
        switch (command){
            case "a":
                promoteExistingSpeaker();
                break;
            case "b":
                Presenter.inputPrompt("newUsername");
                String username = reader.nextLine();
                Presenter.inputPrompt("password");
                String password = reader.nextLine();
                try {
                    usermanager.createUserAccount("Speaker", username, password);
                    Presenter.success();
                } catch (Exception e) {
                    Presenter.duplicateInvalid("username");
                    break;
                }
                break;
        }
    }

    /**
     * Promote a user to be a speaker
     */
    private void promoteExistingSpeaker(){
        Presenter.titlesInSpeaker("promoteExistingSpeaker");
        String name = reader.nextLine();
        try {
            usermanager.becomeSpeaker(name);
        } catch (Exception e) {
            Presenter.invalid("username");
            Presenter.continuePrompt();
            reader.nextLine();
            return;
        }
        eventmanager.becomeSpeaker(name);
    }

    /**
     * Schedule a speaker to an existing event or to a new event
     */
    private void scheduleSpeakers(){
        Presenter.inputPrompt("speakerName");
        String name = reader.nextLine();
        try {
            if (!usermanager.getUserType(name).equals("Speaker")) {
                Presenter.notASpeaker();
                return;
            }
        } catch (Exception e) {
            Presenter.invalid("username");
            return;
        }
        Presenter.titlesInSpeaker("scheduleSpeakers1");
        ArrayList<String> allEvents = eventmanager.getAllEvents();
        for(int i = 0; i < allEvents.size(); i++){
            System.out.println("[" + i + "]" + allEvents.get(i));
        }
        Presenter.menusInSpeaker("scheduleSpeakers1");
                Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        switch (command){
            default:
                addSpeakerToEvent(allEvents, name, command);
                break;
            case "r":
                Presenter.titlesInSpeaker("scheduleSpeakers2");
                ArrayList<String> roomLst = eventmanager.getAllRooms();
                for (String s : roomLst) {
                    System.out.println(s);
                }
                Presenter.menusInSpeaker("scheduleSpeakers2");
                        Presenter.exitToMainMenuPrompt();
                String command4 = reader.nextLine();
                switch (command4){
                    case "a":
                        Presenter.inputPrompt("roomNumber");
                        String room = reader.nextLine();
                        Presenter.inputPrompt("startTime");
                        String time1 = reader.nextLine();
                        Presenter.inputPrompt("duration");
                        String duration = reader.nextLine();
                        Presenter.inputPrompt("description");
                        String description = reader.nextLine();
                        try {
                            eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration), description);
                            Presenter.continuePrompt();
                        } catch (Exception e) {
                            Presenter.invalid("addEvent");
                            break;
                        }
                        Presenter.continuePrompt();
                        reader.nextLine();
                        break;
                    case "e":
                        Presenter.exitingToMainMenu();
                        break;
                    default:
                        try{
                            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(command4));
                            for(Integer i: schedule){
                                System.out.println(eventmanager.findEventStr(i));
                            }
                        } catch (InvalidActivityException e) {
                            Presenter.invalid("getEventSchedule");
                            break;
                        }
                        Presenter.inputPrompt("roomNumber");
                        room = reader.nextLine();
                        Presenter.inputPrompt("startTime");
                        time1 = reader.nextLine();
                        Presenter.inputPrompt("duration");
                        duration = reader.nextLine();
                        Presenter.inputPrompt("description");
                        description = reader.nextLine();
                        try {
                            eventmanager.addEvent(room, Timestamp.valueOf(time1), Integer.parseInt(duration), description);
                        } catch (Exception e) {
                            Presenter.invalid("addEvent");
                            break;
                        }
                        Presenter.success();
                        Presenter.continuePrompt();
                        reader.nextLine();
                        break;
                }
                break;
            case "e":
                Presenter.exitingToMainMenu();
                break;
        }
    }

    /**
     * Add a new speaker to an existing event
     * @param allEvents all existing events
     * @param name the name of user who would be a speaker
     * @param command command that organizer choose
     */
    private void addSpeakerToEvent(ArrayList<String> allEvents, String name, String command){
        if(0 <= Integer.parseInt(command) && Integer.parseInt(command) < allEvents.size()){
            try {
                eventmanager.addUserToEvent("Speaker", name, Integer.parseInt(command));
                Presenter.success();
            } catch (Exception e) {
                Presenter.invalid("addSpeaker");
            }
        } else {
            Presenter.invalid("eventId");
        }
    }
}
