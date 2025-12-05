// FACTORY PATTERN: Centralizes object creation logic
public class CategoryFactory {

    // Factory method 1: Basic category
    public Category createCategory(String name) {
        System.out.println("[Factory] Creating basic category: " + name);
        return new Category(name);
    }

    // Factory method 2: Category with initial values
    public Category createCategory(String name, double initialIncome,
                                   double initialExpense) {
        System.out.println("[Factory] Creating category with initial values: " + name);
        Category category = new Category(name);
        category.addIncome(initialIncome);
        category.addExpense(initialExpense);
        return category;
    }

    // Factory method 3: Special category types (extensible)
    public Category createBudgetCategory(String name, double budgetLimit) {
        System.out.println("[Factory] Creating budget category: " + name);
        Category category = new Category(name);
        // Could add budget-specific logic here
        return category;
    }
}