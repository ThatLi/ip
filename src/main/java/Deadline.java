public class Deadline extends Task {
    protected String by;

    public Deadline(String str, String by) {
        super(str, "D");
        this.by = by;
    }

    /** Returns this deadline in the format used by the task data file. */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
