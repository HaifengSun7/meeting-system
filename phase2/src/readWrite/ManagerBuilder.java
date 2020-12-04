package readWrite;

import event.*;
import event.exceptions.*;
import message.MessageManager;
import request.InvalidTitleException;
import request.RequestManager;
import user.DuplicateUserNameException;
import user.InvalidUsernameException;
import user.UserManager;

import java.sql.*;
import java.util.Objects;

/**
 * Build userManager, eventManager, messageManager, requestManager
 */
public class ManagerBuilder {

    private final UserManager usermanager;
    private final EventManager eventmanager;
    private final MessageManager messagemanager;
    private final RequestManager requestmanager;
    private Statement stmt;

    /**
     * Construct the builder for managers.
     */
    public ManagerBuilder(){
        Connecting cct = new Connecting();
        Connection conn = cct.run();
        this.usermanager = new UserManager();
        this.eventmanager = new EventManager();
        this.messagemanager = new MessageManager();
        this.requestmanager = new RequestManager();
        try{
            this.stmt = conn.createStatement();
        } catch (SQLException ignored){}
    }

    /**
     * Runs the builders.
     */
    public void build(){
        eventManagerInitialize();
        userManagerInitialize();
        messageManagerInitialize();
        requestManagerInitialize();
    }

    /**
     * Get the event manager built by this builder.
     *
     * @return an initialized event manager with all info from database.
     */
    public EventManager getEventManager(){
        return this.eventmanager;
    }

    /**
     * Get the user manager built by this builder.
     *
     * @return an initialized user manager with all info from database.
     */
    public UserManager getUserManager(){
        return this.usermanager;
    }

    /**
     * Get the message manager built by this builder.
     *
     * @return an initialized message manager with all info from database.
     */
    public MessageManager getMessageManager(){
        return this.messagemanager;
    }

    /**
     * Get the request manager built by this builder.
     *
     * @return an initialized request manager with all info from database.
     */
    public RequestManager getRequestManager(){
        return this.requestmanager;
    }

    private void userManagerInitialize() {
        String sql = "SELECT Username, Password, UserType FROM users";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usermanager.createUserAccount(rs.getString("UserType"),
                        rs.getString("Username"), rs.getString("Password"));
        }

        } catch (SQLException e) {
            System.out.println("Bad index in user database");
        } catch (InvalidUsernameException | DuplicateUserNameException e) {
            // ignored. should never happen.
        }
        // Create User Accounts
        String sql2 = "SELECT Username, CanSendMessageTo FROM messageList";
        try(ResultSet rs2 = stmt.executeQuery(sql2)) {
            while (rs2.next()) {
                usermanager.addContactList(rs2.getString("CanSendMessageTo"),
                        rs2.getString("Username"));
            }

        } catch (SQLException e) {
            System.out.println("Bad index in message list (contact) database");
        }
        // Create Message List
        // IMPORTANT: BY DEFAULT, THE EVENT IDs ARE (ASSUMED) CORRECT.
        // IF ANYTHING WENT WRONG, PLEASE TAKE A LOOK AT THE FOLLOWING LINES.
        String sql3 = "SELECT EventId, UserName FROM signedUp";
        try(ResultSet rs3 = stmt.executeQuery(sql3)){
            while(rs3.next()){
                usermanager.addSignedEvent(String.valueOf(rs3.getInt("EventId")),
                        rs3.getString("UserName"));
            }
        } catch (SQLException e) {
            System.out.println("Bad index in signed up database");
        }
    }


    private void eventManagerInitialize(){
        String sql2 = "SELECT RoomNumber, Capacity FROM room";
        try(ResultSet rs2 = stmt.executeQuery(sql2)){
            while(rs2.next()){
                eventmanager.addRoom(rs2.getInt("RoomNumber"), rs2.getInt("Capacity"));
            }
        } catch (SQLException e) {
            System.out.println("Bad index in room database");
        } catch (DuplicateRoomNumberException e) {
            //ignored, should never happen.
        }
        String sql = "SELECT RoomNumber, MaxNumberOfSpeakers," +
                " MaxNumberOfAttendees, StartTime, Duration," +
                " Description, ConferenceName, VIPS FROM event";
        try(ResultSet rs1 = stmt.executeQuery(sql)){
            while(rs1.next()){
                eventmanager.addEvent(String.valueOf(rs1.getInt("RoomNumber")),
                        rs1.getInt("MaxNumberOfSpeakers"),
                        rs1.getInt("MaxNumberOfAttendees"),
                        Timestamp.valueOf(rs1.getString("StartTime")),
                        rs1.getInt("Duration"),
                        rs1.getString("Description"),
                        rs1.getString("VIPS"),
                        rs1.getString("ConferenceName"));
            }
        } catch (SQLException e) {
            System.out.println("Bad index in event database");
        } catch (Exception e) {
            //ignored, should never happen
            System.out.println(e.getMessage());
        }
    }

    private void messageManagerInitialize(){
        int i;
        String sql = "SELECT Sender, Receiver, MessageText," +
                " Unread, ReceiverDeleteStatus, ReceiverArchiveStatus," +
                " SenderDeleteStatus, SenderArchiveStatus FROM message";
        try(ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                i = messagemanager.createMessage(rs.getString("Sender"),
                        rs.getString("Receiver"),
                        rs.getString("MessageText"));
                messagemanager.initializeStatus( i ,
                        rs.getBoolean("Unread"),
                        rs.getBoolean("ReceiverDeleteStatus"),
                        rs.getBoolean("ReceiverArchiveStatus"),
                        rs.getBoolean("SenderDeleteStatus"),
                        rs.getBoolean("SenderArchiveStatus"));
            }
        } catch (SQLException e) {
            System.out.println("Bad index in message database");
        }
    }

    private void requestManagerInitialize() {
        String sql = "SELECT Sender, Status, RequestText, RequestTitle FROM request";
        try(ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                requestmanager.createNewRequest(rs.getString("Sender"),
                        rs.getString("RequestTitle"), rs.getString("RequestText"));
                if (Objects.equals(rs.getString("Status"), "Addressed")){
                    requestmanager.changeStatus(rs.getString("RequestTitle"));
                }
            }
        } catch (SQLException | InvalidTitleException e) {
            System.out.println("Bad index in request database");
        }
    }
}
