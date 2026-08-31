public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String str, String from, String to) {
        super(str, "E");
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
