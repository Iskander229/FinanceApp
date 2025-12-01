import java.io.*;
import java.util.*;

public class UserDatabase {
    private static UserDatabase instance;
    private Map<String, User> users = new HashMap<>();
    private String filePath;

    private UserDatabase() {
        initializeFilePath();
        loadUsers();
    }

    private void initializeFilePath() {
        // Use user's home directory for persistent storage
        String userHome = System.getProperty("user.home");
        String appDirPath = userHome + File.separator + ".personal_finance_app";

        File appDir = new File(appDirPath);
        if (!appDir.exists()) {
            appDir.mkdirs(); // Create directory if it doesn't exist
        }

        filePath = appDirPath + File.separator + "users.dat";
        System.out.println("Database location: " + filePath);
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(User user) {
        System.out.println("Adding user: " + user.getUsername());
        users.put(user.getUsername(), user);
        saveUsers();
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public void saveUsers() {
        System.out.println("Saving " + users.size() + " users to: " + filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No existing user database found at: " + filePath);
            return;
        }

        System.out.println("Loading users from: " + filePath);

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                users = (Map<String, User>) obj;
                System.out.println("Successfully loaded " + users.size() + " users.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // For debugging
    public String getDatabasePath() {
        return filePath;
    }
}