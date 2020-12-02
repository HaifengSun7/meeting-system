package presenter;

public class OrganizerPresenter extends Presenter{
    /**
     * Print out the menu of organizer.
     */
    public static void organizerMenu() {
        System.out.println(
                "[1] manage rooms and add events\n" +
                        "[2] create/promote speaker account\n" +
                        "[3] Schedule speakers and their events\n" +
                        "[4] Send message to a particular person\n" +
                        "[5] Send message to all speakers\n" +
                        "[6] Send message to all attendees\n" +
                        "[7] See messages\n" +
                        "[8] create/promote VIP account\n" +
                        "[9] create new attendee account [Not done Yet]\n" +
                        "[10] create new organizer account [Not done Yet]\n" +
                        "[11] Read/tag all requests [Not done Yet]\n" +
                        "[12] Read/tag all pending requests [Not done Yet]\n" +
                        "[13] Read/tag all addressed requests [Not done Yet]\n" +
                        "[save] Save.\n" +
                        "[e] Save and Log out");
    }

    /**
     * Print out the menus in OrganizerSystem.
     *
     * @param methodName the method name to the corresponding menu.
     */
    public static void menusInOrganizer(String methodName) {
        switch (methodName) {
            case "manageRooms":
                System.out.println("Manage rooms:\n" +
                        "[a] add a new room\n" +
                        "[b] see schedule of a certain room\n" +
                        "[c] add a new event\n" +
                        "[c2] cancel an event\n" +
                        "[d] change an event's maximum number of people\n" +
                        "[e] exit to main menu.");
                break;
            case "createSpeaker":
            case "createVIP":
                System.out.println("You want a promotion or a creation?\n " +
                        "[a] promotion\n " +
                        "[b] creation");
                break;
            case "promoteExistingAttendee":
                System.out.println("Note that promotion will make that attendee to be the VIP attendee.\n" +
                        "Enter a username to promote him/her a VIP.");
                break;
            case "scheduleSpeakers1":
                System.out.println("Input the event number of an existing event to add speaker.\n" +
                        "[r] to check schedules of rooms.");
                break;
            case "scheduleSpeakers2":
                System.out.println("Enter room number to check schedule of the room\n" +
                        "[a] to add new event");
                break;
        }
    }
}
