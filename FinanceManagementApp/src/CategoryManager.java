import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CategoryManager {
    private User user;

    public CategoryManager(User user) {
        this.user = user;
    }

    public void manageCategories(Scanner sc) {
        List<Category> categories = user.getCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        while (true) {
            System.out.println("\n=== Categories ===");

            // Use for-each loop or traditional loop
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ") " + categories.get(i).getName());
            }

            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) {
                return;
            }

            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < categories.size()) {
                    categoryMenu(categories.get(index), sc);
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + categories.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void categoryMenu(Category category, Scanner sc) {
        while (true) {
            System.out.println("\n=== " + category.getName() + " ===");
            System.out.println("1) Add Income");
            System.out.println("2) Add Expense");
            System.out.println("3) View Transactions");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Amount: ");
                    double income = readDouble(sc);
                    category.addIncome(income);
                    System.out.println("Income added.");
                    break;
                case "2":
                    System.out.print("Amount: ");
                    double expense = readDouble(sc);
                    category.addExpense(expense);
                    System.out.println("Expense added.");
                    break;
                case "3":
                    viewTransactions(category);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewTransactions(Category category) {
        List<Transaction> transactions = category.getTransactions();
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n=== Transactions ===");
        for (Transaction t : transactions) {
            String type = t.getType() == TransactionType.INCOME ? "Income" : "Expense";
            System.out.println(type + ": " + t.getAmount());
        }
    }

    private double readDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            } catch (NoSuchElementException e) {
                System.out.print("No input available. Please enter a number: ");
                sc = new Scanner(System.in);
            }
        }
    }
}