package readWrite;

import presenter.LoadingPresenter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connect to the database.
 */
public class Connect {

    private final LoadingPresenter lp = new LoadingPresenter();

    /**
     * Generate the connection.
     */
    public Connection run() {

        Connection conn = null;
        File file = new File("src/resources/database.db");
        String url = file.getAbsolutePath();
        url = "jdbc:sqlite:" + url;

        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            lp.printErrorMessage(e);
        }
        return conn;
    }
}
