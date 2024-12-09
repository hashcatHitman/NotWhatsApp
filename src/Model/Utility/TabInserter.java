package Model.Utility;

/**
 * <p>
 * A utility class, typically used to assist in formatting debug Strings.
 * Contains methods for prepending tabs to every line of a multiline String.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class TabInserter {
// Methods

    /**
     * <p>
     * Inserts multiple tabs before each line in a String.
     * </p>
     *
     * @param string A String to prepend tabs to every line of.
     * @param count  The number of tabs to prepend per line, as an integer.
     *
     * @return The reformatted String.
     */
    public static String insertTabs(String string, int count) {
        for (int tab = 0; tab < count; tab++) {
            string = TabInserter.insertTabs(string);
        }
        return string;
    }

    /**
     * <p>
     * Inserts tabs before each line in a String.
     * </p>
     *
     * @param string A String to prepend tabs to every line of.
     *
     * @return The reformatted String.
     */
    public static String insertTabs(String string) {
        boolean finalNewline = string.endsWith("\n");
        // Split the input string into lines
        String[] lines = string.split("\n");

        // Prepend a tab character to each line
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            result.append("\t").append(line).append("\n");
        }

        // Remove the last newline (for cleaner output)
        if (!result.isEmpty() && finalNewline) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }
}
