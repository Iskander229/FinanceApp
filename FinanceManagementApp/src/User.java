import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class User {

//user methods

    private String username;
    private String password;

    //Constructor. creates a new user in memory
    public User(String u, String p) {
        this.username = u;
        this.password = p;
        //its categories list is not assigned (empty) by default.
    } //user is in memory, but it’s only stored when we call "Users.put" in Main class

    public String getPassword(){
        return password;
    }

//Categories methods.

    //store objects from Category class in "categories" var
    private List<Category> categories = new ArrayList<>();

    //returns the list of categories for logged user.
    public List<Category> getCategories(){
        return categories;
    }

    //adds new category to logged user's list.
    public void addCategory(Category c){
        categories.add(c);
    }

    //clears Categories income and expenses of logged user.
    public void clearValues() {
        //loops every Category class' object and calls its "Clear values" method
        for (Category c : categories) c.clearValues();
    }

    //clears categories of logged user.
    public void clearAll() { categories.clear(); } // just clears categories array created above.

    //returns category with highest income, or first category existing.
    public Category getTopIncomeCategory() {
        return categories.stream().max(Comparator.comparingDouble(Category::getIncome)).orElse(categories.get(0));
    }

    //returns category with highest expenses, or first category existing
    public Category getTopExpenseCategory() {
        return categories.stream().max(Comparator.comparingDouble(Category::getExpense)).orElse(categories.get(0));
    }
}