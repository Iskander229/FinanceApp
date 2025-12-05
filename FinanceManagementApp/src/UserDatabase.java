import java.io.*;
import java.util.*;

// SINGLETON PATTERN: Only one instance of UserDatabase exists
public class UserDatabase {
    private static UserDatabase instance;  // Single instance
    private Map<String, User> users = new HashMap<>();
    private static final String FILE_NAME = "users.dat";

    // Private constructor prevents external instantiation
    private UserDatabase() {
        System.out.println("UserDatabase instance created.");
    }

    // Global access point to the single instance
    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    // Add user and auto-save
    public void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    // Authenticate user
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Check if user exists
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // Save users to file (persistence)
    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            System.out.println("Users saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }

    // Load users from file (persistence)
    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved users found.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {
            users = (Map<String, User>) ois.readObject();
            System.out.println("Loaded " + users.size() + " users.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }
}