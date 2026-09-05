package dobby.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests task deletion through {@link DobbyLogic}. */
class DobbyLogicTest {
    private static final Path DATA_FILE = Path.of("data", "duke.txt");

    private boolean dataFileExisted;
    private byte[] originalData;
    private DobbyLogic logic;

    /** Saves existing task data and starts each test with no saved tasks. */
    @BeforeEach
    void setUp() throws IOException {
        dataFileExisted = Files.exists(DATA_FILE);
        originalData = dataFileExisted ? Files.readAllBytes(DATA_FILE) : null;
        Files.deleteIfExists(DATA_FILE);
        logic = new DobbyLogic();
    }

    /** Restores the task data that was present before the test. */
    @AfterEach
    void restoreDataFile() throws IOException {
        if (dataFileExisted) {
            Files.createDirectories(DATA_FILE.getParent());
            Files.write(DATA_FILE, originalData);
        } else {
            Files.deleteIfExists(DATA_FILE);
        }
    }

    @Test
    void deleteTask_firstTask_success() {
        logic.createToDo("read book");
        logic.createToDo("return book");

        String result = logic.deleteTask(1);

        assertEquals("> Dobby has removed this task. Now Dobby only see 1 tasks!\n  [T][ ] read book", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] return book\n", logic.showTasks());
    }

    @Test
    void deleteTask_middleTask_success() {
        logic.createToDo("first task");
        logic.createToDo("second task");
        logic.createToDo("third task");

        String result = logic.deleteTask(2);

        assertEquals("> Dobby has removed this task. Now Dobby only see 2 tasks!\n  [T][ ] second task", result);
        assertEquals("> Dobby show 2 tasks:\n1. [T][ ] first task\n2. [T][ ] third task\n", logic.showTasks());
    }

    @Test
    void deleteTask_lastTask_success() {
        logic.createToDo("only task");

        String result = logic.deleteTask(1);

        assertEquals("> Dobby has removed this task. Now Dobby only see 0 tasks!\n  [T][ ] only task", result);
        assertEquals("> Dobby show 0 tasks:\n", logic.showTasks());
    }

    @Test
    void deleteTask_zeroIndex_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.deleteTask(0);

        assertEquals("> Dobby is confused. Dobby can't find task 0", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void deleteTask_negativeIndex_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.deleteTask(-1);

        assertEquals("> Dobby is confused. Dobby can't find task -1", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void deleteTask_indexGreaterThanSize_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.deleteTask(2);

        assertEquals("> Dobby is confused. Dobby can't find task 2", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void deleteTask_emptyTaskList_error() {
        String result = logic.deleteTask(1);

        assertEquals("> Dobby is confused. Dobby can't find task 1", result);
        assertEquals("> Dobby show 0 tasks:\n", logic.showTasks());
    }
}
