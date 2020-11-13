package system;

import readWrite.Write;
import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeSystem{
    private final String attendee;
    public Scanner reader = new Scanner(System.in);
    public EventManager eventmanager = new EventManager();
    public UserManager usermanager = new UserManager();
    public MessageManager messagemanager = new MessageManager();

    public AttendeeSystem(String attendee) {
        this.attendee = attendee;
    }

    public void run() {
        String command;
        while (true){
            Presenter.name(attendee);
            Presenter.userType("Attendee");
            Presenter.attendeeMenu();
            command = reader.nextLine();
            switch (command) {
                case "e":
                    usermanager.logout(attendee);
                    break;
                case "1":
                    SignUpForEvent();
                    continue;
                case "2":
                    checkSignedUp();
                    continue;
                case "3":
                    sendMessageToSomeone();
                    continue;
                case "4":
                    seeMessages();
                    continue;
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

    private void seeMessages() {
        addAllToMessageList();
        ArrayList<String> inbox = messagemanager.getInbox(attendee);
        for(int i = 0; i < inbox.size(); i++){
            Presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(attendee);
        for(String sender: inboxSenders){
            usermanager.addContactList(sender, attendee);
        }
        Presenter.defaultPrint("Added all senders to your contact list automatically.");
    }

    private void sendMessageToSomeone(){
        Presenter.inputPrompt("receiver");
        ArrayList<String> contactList= usermanager.getContactList(attendee);
        for(int i = 0; i < contactList.size(); i++){
            Presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try{
        if (!("e".equals(receive))) {
            String receiver = contactList.get(Integer.parseInt(receive));
            Presenter.inputPrompt("message");
            String message = reader.nextLine();
            messagemanager.sendMessage(attendee, receiver, message);
            Presenter.success();
        } else {
            Presenter.exitingToMainMenu();
        }
        } catch(Exception e) {
            Presenter.invalid("");
        }
    }

    private void SignUpForEvent(){
        ArrayList<String> example_list = eventmanager.canSignUp(attendee);
        Presenter.inputPrompt("signUp");
        for (int i = 0; i < example_list.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + eventmanager.findEventStr(Integer.valueOf(example_list.get(i))));
        }
        Presenter.exitToMainMenuPrompt();
        String command = reader.nextLine();
        if (!("e".equals(command))) {
            try {
                eventmanager.addUserToEvent("attendee", attendee, Integer.parseInt(example_list.get(Integer.parseInt(command))));
            } catch (Exception e) {
                Presenter.invalid("");
                return;
            }
            usermanager.addSignedEvent(command, attendee);
            Presenter.success();
        }
        else{
            Presenter.exitingToMainMenu();
        }
    }

    private void checkSignedUp(){
        ArrayList<String> eventsList = usermanager.getSignedEventList(attendee);
        for (String s : eventsList) {
            System.out.println(eventmanager.findEventStr(Integer.valueOf(s)));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }
}
