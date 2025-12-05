import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDatabase userDatabase = UserDatabase.getInstance();
    private static User currentUser = null;
    private static final CategoryFactory categoryFactory = new CategoryFactory();

    public static void main(String[] args) {
        // Load users from file
        userDatabase.loadUsers();

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

            switch (choice) {
                case "1": manageCategories(); break;
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

    private static void manageCategories() {
        List<Category> categories = currentUser.getCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        while (true) {
            System.out.println("\n=== Categories ===");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ") " + categories.get(i).getName());
            }
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) return;

            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < categories.size()) {
                    categoryMenu(categories.get(index));
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + categories.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void categoryMenu(Category category) {
        while (true) {
            System.out.println("\n=== " + category.getName() + " ===");
            System.out.println("1) Add Income");
            System.out.println("2) Add Expense");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Amount: ");
                    double income = readDouble();
                    category.addIncome(income);
                    System.out.println("Income added.");
                    break;
                case "2":
                    System.out.print("Amount: ");
                    double expense = readDouble();
                    category.addExpense(expense);
                    System.out.println("Expense added.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
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
            for (Category c : currentUser.getCategories()) {
                c.clearValues();
            }
            System.out.println("All category values cleared.");
        } else if (choice.equals("B")) {
            currentUser.getCategories().clear();
            System.out.println("All categories deleted.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void showDiagram() {
        List<Category> categories = currentUser.getCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        Category topIncome = null;
        Category topExpense = null;
        double maxIncome = -1;
        double maxExpense = -1;

        for (Category cat : categories) {
            if (cat.getIncome() > maxIncome) {
                maxIncome = cat.getIncome();
                topIncome = cat;
            }
            if (cat.getExpense() > maxExpense) {
                maxExpense = cat.getExpense();
                topExpense = cat;
            }
        }

        System.out.println("\n=== TOP CATEGORIES ===");
        System.out.println("Top Income: " +
                (topIncome != null ? topIncome.getName() + " -> " + topIncome.getIncome() : "None"));
        System.out.println("Top Expense: " +
                (topExpense != null ? topExpense.getName() + " -> " + topExpense.getExpense() : "None"));
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}