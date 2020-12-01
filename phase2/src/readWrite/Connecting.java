package readWrite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * yo i connect.
 */
//TODO: get a better name
public class Connecting {
    /**
     * i do connect.
     */
    public Connection run(){

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
