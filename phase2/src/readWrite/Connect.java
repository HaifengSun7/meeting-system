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
     *
     * @return a Connection object that connects the program gateway with builder.
     */
    public Connection run() {

        Connection conn = null;
        File file = new File("src/resources/database.db");
        String url = file.getAbsolutePath();
        url = "jdbc:sqlite:" + url;
        lp.defaultPrint("Making connection to Database.");
        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            lp.printErrorMessage(e);
        }
        return conn;
    }
}
