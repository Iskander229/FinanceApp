import java.io.Serializable;
import java.util.*;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private List<Category> categories = new ArrayList<>();
    // Remove observers for now to simplify serialization
    // private transient List<UserObserver> observers = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Remove observer methods for now
    /*
    public void attach(UserObserver observer) {
        observers.add(observer);
    }

    public void detach(UserObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (UserObserver observer : observers) {
            observer.update(this, message);
        }
    }
    */

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public List<Category> getCategories() { return categories; }

    public void addCategory(Category category) {
        categories.add(category);
        // notifyObservers("Category '" + category.getName() + "' added");
    }

    public void clearValues() {
        for (Category c : categories) c.clearValues();
        // notifyObservers("All values cleared");
    }

    public void clearAll() {
        categories.clear();
        // notifyObservers("All categories deleted");
    }

    public Category getTopIncomeCategory() {
        if (categories.isEmpty()) return null;
        return categories.stream()
                .max(Comparator.comparingDouble(Category::getIncome))
                .orElse(categories.get(0));
    }

    public Category getTopExpenseCategory() {
        if (categories.isEmpty()) return null;
        return categories.stream()
                .max(Comparator.comparingDouble(Category::getExpense))
                .orElse(categories.get(0));
    }
}