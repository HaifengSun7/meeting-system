package readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Iterates through a list of String prompts. This class is based off of ReadWriteEx on Quercus
 *
 * @author Haifeng Sun
 */

// NOTE: This class is based off of ReadWriteEx on Quercus
public class MessageIterator implements Iterator<String[]> {
    private final List<String[]> messageInfo = new ArrayList<>();
    private int current = 0;

    /**
     * The prompt Strings are read from a file, message.csv,
     * and added to the list of room properties.
     */
    public MessageIterator() {
        try {
            Scanner myReader = new Scanner(new File("src/resources/message.csv"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                messageInfo.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("message.csv is missing");
            e.printStackTrace();
        }
    }

    /**
     * Checks for subsequent prompts.
     *
     * @return true if there is prompt that has not yet been returned.
     */
    @Override
    public boolean hasNext() {
        return current < messageInfo.size();
    }

    /**
     * Returns the next prompt to be printed.
     *
     * @return the next prompt.
     */
    @Override
    public String[] next() {
        String[] res;

        try {
            res = messageInfo.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }
}
