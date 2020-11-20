package readWrite;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class that iterates through files to read.
 */
public class Iterator {
    protected List<String[]> info = new ArrayList<>();
    protected int current = 0;

    /**
     * Checks for subsequent prompts.
     *
     * @return true if there is prompt that has not yet been returned.
     */
    public boolean hasNext() {
        return current < info.size();
    }

    /**
     * Returns the next prompt to be printed.
     *
     * @return the next prompt.
     */
    public String[] next() {
        String[] res;
        // List.get(i) throws an IndexOutBoundsException if
        // we call it with i >= properties.size().
        // But Iterator's next() needs to throw a
        // NoSuchElementException if there are no more elements.
        try {
            res = info.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }
}
