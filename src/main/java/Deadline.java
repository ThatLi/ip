public class Deadline extends Task {
    protected String by;

    public Deadline(String str, String by) {
        super(str, "D");
        this.by = by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
