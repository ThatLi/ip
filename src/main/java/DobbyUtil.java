/**
 * Utility class for Dobby values and functions
 */
public final class DobbyUtil {
    /** Utility attributes */
    // Displays
    static final String LINE = "____________________________________________________________";
    static final String BANNER = "       *       .       *       .       *       \n"
            + "   .      ____        _     _              .   \n"
            + "     *   |  _ \\  ___ | |__ | |__  _   _     * \n"
            + "   .     | | | |/ _ \\| '_ \\| '_ \\| | | |   . \n"
            + "     *   | |_| | (_) | |_) | |_) | |_| |     * \n"
            + "   .     |____/ \\___/|_.__/|_.__/ \\__, |   . \n"
            + "                                  |___/        \n"
            + "       *       .       *       .       *       \n";

    /** Utility methods */
    public static void print(String str) {
        System.out.println(str);
    }

    public static String encloseBracket(String str) {
        return "[" + str + "]";
    }
}
