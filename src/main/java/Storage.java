import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Saves the current task list to the application's data file.
 */
public class Storage {
    /** Relative location of the task data file. */
    private static final Path DATA_FILE = Path.of("data", "duke.txt");

    /**
     * Replaces the data file with the supplied tasks, one task per line.
     *
     * @param tasks tasks to save
     * @throws IOException if the data directory or file cannot be written
     */
    public void save(List<Task> tasks) throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        List<String> lines = tasks.stream().map(Task::toFileString).toList();
        Files.write(DATA_FILE, lines);
    }
}
