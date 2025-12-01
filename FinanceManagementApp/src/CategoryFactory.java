public class CategoryFactory {
    public Category createCategory(String name) {
        return new Category(name);
    }

    public Category createCategory(String name, double initialIncome, double initialExpense) {
        Category category = new Category(name);
        category.addIncome(initialIncome);
        category.addExpense(initialExpense);
        return category;
    }
}