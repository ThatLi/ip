package dobby.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import dobby.exception.DobbyException;
import dobby.task.Task;

/**
 * Saves and loads the task list from the application's data file.
 */
public class Storage {
    /** Relative location of the task data file. */
    private static final Path DATA_FILE = Path.of("data", "duke.txt");

    /**
     * Contains the valid tasks loaded from disk and the number of ignored invalid rows.
     */
    public static final class LoadResult {
        /** Valid tasks reconstructed from the data file. */
        private final List<Task> tasks;
        /** Number of non-blank rows that did not match the save format. */
        private final int invalidTaskCount;

        /** Creates a result containing valid tasks and the invalid-row count. */
        private LoadResult(List<Task> tasks, int invalidTaskCount) {
            this.tasks = tasks;
            this.invalidTaskCount = invalidTaskCount;
        }

        /** Returns the valid tasks loaded from disk. */
        public List<Task> getTasks() {
            return tasks;
        }

        /** Returns the number of invalid task rows that were ignored. */
        public int getInvalidTaskCount() {
            return invalidTaskCount;
        }
    }

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

    /**
     * Loads saved tasks, returning an empty list when the data file is absent.
     *
     * @return valid tasks reconstructed from the data file and the invalid-row count
     * @throws IOException if the data file cannot be read
     */
    public LoadResult load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return new LoadResult(tasks, 0);
        }
        int invalidTaskCount = 0;
        for (String line : Files.readAllLines(DATA_FILE)) {
            if (!line.isBlank()) {
                try {
                    tasks.add(Task.fromFileString(line));
                } catch (DobbyException e) {
                    invalidTaskCount++;
                }
            }
        }
        return new LoadResult(tasks, invalidTaskCount);
    }
}
