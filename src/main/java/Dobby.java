/**
 * Starts the Dobby chatbot application.
 */
public class Dobby {
    /**
     * Displays a welcome banner when the chatbot starts.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String lines = "____________________________________________________________";
        String banner = "       *       .       *       .       *       \n"
                + "   .      ____        _     _              .   \n"
                + "     *   |  _ \\  ___ | |__ | |__  _   _     * \n"
                + "   .     | | | |/ _ \\| '_ \\| '_ \\| | | |   . \n"
                + "     *   | |_| | (_) | |_) | |_) | |_| |     * \n"
                + "   .     |____/ \\___/|_.__/|_.__/ \\__, |   . \n"
                + "                                  |___/        \n"
                + "       *       .       *       .       *       \n";
        print(lines);
        print(banner);
        print("Dobby says hi!");
        print("Dobby is ready to take orders.");
        print(lines);
        print("Dobby says goodbye to master!");
    }

    private static void print(String str) {
        System.out.println(str);
    }
}
