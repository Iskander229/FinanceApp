import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double income = 0;
    private double expense = 0;
    private List<Transaction> transactions = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public double getIncome() { return income; }
    public double getExpense() { return expense; }
    public List<Transaction> getTransactions() { return transactions; }

    public void addTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.INCOME) {
            income += transaction.getAmount();
        } else {
            expense += transaction.getAmount();
        }
        transactions.add(transaction);
    }

    public void addIncome(double amount) {
        addTransaction(new IncomeTransaction(amount));
    }

    public void addExpense(double amount) {
        addTransaction(new ExpenseTransaction(amount));
    }

    public void clearValues() {
        income = 0;
        expense = 0;
        transactions.clear();
    }
}