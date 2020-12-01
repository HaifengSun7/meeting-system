package system;

import com.sun.xml.internal.stream.StaxErrorReporter;
import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import readWrite.*;
import request.InvalidTitleException;
import request.Requestmanager;
import user.UserManager;

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
    protected Requestmanager requestmanager = new Requestmanager();
    protected String conference;

    /**
     * Constructs the User System. Initializes the loading of managers.
     *
     * @param myName the username of the user.
     */
    public UserSystem(String myName) {
        this.myName = myName;
        initializeManagers();
        this.conference = chooseConference();
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
            } else if ("e".equals(receive)) {
                Presenter.exitingToMainMenu();
                return;
            } else {
                Presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            Presenter.invalid("");
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }

    protected void makeNewRequest(){
        Presenter.inputPrompt("makeRequestTitle");
        String title = reader.nextLine();
        Presenter.inputPrompt("makeRequestContext");
        String content = reader.nextLine();
        try{
            requestmanager.createNewRequest(myName, title, content);
        } catch (InvalidTitleException e) {
            Presenter.invalid("invalidRequestTitle");
        }

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

    private void initializeManagers() {
        ManagerBuilder read = new ManagerBuilder();
        read.run();
        usermanager = read.getUserManager();
        eventmanager = read.getEventManager();
        messagemanager = read.getMessageManager();
    }

    protected String chooseConference(){
        Presenter.conferenceChoose();
        ArrayList<String> conferenceList = eventmanager.getAllConference();
        for (int i = 0; i < conferenceList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + conferenceList.get(i));
        }
        Presenter.inputPrompt("enterNumberInSquareBracketsToChooseConference");
        String number = reader.nextLine();
        return conferenceList.get(Integer.parseInt(number));
    }
}
