public class Task {
    String details = "";

    public Task(String str) {
        this.details = str;
    }

    @Override
    public String toString() {
        return this.details;
    }
}
