package readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Iterates through a list of String prompts
 */
public class EventIterator implements Iterator<String[]> {
    private List<String[]> eventInfo = new ArrayList<>();
    private int current = 0;

    /**
     * The prompt Strings are read from a file, event.csv,
     * and added to the list of event properties.
     */
    public EventIterator() {
        try {
            Scanner myReader = new Scanner(new File("src/resources/event.csv"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                eventInfo.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("event.csv is missing");
            e.printStackTrace();
        }
    }

    /**
     * Checks for subsequent prompts.
     * @return true if there is prompt that has not yet been returned.
     */
    @Override
    public boolean hasNext() {
        return current < eventInfo.size();
    }

    /**
     * Returns the next prompt to be printed.
     * @return the next prompt.
     */
    @Override
    public String[] next() {
        String[] res;
        try {
            res = eventInfo.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }

}
