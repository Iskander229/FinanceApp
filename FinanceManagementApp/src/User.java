import java.io.Serializable;
import java.util.*;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private List<Category> categories = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Category> getCategories() { return categories; }
    public void addCategory(Category category) { categories.add(category); }
}