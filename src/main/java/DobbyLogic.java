/**
 * Evaluate user input
 */
public final class DobbyLogic {
    enum Command {
        LIST
    }

    public static void dobbyListen(String input) {
        try {
            Command command = Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            print(input);
        }
        return ;
    }

    public static boolean isBye(String input) {
        return input.equals("bye");
    }

    private static void print(String str) {
        DobbyUtil.print(str);
    }
}