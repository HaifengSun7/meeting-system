package system;

import event.EventManager;
import event.exceptions.*;
import presenter.Presenter;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class EventSystem {
    private final EventManager eventmanager;
    private final Presenter presenter;
    private final String conference;
    private final UserManager usermanager;
    protected Scanner reader = new Scanner(System.in);

    public EventSystem(EventManager eventmanager, UserManager usermanager, String conference) {
        this.eventmanager = eventmanager;
        this.presenter = new Presenter();
        this.conference = conference;
        this.usermanager = usermanager;
    }

    /*
     * Create a new room.
     */
    protected void addNewRoom() {
        presenter.inputPrompt("newRoomNumber");
        String roomNumber = reader.nextLine();
        presenter.inputPrompt("roomSize");
        String size = reader.nextLine();
        try {
            eventmanager.addRoom(Integer.parseInt(roomNumber), Integer.parseInt(size));
            presenter.success();
        } catch (DuplicateRoomNumberException | WrongRoomSizeException e) {
            presenter.printErrorMessage(e);
        } catch (Exception e) {
            presenter.invalid("");
        }
    }

    /*
     * Check all scheduled events in a specific room.
     */
    protected void checkRoom() {
        presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer i : schedule) {
                presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            presenter.defaultPrint("Wrong input.");
            presenter.continuePrompt();
        }

    }

    /*
     * The action of adding an Event, with info from inputs.
     */
    protected void addingEvent() {

        presenter.inputPrompt("roomNumber");
        String room = reader.nextLine();
        presenter.inputPrompt("startTime");
        String time1 = reader.nextLine();
        presenter.inputPrompt("duration");
        String duration = reader.nextLine();
        presenter.inputPrompt("description");
        String description = reader.nextLine();
        presenter.inputPrompt("eventType");
        String type = reader.nextLine();
        presenter.inputPrompt("vip");
        String vip = reader.nextLine();
        int maxSpeaker;
        int maxAttendee;
        try {
            switch (type) {
                case ("Party"):
                    maxSpeaker = 0;
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case ("Single"):
                    maxSpeaker = 1;
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                case ("Multi"):
                    presenter.inputPrompt("numSpeaker");
                    maxSpeaker = Integer.parseInt(reader.nextLine());
                    presenter.inputPrompt("maximum people");
                    maxAttendee = Integer.parseInt(reader.nextLine());
                    break;
                default:
                    throw new NoSuchTypeException("Incorrect Event Type");
            }
        } catch (Exception e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            return;
        }
        try {
            presenter.loadEvent(room, time1, duration);
            eventmanager.addEvent(room, maxSpeaker, maxAttendee, Timestamp.valueOf(time1), Integer.parseInt(duration), description, vip, conference);
            presenter.success();
            presenter.continuePrompt();
        } catch (NotInOfficeHourException | TimeNotAvailableException | InvalidActivityException |
                RoomIsFullException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
        } catch (Exception e) {
            presenter.invalid("addEventGeneral"); // Should not be called
        }
    }

    /*
     * Cancel an event.
     */
    protected void cancelEvent() {
        String eventId = reader.nextLine();
        try {
            ArrayList<String> userList = new ArrayList<>(eventmanager.getAttendees(eventId));
            for (String username : userList) {
                usermanager.deleteSignedEvent(eventId, username);
            }
            eventmanager.cancelEvent(eventId, conference);
            presenter.success();
        } catch (NoSuchEventException | InvalidActivityException | NoSuchConferenceException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
        } catch (Exception e) {
            presenter.invalid("");
        }
    }

    /*
     * set the maximum number of people in the selected event.
     */
    protected void changeEventMaxNumberPeople() {
        presenter.inputPrompt("roomNumber");
        String roomNumber = reader.nextLine();
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(roomNumber));
            for (Integer integer : schedule) {
                presenter.defaultPrint("[" + integer + "] " + eventmanager.findEventStr(integer));
            }
            presenter.inputPrompt("enterNumberInSquareBracketsToChooseEvent");
            presenter.exitToMainMenuPrompt();
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
            presenter.continuePrompt();
            reader.nextLine();
            return;
        } catch (NumberFormatException e) {
            presenter.invalid("");
            presenter.continuePrompt();
            return;
        }
        String command = reader.nextLine();
        if ("e".equals(command)) {
            presenter.exitingToMainMenu();
            presenter.continuePrompt();
        } else {
            try {
                presenter.inputPrompt("newMaxPeopleOfEvent");
                String newMax = reader.nextLine();
                eventmanager.setMaximumPeople(Integer.parseInt(roomNumber), Integer.parseInt(newMax),
                        Integer.parseInt(command));
                presenter.success();
                presenter.continuePrompt();
            } catch (NoSuchEventException | InvalidNewMaxNumberException | InvalidActivityException e) {
                presenter.printErrorMessage(e);
                presenter.continuePrompt();
            }
        }
    }

    /*
     * Check all the events.
     */
    protected void checkAllEvent() {
        try {
            ArrayList<String> allEvents = eventmanager.getAllEvents(conference);
            for (int i = 0; i < allEvents.size(); i++) {
                presenter.defaultPrint("[" + i + "]" + allEvents.get(i));
            }
            System.out.println("That's all.");
        } catch (NoSuchConferenceException e) {
            presenter.printErrorMessage(e);
        }
        presenter.continuePrompt();
    }
    /*
     * show the events in a selected room
     * @param command the room number
     */
    protected void showEvents(String command) {
        try {
            ArrayList<Integer> schedule = eventmanager.getSchedule(Integer.parseInt(command));
            for (Integer i : schedule) {
                presenter.defaultPrint(eventmanager.findEventStr(i));
            }
        } catch (InvalidActivityException e) {
            presenter.printErrorMessage(e);
        }
    }


}
