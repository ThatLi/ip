package dobby.parser;

import dobby.command.Command;
import dobby.command.DeadlineCommand;
import dobby.command.DeleteCommand;
import dobby.command.EventCommand;
import dobby.command.ExitCommand;
import dobby.command.FindCommand;
import dobby.command.InvalidCommand;
import dobby.command.ListCommand;
import dobby.command.MarkCommand;
import dobby.command.TodoCommand;
import dobby.command.UnmarkCommand;
import dobby.exception.DobbyException;
import dobby.util.DateTimeUtil;

/** Converts every user input into an executable command object. */
public final class Parser {
    private Parser() {
    }

    /** Parses one complete user input line. */
    public static Command parse(String input) {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return invalid("> Dobby couldn't hear you. Dobby want you to speak louder!");
        }
        if (trimmedInput.equalsIgnoreCase("list")) {
            return new ListCommand();
        }
        if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        }
        String[] tokens = trimmedInput.split("\\s+"); // '\s+' matches 1 or more whitespace(s)
        return switch (tokens[0].toLowerCase()) {
            case "list" -> invalid("> Dobby is confused. Dobby think you meant 'list'");
            case "todo" -> tokens.length > 1 ? new TodoCommand(join(tokens, 1, tokens.length))
                    : invalid("> Dobby is confused. Dobby think you meant 'todo <description>'");
            case "deadline" -> deadline(tokens);
            case "event" -> event(tokens);
            case "find" -> tokens.length > 1 ? new FindCommand(join(tokens, 1, tokens.length))
                    : invalid("> Dobby is confused. Dobby think you meant 'find <keyword>'");
            case "mark" -> numbered(tokens, true, false);
            case "unmark" -> numbered(tokens, false, false);
            case "delete" -> numbered(tokens, false, true);
            default -> invalid(" > Dobby asks is this a Todo, Deadline, or Event?");
        };
    }

    private static Command numbered(String[] tokens, boolean isMark, boolean isDelete) {
        if (tokens.length != 2) {
            return invalid("> Dobby is confused. Dobby think you meant '" + tokens[0] + " <Task number>'");
        }
        try {
            int taskNumber = parseTaskNumber(tokens[1]);
            return isDelete ? new DeleteCommand(taskNumber)
                    : (isMark ? new MarkCommand(taskNumber) : new UnmarkCommand(taskNumber));
        } catch (DobbyException e) {
            return invalid(e.getMessage());
        }
    }

    private static Command deadline(String[] tokens) {
        int byIndex = marker(tokens, "/by", 1);
        if (byIndex == -1 || byIndex == 1 || byIndex == tokens.length - 1) {
            return invalid("> Dobby is confused. Dobby think you meant 'deadline <description> /by <date/time>'");
        }
        try {
            return new DeadlineCommand(join(tokens, 1, byIndex),
                    DateTimeUtil.parse(join(tokens, byIndex + 1, tokens.length)));
        } catch (DobbyException e) {
            return invalid("> Dobby needs a valid date: yyyy-MM-dd, optionally followed by HHmm.");
        }
    }

    private static Command event(String[] tokens) {
        int fromIndex = marker(tokens, "/from", 1);
        int toIndex = marker(tokens, "/to", fromIndex + 1);
        if (fromIndex == -1 || toIndex == -1 || fromIndex == 1
                || fromIndex + 1 == toIndex || toIndex == tokens.length - 1) {
            return invalid("> Dobby is confused. Dobby think you meant "
                    + "'event <description> /from <date/time> /to <date/time>'");
        }
        try {
            return new EventCommand(join(tokens, 1, fromIndex),
                    DateTimeUtil.parse(join(tokens, fromIndex + 1, toIndex)),
                    DateTimeUtil.parse(join(tokens, toIndex + 1, tokens.length)));
        } catch (DobbyException e) {
            return invalid("> Dobby needs valid dates: yyyy-MM-dd, optionally followed by HHmm.");
        }
    }

    private static InvalidCommand invalid(String message) {
        return new InvalidCommand(message);
    }

    /** Converts task-number text into an integer used by a Dobby command. */
    private static int parseTaskNumber(String text) throws DobbyException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new DobbyException("Dobby is confused. Dobby expected a task number", e);
        }
    }

    private static int marker(String[] tokens, String marker, int start) {
        for (int index = Math.max(0, start); index < tokens.length; index++) {
            if (marker.equalsIgnoreCase(tokens[index])) {
                return index;
            }
        }
        return -1;
    }

    private static String join(String[] tokens, int start, int end) {
        String[] selectedTokens = new String[end - start];
        System.arraycopy(tokens, start, selectedTokens, 0, selectedTokens.length);
        return String.join(" ", selectedTokens);
    }
}
