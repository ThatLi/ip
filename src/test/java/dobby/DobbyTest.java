package dobby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests command handling through the UI-independent Dobby facade. */
class DobbyTest {
    private static final Path DATA_FILE = Path.of("data", "duke.txt");

    private boolean hasOriginalDataFile;
    private byte[] originalData;
    private Dobby dobby;

    /** Saves existing task data and starts each test with no saved tasks. */
    @BeforeEach
    void setUp() throws IOException {
        hasOriginalDataFile = Files.exists(DATA_FILE);
        originalData = hasOriginalDataFile ? Files.readAllBytes(DATA_FILE) : null;
        Files.deleteIfExists(DATA_FILE);
        dobby = new Dobby();
    }

    /** Restores the task data that was present before the test. */
    @AfterEach
    void restoreDataFile() throws IOException {
        if (hasOriginalDataFile) {
            Files.createDirectories(DATA_FILE.getParent());
            Files.write(DATA_FILE, originalData);
        } else {
            Files.deleteIfExists(DATA_FILE);
        }
    }

    @Test
    void getResponse_taskCommands_shareTaskState() {
        assertEquals("> Dobby noted a new Todo: read book", dobby.getResponse("todo read book"));
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", dobby.getResponse("list"));
    }

    @Test
    void getResponse_exitCommand_returnsGoodbyeMessage() {
        assertEquals("> Dobby says goodbye to master!", dobby.getResponse("bye"));
    }

    @Test
    void isExitCommand_exitAndNonExitCommands_returnsMatchingStatus() {
        assertTrue(dobby.isExitCommand("bye"));
        assertFalse(dobby.isExitCommand("list"));
    }
}
