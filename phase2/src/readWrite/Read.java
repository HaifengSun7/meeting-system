package readWrite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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




    private static void connect() {
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

    }
}
