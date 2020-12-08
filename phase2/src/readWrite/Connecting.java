package readWrite;

import presenter.LoadingPresenter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connect with the database.
 */
public class Connecting {

    private final LoadingPresenter lp = new LoadingPresenter();

    /**
     * Start the connection.
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
            lp.printLoad();

        } catch (SQLException e) {
            lp.printErrorMessage(e);
        }
        return conn;
    }
}
