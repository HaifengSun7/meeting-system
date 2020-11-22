package readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Iterates through a list of String prompts. This class is based off of ReadWriteEx on Quercus
 */

// NOTE: This class is based off of ReadWriteEx on Quercus
public class MessageIterator extends Iterator {
    /**
     * The prompt Strings are read from a file, message.csv,
     * and added to the list of room properties.
     */
    public MessageIterator() {
        try {
            Scanner myReader = new Scanner(new File("src/resources/message.csv"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                info.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("message.csv is missing");
            e.printStackTrace();
        }
    }
}
