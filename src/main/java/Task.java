/**
 * General Task class to be stored in DobbyLogic.java
 */
public class Task {
    String description = "";

    /**
     * Initiate a task with its description
     * @param str Description
     */
    public Task(String str) {
        this.description = str;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
