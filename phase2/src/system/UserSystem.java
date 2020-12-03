package system;

import event.EventManager;
import event.exceptions.NoSuchConferenceException;
import message.MessageManager;
import presenter.Presenter;
import readWrite.*;
import request.InvalidTitleException;
import request.NoSuchRequestException;
import request.RequestManager;
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
    protected RequestManager requestmanager = new RequestManager();
    protected String conference;

    /**
     * Constructs the User System. Initializes the loading of managers.
     *
     * @param myName the username of the user.
     */
    public UserSystem(String myName) {
        this.myName = myName;
        initializeManagers();
        try {
            this.conference = chooseConference();
        } catch (NoSuchConferenceException e) {
            Presenter.printErrorMessage(e);
        }
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
        requestmanager = read.getRequestManager();
    }

    protected String chooseConference() throws NoSuchConferenceException {
        Presenter.conferenceChoose();
        ArrayList<String> conferenceList = eventmanager.getAllConference();
        for (int i = 0; i < conferenceList.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + conferenceList.get(i));
        }
        Presenter.inputPrompt("enterNumberInSquareBracketsToChooseConference");
        String number = reader.nextLine();
        String chosenConference = null;
        try {
            chosenConference = conferenceList.get(Integer.parseInt(number));
        } catch (Exception e) {
            Presenter.invalid("conference");
        }
        return chosenConference;
    }

    protected void makeNewRequest(){
        Presenter.inputPrompt("makeRequestTitle");
        String title = reader.nextLine();
        Presenter.inputPrompt("makeRequestContext");
        String content = reader.nextLine();
        try{
            requestmanager.createNewRequest(myName, title, content);
            Presenter.inputPrompt("addSuccess");
        } catch (InvalidTitleException e) {
            Presenter.invalid("invalidRequestTitle");
        }
    }

    protected void seeMyRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            Presenter.inputPrompt("NoRequests");
        } else {
            Presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            Presenter.inputPrompt("readRequest");
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    Presenter.defaultPrint(requestList.get(input)[1]);
                } else {
                    Presenter.invalid("");
                    Presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if (!"e".equals(command)) {
                    Presenter.invalid("");
                }
                Presenter.exitingToMainMenu();
            }
        }
    }

    protected void printRequests(ArrayList<String[]> requestList){ //Helper function
        for (int i = 0; i < requestList.size(); i++) {
            String requestTitle = requestList.get(i)[0];
            String status;
            if (requestmanager.getRequestStatus(requestTitle)){
                status = "[Status: Addressed] ";
            } else {
                status = "[Status: Pending]   ";
            }
            Presenter.defaultPrint("[" + i + "] " + status + requestList.get(i)[0]);
        }
        Presenter.exitToMainMenuPrompt();
    }

    protected void deleteRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            Presenter.inputPrompt("NoRequests");
        } else {
            Presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    try{
                        requestmanager.recallSingleRequest(requestList.get(Integer.parseInt(command))[0]);
                        Presenter.inputPrompt("deleteSuccess");
                    } catch (NoSuchRequestException e) {
                        Presenter.invalid("noSuchRequest");
                    }
                } else {
                    Presenter.invalid("");
                    Presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if ("R".equals(command)) {
                    Presenter.inputPrompt("recallRequestConfirm");
                    String confirm = reader.nextLine();
                    if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
                        try {
                            requestmanager.recallAllRequestsFrom(myName);
                            Presenter.inputPrompt("deleteSuccess");
                        } catch (NoSuchRequestException f) {
                            Presenter.invalid("noSuchRequest");
                        }
                    }
                } else if (!"e".equals(command)) {
                    Presenter.invalid("");
                }
                Presenter.exitingToMainMenu();
            }
        }
    }

    /**
     * Save the current status of events, users, messages, and requests.
     */
    protected void save(){
        Write write = new Write(usermanager, eventmanager, messagemanager, requestmanager);
        write.run();
    }
    protected void markUnreadMessages() {
        //addAllToMessageList(); //TODO: do we need this
        ArrayList<String> unreadInbox = messagemanager.getUnread(myName);
        Presenter.inputPrompt("enter number in square bracket to mark message as read");
        for (int i = 0; i < unreadInbox.size(); i++) {
            Presenter.defaultPrint("[" + i + "] " + unreadInbox.get(i));
        }
        Presenter.defaultPrint("[a] mark all as read");
        Presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();

        switch (command){
            case "e":
                Presenter.exitingToMainMenu();
                break;
            case "a":
                try{
                    messagemanager.markAllAsRead(myName);
                    Presenter.success();
                } catch (Exception e){
                    Presenter.printErrorMessage(e);
                }
                break;
            default:
                try {
                    messagemanager.markKthAsRead(myName, Integer.valueOf(command));;
                } catch (Exception e) {
                    Presenter.defaultPrint("Input out of range");
                    return;
                }
                Presenter.success();
        }
        Presenter.continuePrompt();
        reader.nextLine();
    }
}
