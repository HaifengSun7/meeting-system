package ReadWrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Iterates through a list of String prompts
 */
public class RoomIterator implements Iterator<String[]> {
    private List<String[]> roominfo = new ArrayList<>();
    private int current = 0;

    /**
     * The prompt Strings are read from a file, room_properties.txt,
     * and added to the list of room properties.
     */
    public RoomIterator() {

        //open file and read from it...
        BufferedReader br = null;
        try {
            Scanner myReader = new Scanner(new File("room.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                roominfo.add(data.split(","));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("room_properties.txt is missing");
            e.printStackTrace();
        }
    }

    /**
     * Checks for subsequent prompts.
     * @return true if there is prompt that has not yet been returned.
     */
    @Override
    public boolean hasNext() {
        return current < roominfo.size();
    }

    /**
     * Returns the next prompt to be printed.
     * @return the next prompt.
     */
    @Override
    public String[] next() {
        String[] res;

        // List.get(i) throws an IndexOutBoundsException if
        // we call it with i >= properties.size().
        // But Iterator's next() needs to throw a
        // NoSuchElementException if there are no more elements.
        try {
            res = roominfo.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }

    /**
     * Removes the prompt just returned. Unsupported.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }


}
