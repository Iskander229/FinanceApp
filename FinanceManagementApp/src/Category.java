import java.io.Serializable;

class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double income = 0;
    private double expense = 0;

    public Category(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public double getIncome() { return income; }
    public double getExpense() { return expense; }
    public void addIncome(double amount) { income += amount; }
    public void addExpense(double amount) { expense += amount; }
    public void clearValues() { income = 0; expense = 0; }  // This was missing
}