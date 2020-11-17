package readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Iterates through a list of String prompts. This class is based off of ReadWriteEx on Quercus
 */

// NOTE: This class is based off of ReadWriteEx on Quercus
public class UserIterator extends Iterator {
    /**
     * The prompt Strings are read from a file, user.csv,
     * and added to the list of student properties.
     */
    public UserIterator() {
        try {
            Scanner myReader = new Scanner(new File("src/resources/user.csv"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                info.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("user.csv is missing");
            e.printStackTrace();
        }
    }
}
