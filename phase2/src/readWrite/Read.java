package readWrite;

import user.DuplicateUserNameException;
import user.InvalidUsernameException;
import user.UserManager;

import java.io.File;
import java.sql.*;

/**
 * I read.
 */
public class Read {

    private final Connection conn;
    private Statement stmt;

    public Read(){
        this.conn = connect();
        try{this.stmt = conn.createStatement();}
        catch (SQLException e){
            //ignored
        }
    }
    /**
     * run.
     */
    public void run(){
        connect();



    }

    private UserManager userManagerInitialize() {
        UserManager usermanager = new UserManager();
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


        return usermanager;
    }




    private Connection connect(){
        Connection conn = null;
        File file = new File("src/resources/database.db");
        String url = file.getAbsolutePath();
        url = "jdbc:sqlite:" + url;

        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }
}
