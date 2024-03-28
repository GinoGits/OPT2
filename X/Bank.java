package X;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Bank {
    private List<User> users = new ArrayList<>(); // List of all users in the system
    private int userIdCounter = 1; // Counter for assigning unique user IDs

    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create an initial user for testing purposes
        User initialUser = new User("gino", 1,
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
        initialUser.createAccount();
        bank.users.add(initialUser);

        printMessage("");
        printMessage("");
        printMessage("\u001B[35mWelcome to the Banking App!\u001B[0m");
        printMessage("");
        printMessage("");

        // Start the app
        bank.startApp();
    }

    public void startApp() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Main menu
        while (running) {
            printMessage("Select an option:");
            printMessage("1. Create an account");
            printMessage("2. Login");
            printMessage("3. Exit\n");

            int choice = readIntegerInput(scanner);
            printMessage("");

            switch (choice) {
                case 1:
                    createUserAccount(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    printMessage("Goodbye!");
                    running = false;
                    break;
                default:
                    printMessage("Invalid choice. Please try again.\n");
            }
        }

        scanner.close();
    }

    private void createUserAccount(Scanner scanner) {
        User newUser = User.createUser(scanner, userIdCounter);
        printMessage("");
        if (newUser != null) {
            users.add(newUser);
            // Create new account for the user
            newUser.createAccount();
            userIdCounter++;
        }
    }

    private void loginUser(Scanner scanner) {
        // Check if there are any users in the system
        User loggedInUser = User.login(scanner, users.toArray(new User[users.size()]));

        // If the user is logged in, show the user menu
        if (loggedInUser != null) {
            boolean userMenuRunning = true;
            while (userMenuRunning) {
                showOptionsMenu();
                printMessage("");
                int userChoice = readIntegerInput(scanner);
                printMessage("");

                // Handle user choice
                switch (userChoice) {

                    case 1:
                        // view balance
                        printMessage("Your balance is: €" + loggedInUser.getRekening().getBalance());
                        printMessage("");
                        break;

                    case 2:
                        // view saving goals
                        List<SavingGoal> savingGoals = loggedInUser.getSpaarRekening().getSavingGoals();

                        if (savingGoals == null || savingGoals.isEmpty()) {
                            printMessage("You have no saving goals set up.");
                        } else {
                            printMessage("Your Saving Goals:");
                            printMessage("-------------------------");
                            for (SavingGoal goal : savingGoals) {
                                double goalBalance = goal.getGoal() - goal.getBalance();
                                printMessage("Goal Name: " + goal.getName() + " | Currently: €"
                                        + goal.getBalance() + " | Goal Amount: €"
                                        + goal.getGoal() + " | Needed: €" + goalBalance);
                            }
                        }
                        break;

                    case 3:
                        // deposit
                        Boolean depositToAccount = true;
                        printMessage("Enter transaction details:");
                        printMessage("-------------------------");
                        System.out.print("Amount: €");
                        double depositAmount = readDoubleInput(scanner);
                        if (depositAmount < 0) {
                            throw new IllegalArgumentException("Invalid amount. Amount cannot be negative.");
                        }
                        LocalDate depositDateToday = LocalDate.now();
                        String depositDate = depositDateToday.toString();
                        System.out.print("Description: ");
                        String depositDescription = readStringInput(scanner);

                        List<Category> categories = loggedInUser.getRekening().getCategories();
                        printMessage("");
                        printMessage("Select a category:");
                        printMessage("0. None (No category)");

                        for (int i = 0; i < categories.size(); i++) {
                            printMessage((i + 1) + ". " + categories.get(i).getName());
                        }

                        printMessage("");
                        System.out.print("Select category (Enter the corresponding number): ");
                        int selectedCategoryIndex = readIntegerInput(scanner);

                        Category selectedCategory = null;
                        if (selectedCategoryIndex > 0 && selectedCategoryIndex <= categories.size()) {
                            selectedCategory = categories.get(selectedCategoryIndex - 1);
                        }

                        printMessage("");
                        printMessage("Select a destination:");
                        printMessage("0. Account | Currently: €" + loggedInUser.getRekening().getBalance());
                        List<SavingGoal> DepositSavingGoals = loggedInUser.getSpaarRekening().getSavingGoals();
                        for (int i = 0; i < DepositSavingGoals.size(); i++) {
                            printMessage((i + 1) + ". " + DepositSavingGoals.get(i).getName()
                                    + " Amount: €" + DepositSavingGoals.get(i).getGoal());
                        }

                        printMessage("");
                        System.out.print("Select savings goal (Enter the corresponding number): ");
                        int selectedGoalIndex = readIntegerInput(scanner);

                        SavingGoal selectedGoal = null;
                        if (selectedGoalIndex > 0 && selectedGoalIndex <= DepositSavingGoals.size()) {
                            selectedGoal = DepositSavingGoals.get(selectedGoalIndex - 1);
                            depositToAccount = false;
                        }

                        Transaction newDeposit = new Transaction(depositAmount, depositDate,
                                depositDescription, selectedCategory, selectedGoal);

                        printMessage("");
                        printMessage("\u001B[33mTransaction details");
                        printMessage("-------------------------");
                        printMessage("Amount: €" + depositAmount);
                        printMessage("Date: " + depositDate);
                        printMessage("Description: " + depositDescription);
                        printMessage("Category: " + (selectedCategory != null ? selectedCategory : "N/A"));
                        printMessage("Saving Goal: " + (selectedGoal != null ? selectedGoal : "N/A") + "\u001B[0m");
                        printMessage("");

                        if (depositToAccount) {
                            loggedInUser.getRekening().addTransaction(newDeposit);
                        } else if (!depositToAccount) {
                            if (selectedGoal != null) {
                                loggedInUser.getSpaarRekening().addTransaction(newDeposit);
                            } else {
                                printMessage("Invalid savings goal selected. Skipping transaction.");
                            }
                        } else {
                            printMessage("Invalid account type.");
                        }

                        printMessage("");
                        break;

                    case 4:
                        // withdraw
                        boolean withdrawFromAccount = true;
                        printMessage("Enter transaction details:");
                        printMessage("-------------------------");
                        System.out.print("Amount: €");
                        double withdrawAmount = readDoubleInput(scanner);
                        if (withdrawAmount < 0) {
                            throw new IllegalArgumentException("Invalid amount. Amount cannot be negative.");
                        }
                        LocalDate withdrawDateToday = LocalDate.now();
                        System.out.print("Description: ");
                        String withdrawDescription = readStringInput(scanner);
                        printMessage("");

                        List<Category> categories2 = loggedInUser.getRekening().getCategories();
                        printMessage("");
                        printMessage("Select a category:");
                        printMessage("0. None (No category)");

                        for (int i = 0; i < categories2.size(); i++) {
                            printMessage((i + 1) + ". " + categories2.get(i).getName());
                        }

                        printMessage("");
                        System.out.print("Select category (Enter the corresponding number): ");
                        int selectedCategoryIndex2 = readIntegerInput(scanner);

                        Category selectedCategory2 = null;
                        if (selectedCategoryIndex2 > 0 && selectedCategoryIndex2 <= categories2.size()) {
                            selectedCategory2 = categories2.get(selectedCategoryIndex2 - 1);
                        }

                        printMessage("");
                        printMessage("Select a source:");
                        printMessage("0. Account | Currently: €" + loggedInUser.getRekening().getBalance());
                        List<SavingGoal> savingGoals2 = loggedInUser.getSpaarRekening().getSavingGoals();
                        for (int i = 0; i < savingGoals2.size(); i++) {
                            SavingGoal goal = savingGoals2.get(i);
                            printMessage((i + 1) + ". " + goal.getName() + " | Currently: €" + goal.getBalance() +
                                    " | Goal Amount: €" + goal.getGoal());
                        }
                        printMessage("");
                        System.out.print("Select savings goal (Enter the corresponding number): ");
                        int selectedGoalIndex2 = readIntegerInput(scanner);

                        SavingGoal selectedGoal2 = null;
                        if (selectedGoalIndex2 > 0 && selectedGoalIndex2 <= savingGoals2.size()) {
                            selectedGoal2 = savingGoals2.get(selectedGoalIndex2 - 1);
                            withdrawFromAccount = false;
                        }

                        printMessage("");
                        printMessage("\u001B[33mTransaction details");
                        printMessage("-------------------------");
                        printMessage("Amount: €" + withdrawAmount);
                        printMessage("Date: " + withdrawDateToday);
                        printMessage("Description: " + withdrawDescription);
                        printMessage("Category: " + (selectedCategory2 != null ? selectedCategory2 : "N/A"));
                        printMessage("Saving Goal: " + (selectedGoal2 != null ? selectedGoal2 : "N/A") + "\u001B[0m");
                        printMessage("");

                        double totalBalanceRekening = loggedInUser.getRekening().getBalance();
                        if (selectedGoal2 != null
                                && selectedGoal2.getGoal() - selectedGoal2.getBalance() < withdrawAmount) {
                            printMessage("Insufficient balance in the selected saving goal.");
                        } else if (withdrawAmount > totalBalanceRekening) {
                            printMessage("Insufficient balance in your account.");
                        } else {
                            Transaction newWithdraw = new Transaction(-withdrawAmount,
                                    LocalDate.now().toString(),
                                    withdrawDescription, selectedCategory2, selectedGoal2);

                            if (withdrawFromAccount) {
                                loggedInUser.getRekening().addTransaction(newWithdraw);
                            } else if (!withdrawFromAccount) {
                                loggedInUser.getSpaarRekening().addTransaction(newWithdraw);
                            } else {
                                printMessage("Invalid account type.");
                            }
                        }
                        break;

                    case 5:
                        // view categories
                        List<Category> rekeningCategories = loggedInUser.getRekening().getCategories();
                        if (rekeningCategories.isEmpty()) {
                            printMessage("You have no categories set up.");
                        } else {
                            printMessage("Categories: ");
                            for (Category category : rekeningCategories) {
                                printMessage(category.getName());
                            }
                        }
                        break;

                    case 6:
                        // settings
                        Settings(scanner, loggedInUser);
                        break;

                    case 7:
                        // logout
                        printMessage("Returning to Login Screen...");
                        printMessage("");
                        userMenuRunning = false;
                        break;

                    default:
                        printMessage("Invalid choice. Please try again.");
                }
            }
        }
    }

    private void Settings(Scanner scanner, User loggedInUser) {
        boolean optionsMenuRunning = true;
        while (optionsMenuRunning) {
            showSettingsMenu();
            int userChoice = readIntegerInput(scanner);
            printMessage("");
            switch (userChoice) {
                case 1:
                    // new username
                    User.changeUsername(scanner, loggedInUser);
                    break;

                case 2:
                    // new password
                    User.changePassword(scanner, loggedInUser);
                    break;

                case 3:
                    // delete account
                    boolean accountDeleted = User.deleteAccount(scanner, loggedInUser, users);
                    if (accountDeleted) {
                        loggedInUser = null; // Log out the user
                        printMessage("");
                        printMessage("");
                        printMessage("\u001B[35mWelcome to the Banking App!\u001B[0m");
                        printMessage("");
                        printMessage("");

                        startApp(); // Return to the initial main menu
                                    // // user
                        return; // Return to main method to start over
                    } else {
                        printMessage("Failed to delete the account. Please try again.\n");
                    }
                    break;

                case 4:
                    // new goal
                    SpaarRekening.addSavingGoal(scanner, loggedInUser);
                    break;

                case 5:
                    // delete goal
                    SpaarRekening.deleteSavingGoal(scanner, loggedInUser);
                    break;

                case 6:
                    // new category
                    Rekening.addCategory(scanner, loggedInUser);
                    break;

                case 7:
                    // delete category
                    Rekening.deleteCategory(scanner, loggedInUser);
                    break;

                case 8:
                    // back
                    optionsMenuRunning = false;
                    break;

                default:
                    printMessage("Invalid choice. Please try again.");
            }
        }
    }

    // Print een bericht naar de console
    public static void printMessage(String message) {
        try {
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("Error while printing the message: " + e.getMessage());
        }
    }

    // Laat de app opties zien die de gebruiker kan kiezen
    public static void showOptionsMenu() {
        printMessage("");
        printMessage("What do you wanna do?");
        printMessage("1. See balance");
        printMessage("2. See saving goals");
        printMessage("3. Add deposit");
        printMessage("4. Add withdraw");
        printMessage("5. View Categories");
        printMessage("6. Settings");
        printMessage("7. Logout");
    }

    // Laat de seetings opties zien die de gebruiker kan kiezen
    public static void showSettingsMenu() {
        printMessage("");
        printMessage("What do you wanna do?");
        printMessage("1. Change username");
        printMessage("2. Change password");
        printMessage("3. Delete account");
        printMessage("4. Make new savingsgoal");
        printMessage("5. Delete savingsgoal");
        printMessage("6. Make new category");
        printMessage("7. Delete category");
        printMessage("8. Back");
    }

    // Een veilige manier om String input te lezen
    public static String readStringInput(Scanner scanner) {
        String input = "";
        boolean validInput = false;

        while (!validInput) {
            try {
                input = scanner.nextLine().trim();
                validInput = true;
            } catch (NoSuchElementException e) {
                String message = "Invalid input. Please enter a valid text.";
                printMessage("");
                printMessage("\u001B[31m" + message + "\u001B[0m");
                printMessage("");
                scanner.next(); // Clear the input buffer
            }
        }

        return input;
    }

    // Een veilige manier om Double input te lezen
    public static double readDoubleInput(Scanner scanner) {
        double input = 0.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                input = Double.parseDouble(scanner.next());
                validInput = true;
                scanner.nextLine();
            } catch (NumberFormatException e) {
                String message = "Invalid input. Please enter a valid number.";
                printMessage("");
                printMessage("\u001B[31m" + message + "\u001B[0m");
                printMessage("");
                scanner.nextLine();
            }
        }

        return input;
    }

    // Een veilige manier om Integer input te lezen
    public static int readIntegerInput(Scanner scanner) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                input = Integer.parseInt(scanner.next());
                validInput = true;
                scanner.nextLine();
            } catch (NumberFormatException e) {
                String message = "Invalid input. Please enter a valid number.";
                printMessage("");
                printMessage("\u001B[31m" + message + "\u001B[0m");
                printMessage("");
                scanner.nextLine();
            }
        }

        return input;
    }
}
