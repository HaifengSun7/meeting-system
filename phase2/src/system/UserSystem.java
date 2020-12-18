package system;

import event.EventManager;
import message.MessageManager;
import presenter.Presenter;
import readWrite.ManagerBuilder;
import readWrite.Write;
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
    protected final Presenter presenter = new Presenter();
    protected final EventSystem eventSystem;
    protected final RequestSystem requestSystem;
    protected final MessageSystem messageSystem;
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
        this.eventSystem = new EventSystem(eventmanager, usermanager, conference, myName);
        this.requestSystem = new RequestSystem(requestmanager, myName);
        this.messageSystem = new MessageSystem(usermanager, eventmanager, messagemanager, myName, conference);
    }

    /**
     * It is an abstract method and will call the run() methods of subclasses to run.
     */
    public abstract void run();

    private void initializeManagers() {
        ManagerBuilder read = new ManagerBuilder();
        read.build();
        usermanager = read.getUserManager();
        eventmanager = read.getEventManager();
        messagemanager = read.getMessageManager();
        requestmanager = read.getRequestManager();
    }

    /**
     * Do the things required to choose the conference.
     *
     * @return the chosen conference name.
     */
    protected String chooseConference() {
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
     * Save the current status of events, users, messages, and requests.
     */
    protected void save() {
        presenter.defaultPrint("Saving files, please don't press anything until further instructions.");
        Write write = new Write(usermanager, eventmanager, messagemanager, requestmanager);
        write.run();
    }


}
