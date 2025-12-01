import java.io.Serializable;

enum TransactionType {
    INCOME, EXPENSE
}

abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    protected double amount;
    protected TransactionType type;

    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
    }

    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
}

class IncomeTransaction extends Transaction {
    private static final long serialVersionUID = 1L;
    public IncomeTransaction(double amount) {
        super(amount, TransactionType.INCOME);
    }
}

class ExpenseTransaction extends Transaction {
    private static final long serialVersionUID = 1L;
    public ExpenseTransaction(double amount) {
        super(amount, TransactionType.EXPENSE);
    }
}