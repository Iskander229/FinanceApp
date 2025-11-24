class Category {
    //category class contains the name, income, and expenses of a certain category that is added in user's array then.
    private String name;
    private double income = 0;
    private double expense = 0;

    public Category(String name) {
        this.name = name;
    }

    // I think this is all understandable.
    public String getName() { return name; }
    public double getIncome() { return income; }
    public double getExpense() { return expense; }

    public void addIncome(double amount) { income += amount; }
    public void addExpense(double amount) { expense += amount; }

    public void clearValues() { income = 0; expense = 0; }
}