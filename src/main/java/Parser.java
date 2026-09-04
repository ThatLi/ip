import java.time.format.DateTimeParseException;

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
        Command taskCommand = parseTaskCommand(tokens);
        if (taskCommand != null) {
            return taskCommand;
        }
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

    /** Parses valid task-creation commands, leaving malformed input for DobbyLogic's error handling. */
    private static Command parseTaskCommand(String[] tokens) {
        if (tokens[0].equalsIgnoreCase("todo") && tokens.length > 1) {
            return new TodoCommand(joinTokens(tokens, 1, tokens.length));
        }
        if (tokens[0].equalsIgnoreCase("deadline")) {
            return parseDeadline(tokens);
        }
        if (tokens[0].equalsIgnoreCase("event")) {
            return parseEvent(tokens);
        }
        return null;
    }

    /** Parses a valid deadline command. */
    private static Command parseDeadline(String[] tokens) {
        int byIndex = findMarker(tokens, "/by", 1);
        if (byIndex == -1 || byIndex == 1 || byIndex == tokens.length - 1) {
            return null;
        }
        try {
            return new DeadlineCommand(joinTokens(tokens, 1, byIndex),
                    DateTimeUtil.parse(joinTokens(tokens, byIndex + 1, tokens.length)));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Parses a valid event command. */
    private static Command parseEvent(String[] tokens) {
        int fromIndex = findMarker(tokens, "/from", 1);
        int toIndex = findMarker(tokens, "/to", fromIndex + 1);
        if (fromIndex == -1 || toIndex == -1 || fromIndex == 1
                || fromIndex + 1 == toIndex || toIndex == tokens.length - 1) {
            return null;
        }
        try {
            return new EventCommand(joinTokens(tokens, 1, fromIndex),
                    DateTimeUtil.parse(joinTokens(tokens, fromIndex + 1, toIndex)),
                    DateTimeUtil.parse(joinTokens(tokens, toIndex + 1, tokens.length)));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Returns a marker's index, or {@code -1} when it is absent. */
    private static int findMarker(String[] tokens, String marker, int startIndex) {
        for (int index = Math.max(0, startIndex); index < tokens.length; index++) {
            if (marker.equalsIgnoreCase(tokens[index])) {
                return index;
            }
        }
        return -1;
    }

    /** Joins tokens in the half-open range [start, end) with spaces. */
    private static String joinTokens(String[] tokens, int start, int end) {
        String[] selectedTokens = new String[end - start];
        System.arraycopy(tokens, start, selectedTokens, 0, selectedTokens.length);
        return String.join(" ", selectedTokens);
    }
}
