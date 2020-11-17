package system;

import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import readWrite.*;
import user.UserManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>User System</h1>
 * The abstract class of different user systems. Initializes all the managers. Having abstract method run().
 */
public abstract class UserSystem {

    protected final String myName;
    protected Scanner reader = new Scanner(System.in);
    protected EventManager eventmanager = new EventManager();
    protected UserManager usermanager = new UserManager();
    protected MessageManager messagemanager = new MessageManager();

    /**
     * Constructs the User System. Initializes the loading of managers.
     * @param myName the username of the user.
     */
    public UserSystem(String myName) {
        this.myName = myName;
        initializeManagers(usermanager, eventmanager, messagemanager);
    }

    /**
     * It is an abstract method and will call the run() methods of subclasses to run.
     */
    public abstract void run();

    /**
     * See the messages that the user got from other users.
     */
    protected void seeMessages() {
        addAllToMessageList();
        ArrayList<String> inbox = messagemanager.getInbox(myName);
        for (int i = 0; i < inbox.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Send messages to a specific person in the contact list.
     */
    protected void sendMessageToSomeone() {
        Presenter.inputPrompt("receiver");
        ArrayList<String> contactList = usermanager.getContactList(myName);
        for (int i = 0; i < contactList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        Presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try {
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                Presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(myName, receiver, message);
            } else if("e".equals(receive)) {
                Presenter.exitingToMainMenu();
                return;
            } else {
                Presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            Presenter.invalid("default");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    /*
     * Add all senders of the inbox messages to user's contact list.
     */
    private void addAllToMessageList() {
        ArrayList<String> inboxSenders = messagemanager.getInboxSender(myName);
        for (String sender : inboxSenders) {
            usermanager.addContactList(sender, myName);
        }
        Presenter.autoAddToMessageList();
    }

    private void initializeManagers(UserManager userManager, EventManager eventManager, MessageManager messageManager){
        initializeUserManager(userManager);
        initializeEventManager(eventManager);
        initializeMessageManager(messageManager);
    }

    private void initializeUserManager(UserManager userManager){
        Iterator userIterator = new UserIterator();
        Iterator eventIterator = new EventIterator();
        String[] temp;
        while (userIterator.hasNext()) {
            temp = userIterator.next();
            try {
                userManager.createUserAccount(temp[2], temp[0], temp[1]);
            } catch (Exception e) {
                System.out.println("This should not be happening.");
            }
        }
        Iterator userIter = new UserIterator();
        while (userIter.hasNext()) {
            temp = userIter.next();
            for (int i = 3; i < temp.length; i++) {
                userManager.addContactList(temp[i], temp[0]);
            }
        }
        String[] temp2;
        int k = 0;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next();
            for (int j = 4; j < temp2.length; j++) {
                try {
                    userManager.addSignedEvent(String.valueOf(k), temp2[j]);
                } catch (Exception e) {
                    System.out.println("cannot add event (userManager). something went wrong.");
                }
            }
            k += 1;
        }
    }

    private void initializeEventManager(EventManager eventManager){
        int j;
        int k = 0;
        Iterator eventIterator = new EventIterator();
        Iterator roomIterator = new RoomIterator();
        UserManager usermanager = new UserManager();
        initializeUserManager(usermanager);
        String[] temp;
        System.out.println("loading existing events from file...");
        while (roomIterator.hasNext()) {
            temp = roomIterator.next();
            try {
                eventManager.addRoom(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            } catch (Exception e) {
                System.out.println("Failed to add room" + Integer.parseInt(temp[0]));
            }
        }
        String[] temp2;
        while (eventIterator.hasNext()) {
            temp2 = eventIterator.next();
            try {
                eventManager.addEvent(temp2[0], Timestamp.valueOf(temp2[1]), Integer.parseInt(temp2[2]), temp2[3]);
            } catch (Exception e) {
                System.out.println("Failed to load event" + temp2[0] + "Invalid room number.");
            }
            for (j = 4; j < temp2.length; j++) {
                try {
                    eventManager.addUserToEvent(usermanager.getUserType(temp2[j]), temp2[j], k);
                } catch (Exception e) {
                    System.out.println("Failed to add User to Event, "+e.getMessage());
                }
            }
            k += 1;
        }
        System.out.println("\n Load complete. Welcome to the system. \n");
    }

    private void initializeMessageManager(MessageManager messageManager){
        int j;
        MessageIterator messageIterator = new MessageIterator();
        String[] temp;
        String temp_str;
        while (messageIterator.hasNext()) {
            temp = messageIterator.next();
            StringBuilder temp_strBuilder = new StringBuilder(temp[2]);
            for (j = 3; j < temp.length; j++) {
                temp_strBuilder.append(',').append(temp[j]);
            }
            temp_str = temp_strBuilder.toString();
            try {
                messageManager.sendMessage(temp[0], temp[1], temp_str);
            } catch (Exception e) {
                System.out.println("This shouldn't happen");
            }
        }
    }

}
