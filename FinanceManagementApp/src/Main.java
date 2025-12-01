import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDatabase userDatabase = UserDatabase.getInstance();
    private static User currentUser = null;
    private static final CategoryFactory categoryFactory = new CategoryFactory();

    public static void main(String[] args) {
        System.out.println("=== Personal Finance App ===");
        System.out.println("Database location: " + userDatabase.getDatabasePath());

        // Load users from file
        userDatabase.loadUsers();

        // Test: Create a test user if none exist
        if (!userDatabase.userExists("test")) {
            System.out.println("Creating test user...");
            User testUser = new User("test", "test123");
            userDatabase.addUser(testUser);
        }

        boolean running = true;
        while (running) {
            loginMenu();
            if (currentUser != null) {
                mainMenu();
            }
        }
    }

    private static void loginMenu() {
        while (currentUser == null) {
            System.out.println("\n=== LOGIN MENU ===");
            System.out.println("1) Login");
            System.out.println("2) Create Account");
            System.out.println("3) Exit Program");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": login(); break;
                case "2": createAccount(); break;
                case "3":
                    userDatabase.saveUsers();
                    System.exit(0);
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Choose username: ");
        String username = sc.nextLine();

        if (userDatabase.userExists(username)) {
            System.out.println("Username already exists.");
            return;
        }

        System.out.print("Choose password: ");
        String password = sc.nextLine();

        User newUser = new User(username, password);
        userDatabase.addUser(newUser);
        System.out.println("Account created successfully. Please login.");
    }

    private static void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        currentUser = userDatabase.authenticate(username, password);
        if (currentUser != null) {
            System.out.println("Logged in successfully!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void mainMenu() {
        while (currentUser != null) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Manage Categories");
            System.out.println("2) Create Category");
            System.out.println("3) Clear Start");
            System.out.println("4) Show Diagram");
            System.out.println("5) Log Out");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            CategoryManager categoryManager = new CategoryManager(currentUser);

            switch (choice) {
                case "1": categoryManager.manageCategories(sc); break;
                case "2":
                    System.out.print("Category name: ");
                    String name = sc.nextLine();
                    Category category = categoryFactory.createCategory(name);
                    currentUser.addCategory(category);
                    System.out.println("Category created.");
                    break;
                case "3": clearStart(); break;
                case "4": showDiagram(); break;
                case "5":
                    userDatabase.saveUsers();
                    currentUser = null;
                    break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void clearStart() {
        System.out.println("\n=== CLEAR START ===");
        System.out.println("A) Clear ONLY incomes & expenses");
        System.out.println("B) Clear EVERYTHING (delete categories)");
        System.out.print("Choose: ");
        String choice = sc.nextLine().toUpperCase();

        if (choice.equals("A")) {
            currentUser.clearValues();
            System.out.println("All category values cleared.");
        } else if (choice.equals("B")) {
            currentUser.clearAll();
            System.out.println("All categories deleted.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void showDiagram() {
        if (currentUser.getCategories().isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        Category topIncome = currentUser.getTopIncomeCategory();
        Category topExpense = currentUser.getTopExpenseCategory();

        System.out.println("\n=== TOP CATEGORIES ===");
        System.out.println("Top Income: " + topIncome.getName() + " -> " + topIncome.getIncome());
        System.out.println("Top Expense: " + topExpense.getName() + " -> " + topExpense.getExpense());
    }
}