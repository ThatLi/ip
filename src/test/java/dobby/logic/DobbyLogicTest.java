package dobby.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import dobby.util.DateTimeUtil;
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
    void constructor_emptyDataFile_startsWithoutMessageOrTasks() {
        assertEquals("", logic.getStartupMessage());
        assertEquals("> Dobby show 0 tasks:\n", logic.showTasks());
    }

    @Test
    void constructor_validSavedTasks_loadsTasksWithoutMessage() throws IOException {
        Files.writeString(DATA_FILE, "T | 0 | read book\nD | 1 | return book | 2019-12-02 1800\n");

        logic = new DobbyLogic();

        assertEquals("", logic.getStartupMessage());
        assertEquals("> Dobby show 2 tasks:\n1. [T][ ] read book\n"
                + "2. [D][X] return book (by: Dec 02 2019, 18:00)\n", logic.showTasks());
    }

    @Test
    void constructor_invalidSavedTask_reportsInvalidTaskAndLoadsValidTasks() throws IOException {
        Files.writeString(DATA_FILE, "T | 0 | read book\nnot a task\n");

        logic = new DobbyLogic();

        assertEquals("> Dobby skipped 1 invalid saved task(s).", logic.getStartupMessage());
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void createToDo_validDescription_addsTask() {
        String result = logic.createToDo("read book");

        assertEquals("> Dobby noted a new Todo: read book", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void createDeadline_dateOnly_addsDeadlineWithDateDisplay() throws Exception {
        String result = logic.createDeadline("return book", DateTimeUtil.parse("2019-12-02"));

        assertEquals("> Dobby noted a new Deadline: return book by Dec 02 2019", result);
        assertEquals("> Dobby show 1 tasks:\n1. [D][ ] return book (by: Dec 02 2019)\n", logic.showTasks());
    }

    @Test
    void createDeadline_dateAndTime_addsDeadlineWithDateTimeDisplay() throws Exception {
        String result = logic.createDeadline("return book", DateTimeUtil.parse("2019-12-02 1800"));

        assertEquals("> Dobby noted a new Deadline: return book by Dec 02 2019, 18:00", result);
        assertEquals("> Dobby show 1 tasks:\n1. [D][ ] return book (by: Dec 02 2019, 18:00)\n", logic.showTasks());
    }

    @Test
    void createEvent_dateOnly_addsEventWithDateDisplay() throws Exception {
        String result = logic.createEvent("project meeting", DateTimeUtil.parse("2019-12-03"),
                DateTimeUtil.parse("2019-12-04"));

        assertEquals("> Dobby noted a new Event: project meeting from Dec 03 2019 to Dec 04 2019", result);
        assertEquals("> Dobby show 1 tasks:\n1. [E][ ] project meeting (from: Dec 03 2019 to: Dec 04 2019)\n",
                logic.showTasks());
    }

    @Test
    void createEvent_dateAndTime_addsEventWithDateTimeDisplay() throws Exception {
        String result = logic.createEvent("project meeting", DateTimeUtil.parse("2019-12-03 0900"),
                DateTimeUtil.parse("2019-12-03 1100"));

        assertEquals("> Dobby noted a new Event: project meeting from Dec 03 2019, 09:00 to Dec 03 2019, 11:00",
                result);
        assertEquals("> Dobby show 1 tasks:\n1. [E][ ] project meeting (from: Dec 03 2019, 09:00"
                + " to: Dec 03 2019, 11:00)\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_markTask_marksTaskDone() {
        logic.createToDo("read book");

        String result = logic.changeTaskStatus(1, true);

        assertEquals("> Dobby will mark this as done!\n   [T][X] read book", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][X] read book\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_unmarkTask_marksTaskNotDone() {
        logic.createToDo("read book");
        logic.changeTaskStatus(1, true);

        String result = logic.changeTaskStatus(1, false);

        assertEquals("> Dobby will mark this as not done!\n   [T][ ] read book", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_invalidIndex_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.changeTaskStatus(2, true);

        assertEquals("> Dobby is confused. Dobby can't find task 2", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_nonPositiveIndex_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.changeTaskStatus(0, true);

        assertEquals("> Dobby is confused. Dobby can't find task 0", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_negativeIndex_errorAndTasksUnchanged() {
        logic.createToDo("read book");

        String result = logic.changeTaskStatus(-1, true);

        assertEquals("> Dobby is confused. Dobby can't find task -1", result);
        assertEquals("> Dobby show 1 tasks:\n1. [T][ ] read book\n", logic.showTasks());
    }

    @Test
    void changeTaskStatus_emptyTaskList_error() {
        String result = logic.changeTaskStatus(1, true);

        assertEquals("> Dobby is confused. Dobby can't find task 1", result);
        assertEquals("> Dobby show 0 tasks:\n", logic.showTasks());
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
