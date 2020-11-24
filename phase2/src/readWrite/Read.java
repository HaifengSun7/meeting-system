package readWrite;

import user.UserManager;

import java.io.File;
import java.sql.*;

/**
 * I read.
 */
public class Read {

    /**
     * run.
     */
    public void run(){
        connect();



    }

    private UserManager userManagerInitialize() {
        UserManager usermanager = new UserManager();
        String sql = "SELECT Username, Password, UserType FROM users";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("Username") +  "\t" +
                    rs.getString("Password") + "\t" +
                    rs.getString("Usertype"));
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
