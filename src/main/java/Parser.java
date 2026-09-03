/**
 * Converts recognized user input into command objects.
 */
public final class Parser {
    /** Prevents construction of this utility class. */
    private Parser() {
    }

    /**
     * Parses commands that have been extracted from {@link DobbyLogic}.
     *
     * @param input complete user input
     * @return a command object, or {@code null} when this parser does not yet handle the input
     */
    public static Command parse(String input) {
        if (input.trim().equalsIgnoreCase("list")) {
            return new ListCommand();
        }
        if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        }
        return null;
    }
}
