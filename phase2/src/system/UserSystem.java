package system;

import event.EventManager;
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
    private final Presenter presenter = new Presenter();
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
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    /**
     * Send messages to a specific person in the contact list.
     */
    protected void sendMessageToSomeone() {
        presenter.inputPrompt("receiver");
        ArrayList<String> contactList = usermanager.getContactList(myName);
        for (int i = 0; i < contactList.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + contactList.get(i));
        }
        presenter.exitToMainMenuPrompt();
        String receive = reader.nextLine();
        try {
            if (!("e".equals(receive)) && (0 <= Integer.parseInt(receive)) && (Integer.parseInt(receive) < contactList.size())) {
                String receiver = contactList.get(Integer.parseInt(receive));
                presenter.inputPrompt("message");
                String message = reader.nextLine();
                messagemanager.sendMessage(myName, receiver, message);
            } else if ("e".equals(receive)) {
                presenter.exitingToMainMenu();
                return;
            } else {
                presenter.inputOutOfRange();
            }
        } catch (Exception e) {
            presenter.invalid("");
        }
        presenter.continuePrompt();
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
        presenter.autoAddToMessageList();
    }

    private void initializeManagers() {
        ManagerBuilder read = new ManagerBuilder();
        read.build();
        usermanager = read.getUserManager();
        eventmanager = read.getEventManager();
        messagemanager = read.getMessageManager();
        requestmanager = read.getRequestManager();
    }

    protected String chooseConference(){
        presenter.conferenceChoose();
        ArrayList<String> conferenceList = eventmanager.getAllConference();
        for (int i = 0; i < conferenceList.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + conferenceList.get(i));
        }
        presenter.inputPrompt("enterNumberInSquareBracketsToChooseConference");
        String number = reader.nextLine();
        String chosenConference = null;
        try {
            chosenConference = conferenceList.get(Integer.parseInt(number));
        } catch (Exception e) {
            presenter.invalid("conference");
        }
        return chosenConference;
    }

    /**
     * Create a new request.
     * Input from reader: String title
     * Input from reader: String content
     */
    protected void makeNewRequest(){
        presenter.inputPrompt("makeRequestTitle");
        String title = reader.nextLine();
        presenter.inputPrompt("makeRequestContext");
        String content = reader.nextLine();
        try{
            requestmanager.createNewRequest(myName, title, content);
            presenter.inputPrompt("addSuccess");
        } catch (InvalidTitleException e) {
            presenter.invalid("invalidRequestTitle");
        }
    }

    /**
     * Print a request list include all requests from one particular person.
     */
    protected void seeMyRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            presenter.inputPrompt("NoRequests");
        } else {
            presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            presenter.inputPrompt("readRequest");
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    presenter.defaultPrint(requestList.get(input)[1]);
                } else {
                    presenter.invalid("");
                    presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if (!"e".equals(command)) {
                    presenter.invalid("");
                }
                presenter.exitingToMainMenu();
            }
        }
    }

    /**
     * This is a protected helper function.
     * Help to print out a list of requests regardless what type of requests list contained.
     * Print a request list include all requests from one particular person.
     */
    protected void printRequests(ArrayList<String[]> requestList){ //Helper function
        for (int i = 0; i < requestList.size(); i++) {
            String requestTitle = requestList.get(i)[0];
            String status;
            if (requestmanager.getRequestStatus(requestTitle)){
                status = "[Status: Addressed] ";
            } else {
                status = "[Status: Pending]   ";
            }
            presenter.defaultPrint("[" + i + "] " + status + requestList.get(i)[0]);
        }
        presenter.exitToMainMenuPrompt();
    }

    /**
     * This method can delete requests from one user.
     */
    protected void deleteRequests(){
        ArrayList<String[]> requestList = requestmanager.getRequestsFrom(myName);
        if (requestList.size() == 0){
            presenter.inputPrompt("NoRequests");
        } else {
            presenter.inputPrompt("requestIntroduction");
            printRequests(requestList);
            String command = reader.nextLine();
            try {
                int input = Integer.parseInt(command);
                if ((0 <= input) && (input < requestList.size())) {
                    try{
                        requestmanager.recallSingleRequest(requestList.get(Integer.parseInt(command))[0]);
                        presenter.inputPrompt("deleteSuccess");
                    } catch (NoSuchRequestException e) {
                        presenter.invalid("noSuchRequest");
                    }
                } else {
                    presenter.invalid("");
                    presenter.exitingToMainMenu();
                }
            } catch (NumberFormatException e) {
                if ("R".equals(command)) {
                    presenter.inputPrompt("recallRequestConfirm");
                    String confirm = reader.nextLine();
                    if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("Y")) {
                        try {
                            requestmanager.recallAllRequestsFrom(myName);
                            presenter.inputPrompt("deleteSuccess");
                        } catch (NoSuchRequestException f) {
                            presenter.invalid("noSuchRequest");
                        }
                    }
                } else if (!"e".equals(command)) {
                    presenter.invalid("");
                }
                presenter.exitingToMainMenu();
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
        presenter.inputPrompt("enter number in square bracket to mark message as read");
        for (int i = 0; i < unreadInbox.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + unreadInbox.get(i));
        }
        presenter.defaultPrint("[a] mark all as read");
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();

        switch (command){
            case "e":
                presenter.exitingToMainMenu();
                break;
            case "a":
                try{
                    messagemanager.markAllAsRead(myName);
                    presenter.success();
                } catch (Exception e){
                    presenter.printErrorMessage(e);
                }
                break;
            default:
                try {
                    messagemanager.markKthAsRead(myName, Integer.valueOf(command));
                } catch (Exception e) {
                    presenter.defaultPrint("Input out of range");
                    return;
                }
                presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    protected void deleteMessage() {
        ArrayList<String> inbox = messagemanager.getAll(myName);
        presenter.inputPrompt("enter number in square bracket to delete message. Warning: you might mis-deleted messages you haven't read");
        for (int i = 0; i < inbox.size(); i++){
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.deleteKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    protected void archiveMessage(){
        ArrayList<String> inbox = messagemanager.getAll(myName);
        presenter.inputPrompt("enter number in square bracket to archive message. " +
                "Warning: you might archive message that you have archived");
        for (int i = 0; i < inbox.size(); i++){
            presenter.defaultPrint("[" + i + "] " + inbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.archiveKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    protected void unArchiveMessage(){
        ArrayList<String> archivedInbox = messagemanager.getArchived(myName);
        presenter.inputPrompt("enter number in square bracket to archive message." +
                " Warning: you might archive message that you have archived");
        for (int i = 0; i < archivedInbox.size(); i++){
            presenter.defaultPrint("[" + i + "] " + archivedInbox.get(i));
        }
        presenter.defaultPrint("[e] exit");
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
        } else {
            try {
                messagemanager.unArchiveKth(myName, Integer.valueOf(command));
            } catch (Exception e) {
                presenter.defaultPrint("Input out of range");
                return;
            }
            presenter.success();
        }
        presenter.continuePrompt();
        reader.nextLine();
    }

    protected void seeArchivedMessage(){
        ArrayList<String> archivedMessage = messagemanager.getArchived(myName);
        for (int i = 0; i < archivedMessage.size(); i++) {
            presenter.defaultPrint("[" + i + "] " + archivedMessage.get(i));
        }
        presenter.continuePrompt();
        reader.nextLine();
    }
}
