package system;

import presenter.SpeakerPresenter;

/**
 * The SpeakerSystem program implements the system of Speaker user. Extends from UserSystem
 */
public class SpeakerSystem extends UserSystem {

    private final SpeakerPresenter speakerPresenter;

    /**
     * Constructor for SpeakerSystem
     *
     * @param myName A String, which is the username of speaker who logged in.
     */
    public SpeakerSystem(String myName) {
        super(myName);
        this.speakerPresenter = new SpeakerPresenter();
    }

    /**
     * Run the Speaker System. Print out speaker's menu, and perform speaker's operations.
     */
    @Override
    public void run() {

        while (conference != null) {
            speakerPresenter.name(myName);
            speakerPresenter.userType("Speaker");
            speakerPresenter.speakerMenu();
            String command = reader.nextLine();

            switch (command) {
                case "e":
                    usermanager.logout(myName);
                    break;
                case "1":   //See the events that the speaker gave
                    eventSystem.checkTalkedEvent();
                    continue;
                case "2":   //See the messages that the speaker gave
                    messageSystem.getSentMessages();
                    continue;
                case "3":  //send messages to all Attendees who signed up for a particular event
                    messageSystem.sendMessageToEvent();
                    continue;
                case "4": //send messages to a particular Attendee who signed up for a particular event
                    messageSystem.sendMessageToOneAttendee();
                    continue;
                case "5": //respond to an Attendee
                    messageSystem.respondToAttendee();
                    continue;
                case "6": // send message (regular)
                    messageSystem.sendMessageToSomeone();
                case "7": //see message inbox
                    messageSystem.seeMessages();
                    continue;
                case "8":
                    requestSystem.makeNewRequest();
                    continue;
                case "9":
                    requestSystem.seeMyRequests();
                    continue;
                case "10":
                    requestSystem.deleteRequests();
                    continue;
                case "11":
                    messageSystem.markUnreadMessages();
                    continue;
                case "12":
                    messageSystem.deleteMessage();
                    continue;
                case "13":
                    messageSystem.archiveMessage();
                    continue;
                case "14":
                    messageSystem.unArchiveMessage();
                    continue;
                case "15":
                    messageSystem.seeArchivedMessage();
                    continue;
                case "save":
                    save();
                    continue;
                default:
                    speakerPresenter.wrongKeyReminder();
                    continue;
            }
            break;
        }
        save();
    }

}