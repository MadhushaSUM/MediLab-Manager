

public class finAnalysistest {
    private String name;
    private int number;
    private int cost;
    private int total;

    public finAnalysistest(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public finAnalysistest(String name, int number, int total) {
        this.name = name;
        this.number = number;
        this.total = total;
    }

    public void setNumber(int number) {
        this.number = number;
        this.total = number * cost;
    }

    public String getName() { return name; }
    public int getNumber() { return number; }
    public int getTotal() { return total; }
}
