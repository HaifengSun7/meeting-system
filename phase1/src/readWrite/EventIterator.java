package readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Iterates through a list of String prompts. This class is based off of ReadWriteEx on Quercus
 */

// NOTE: This class is based off of ReadWriteEx on Quercus
public class EventIterator extends Iterator {
    /**
     * The prompt Strings are read from a file, event.csv,
     * and added to the list of event properties.
     */
    public EventIterator() {
        try {
            Scanner myReader = new Scanner(new File("src/resources/event.csv"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                info.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("event.csv is missing");
            e.printStackTrace();
        }
    }
}
