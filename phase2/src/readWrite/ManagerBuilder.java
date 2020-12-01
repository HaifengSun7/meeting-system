package readWrite;

import event.*;
import event.exceptions.DuplicateRoomNumberException;
import event.exceptions.NotInOfficeHourException;
import event.exceptions.RoomIsFullException;
import event.exceptions.TimeNotAvailableException;
import message.MessageManager;
import user.DuplicateUserNameException;
import user.InvalidUsernameException;
import user.UserManager;

import javax.activity.InvalidActivityException;
import java.sql.*;

/**
 * I read.
 */
public class ManagerBuilder {
    private final UserManager usermanager;
    private final EventManager eventmanager;
    private final MessageManager messagemanager;
    private Statement stmt;

    public ManagerBuilder(){
        Connecting cct = new Connecting();
        Connection conn = cct.run();
        this.usermanager = new UserManager();
        this.eventmanager = new EventManager();
        this.messagemanager = new MessageManager();
        try{
            this.stmt = conn.createStatement();
        } catch (SQLException e){
            //ignored
        }
    }
    /**
     * run.
     */
    public void run(){
        eventManagerInitialize();
        userManagerInitialize();
        messageManagerInitialize();
    }

    public EventManager getEventManager(){
        return this.eventmanager;
    }

    public UserManager getUserManager(){
        return this.usermanager;
    }

    public MessageManager getMessageManager(){
        return this.messagemanager;
    }

    private void userManagerInitialize() {
        String sql = "SELECT Username, Password, UserType FROM users";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usermanager.createUserAccount(rs.getString("UserType"), rs.getString("Username"), rs.getString("Password"));
        }

        } catch (SQLException e) {
            System.out.println("i dont fucking know.");
        } catch (InvalidUsernameException | DuplicateUserNameException e) {
            // ignored. should never happen.
        }
        // Create User Accounts
        String sql2 = "SELECT Username, CanSendMessageTo FROM messageList";
        try(ResultSet rs2 = stmt.executeQuery(sql2)) {
            while (rs2.next()) {
                usermanager.addContactList(rs2.getString("CanSendMessageTo"), rs2.getString("Username"));
            }

        } catch (SQLException e) {
            System.out.println("i dont fucking know.2");
        }
        // Create Message List
        // IMPORTANT: BY DEFAULT, THE EVENT IDs ARE (ASSUMED) CORRECT.
        // IF ANYTHING WENT WRONG, PLEASE TAKE A LOOK AT THE FOLLOWING LINES.
        String sql3 = "SELECT EventId, UserName FROM signedUp";
        try(ResultSet rs3 = stmt.executeQuery(sql3)){
            while(rs3.next()){
                usermanager.addSignedEvent(String.valueOf(rs3.getInt("EventId")), rs3.getString("UserName"));
            }
        } catch (SQLException e) {
            System.out.println("I don't fucking know 3");
        }
    }


    private void eventManagerInitialize(){
        String sql2 = "SELECT RoomNumber, Capacity FROM room";
        try(ResultSet rs2 = stmt.executeQuery(sql2)){
            while(rs2.next()){
                eventmanager.addRoom(rs2.getInt("RoomNumber"), rs2.getInt("Capacity"));
            }
        } catch (SQLException e) {
            System.out.println("Wrong index in Database, init for roomManager");
        } catch (DuplicateRoomNumberException e) {
            //ignored, should never happen.
        }
        String sql = "SELECT RoomNumber, MaxNumberOfSpeakers, MaxNumberOfAttendees, StartTime, Duration, Description, ConferenceName FROM event";
        try(ResultSet rs1 = stmt.executeQuery(sql)){
            while(rs1.next()){
                System.out.println(rs1.getString("StartTime"));
                eventmanager.addEvent(String.valueOf(rs1.getInt("RoomNumber")),
                        rs1.getInt("MaxNumberOfSpeakers"),
                        rs1.getInt("MaxNumberOfAttendees"),
                        Timestamp.valueOf(rs1.getString("StartTime")),
                        rs1.getInt("Duration"),
                        rs1.getString("Description"),
                        rs1.getString("VIP"));
            }

        } catch (SQLException e) {
            System.out.println("Wrong index in Database");
        } catch (RoomIsFullException | InvalidActivityException | TimeNotAvailableException | NotInOfficeHourException e) {
            //ignored, should never happen
            System.out.println(e.getMessage());
        }
    }

    private void messageManagerInitialize(){
        String sql = "SELECT Sender, Receiver, MessageText FROM message";
        try(ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                messagemanager.sendMessage(rs.getString("Sender"), rs.getString("Receiver"), rs.getString("MessageText"));
            }
        } catch (SQLException e) {
            System.out.println("I don't fucking know 6");
        }
    }
}
