public class Doctor {
    private String firstLine;
    private String secondLine;

    public Doctor(String firstLine, String secondLine) {
        this.firstLine = firstLine;
        this.secondLine = secondLine;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    @Override
    public String toString() {
        return firstLine + "  " + secondLine;
    }
}
