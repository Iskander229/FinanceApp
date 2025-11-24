import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in); //to help handle user inputs
    //hash map to save users name and password.
    private static final Map<String, User> users = new HashMap<>(); //string - username, User object is value.
    private static User currentUser = null;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            loginMenu();
            mainMenu();
            //We can make running=false logic because right now it is
            //infinite, or keep using system.exit in LoginMenu()...
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
                case "3": System.exit(0);
                default: System.out.println("Invalid.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Choose username: ");
        String u = sc.nextLine(); // save username
        //check if Users hashmap contains such username
        if (users.containsKey(u)) {
            System.out.println("User exists.");
            return;
        }
        System.out.print("Choose password: ");
        String p = sc.nextLine(); //save password

        // now put this user into Users hashmap using "new User" constructor (see in User class),
        users.put(u, new User(u, p));
        System.out.println("Account created. Now login.");
    }

    private static void login() {
        System.out.print("Username: ");
        String u = sc.nextLine(); //nextline reads whole line until enter pressed
        System.out.print("Password: ");
        String p = sc.nextLine();

        //check from User class if username(u) and password(p) exists
        if (users.containsKey(u) && users.get(u).getPassword().equals(p)) {
            currentUser = users.get(u); //assign user to operate main menu.
            System.out.println("Logged in!");
        } else {
            System.out.println("Wrong login.");
        }
    }

    //now when user is assigned:
    private static void mainMenu() {
        while (currentUser != null) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Manage Categories");
            System.out.println("2) Create Category");
            System.out.println("3) Clear Start");
            System.out.println("4) Show Diagram");
            System.out.println("5) Log Out");
            System.out.print("Choose: ");
            String c = sc.nextLine(); //reads input until enter pressed

            switch (c) {
                case "1": manageCategories(); break;
                case "2": createCategory(); break;
                case "3": clearStart(); break;
                case "4": showDiagram(); break;
                case "5": currentUser = null; break; //log out user
                default: System.out.println("Invalid.");
            }
        }
    }

    //considering user is still assigned
    private static void createCategory() {
        System.out.print("Category name: ");
        String name = sc.nextLine();
        currentUser.addCategory(new Category(name)); //add category to logged user
        System.out.println("Category created.");
    }
    //considering user is still assigned
    private static void manageCategories() {
        if (currentUser.getCategories().isEmpty()) { //check categories of user assigned
            System.out.println("No categories.");
            return;
        }

        while (true) {
            System.out.println("\n=== Categories ===");
            List<Category> list = currentUser.getCategories(); //get logged user's categories list
            //add his categories to the numbered choose list
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i+1) + ") " + list.get(i).getName());
            }
            //last options in this list.
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = sc.nextLine(); // read input until enter pressed

            if (c.equals("0")) return; //exit loop
            //check for invalid index
            int idx;
            try { idx = Integer.parseInt(c) - 1; }
            catch (Exception e) { System.out.println("Invalid."); continue; }

            if (idx < 0 || idx >= list.size()) {
                System.out.println("Invalid.");
                continue;
            }

            categoryMenu(list.get(idx));
        }
    }

    //considering user is still assigned
    private static void categoryMenu(Category cat) { //cat is a certain category chosen by user to manage.
        while (true) {
            System.out.println("\n=== " + cat.getName() + " ==="); //you are in this category
            System.out.println("1) Add Income");
            System.out.println("2) Add Expense");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = sc.nextLine();

            switch (c) {
                case "1":
                    System.out.print("Amount: ");
                    cat.addIncome(readDouble()); //add income to this cat
                    System.out.println("Income added.");
                    break;
                case "2":
                    System.out.print("Amount: ");
                    cat.addExpense(readDouble()); //add expense to this cat
                    System.out.println("Expense added.");
                    break;
                case "0": return;
                default: System.out.println("Invalid.");
            }
        }
    }

    private static void clearStart() {
        System.out.println("\n=== CLEAR START ===");
        System.out.println("A) Clear ONLY incomes & expenses");
        System.out.println("B) Clear EVERYTHING (delete categories)");
        System.out.print("Choose: ");
        String c = sc.nextLine().toUpperCase(); // to handle any case Upper or Lower...

        if (c.equals("A")) {
            currentUser.clearValues(); //use User class' clear values method
            System.out.println("All category values cleared.");
        } else if (c.equals("B")) {
            currentUser.clearAll(); //use User class' clear all method
            System.out.println("All categories deleted.");
        } else {
            System.out.println("Invalid.");
        }
    }

    private static void showDiagram() {
        //if assigned user has no categories created.
        if (currentUser.getCategories().isEmpty()) {
            System.out.println("No categories.");
            return;
        }

        Category topIncome = currentUser.getTopIncomeCategory(); //use User class' gettopincome method
        Category topExpense = currentUser.getTopExpenseCategory(); //use User class' gettopexpense method

        System.out.println("\n=== TOP CATEGORIES ===");
        System.out.println("Top Income: " + topIncome.getName() + " -> " + topIncome.getIncome());
        System.out.println("Top Expense: " + topExpense.getName() + " -> " + topExpense.getExpense());
    }

    //check if input is a number
    private static double readDouble() {
        while (true) { //keep asking until we get valid input
            try { return Double.parseDouble(sc.nextLine()); } //try to convert  input to double
            catch (Exception e) { System.out.print("Enter number: "); } // ask number.
        }
    }
}


