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
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length == 2) {
            try {
                int taskNumber = Integer.parseInt(tokens[1]);
                if (tokens[0].equalsIgnoreCase("mark")) {
                    return new MarkCommand(taskNumber);
                }
                if (tokens[0].equalsIgnoreCase("unmark")) {
                    return new UnmarkCommand(taskNumber);
                }
                if (tokens[0].equalsIgnoreCase("delete")) {
                    return new DeleteCommand(taskNumber);
                }
            } catch (NumberFormatException e) {
                // DobbyLogic retains the established invalid-number response for now.
            }
        }
        return null;
    }
}
