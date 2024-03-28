package X;

import java.util.List;
import java.util.Scanner;

// This is the Rekening class
public class Rekening extends BankAccount {
    private double balance;

    // Constructor
    public Rekening() {
        super();
    }

    public double getBalance() {
        return this.balance;
    }

    // Override the deductAmount method
    // Want de balance moet aangepast worden
    @Override
    public void deductAmount(double amount) {
        this.balance -= amount;
    }

    // Override the addTransaction method
    // Want we voegen een trasanctie toe aan rekening
    // Spaarreknening heeft een andere implementatie
    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        double transactionAmount = transaction.getAmount();
        if (transactionAmount >= 0) { // Check if it's a deposit
            this.balance += transactionAmount;
            Bank.printMessage("\u001B[32mDeposit of € " + Math.abs(transaction.getAmount()) +
                    " processed for the Rekening account.\u001B[0m");

        } else { // Deduct for withdrawal (Safety check)
            deductAmount(Math.abs(transactionAmount));
            Bank.printMessage("\u001B[32mWithdraw of € " + Math.abs(transaction.getAmount()) +
                    " processed for the Rekening account.\u001B[0m");

        }
    }

    // Voeg een nieuwe categorie toe aan de rekening
    public static void addCategory(Scanner scanner, User loggedInUser) {
        Rekening rekening = loggedInUser.getRekening();

        Bank.printMessage("");
        Bank.printMessage("Enter the name of the new category:");
        String newCategoryName = Bank.readStringInput(scanner);

        Category newCategory = new Category(newCategoryName);
        rekening.addCategory(newCategory);

        Bank.printMessage("");
        Bank.printMessage("Category '" + newCategoryName + "' added successfully.");
    }

    // Delete a category from the rekening
    public static void deleteCategory(Scanner scanner, User loggedInUser) {
        Rekening rekening = loggedInUser.getRekening();
        List<Category> categories = rekening.getCategories();
        Bank.printMessage("");
        Bank.printMessage("Select a category:");
        Bank.printMessage("0. None (No category)");

        for (int i = 0; i < categories.size(); i++) {
            Bank.printMessage((i + 1) + ". " + categories.get(i).getName());
        }

        Bank.printMessage("");
        System.out.print("Select category (Enter the corresponding number): ");
        int selectedCategoryIndex = Bank.readIntegerInput(scanner);

        if (selectedCategoryIndex == 0) {
            Bank.printMessage("");
            Bank.printMessage("Canceled");
        } else if (selectedCategoryIndex > 0 && selectedCategoryIndex <= categories.size()) {
            Category selectedCategory = categories.get(selectedCategoryIndex - 1);
            rekening.getCategories().remove(selectedCategory);
            Bank.printMessage("");
            Bank.printMessage("Category '" + selectedCategory.getName() + "' deleted successfully.");
        } else {
            Bank.printMessage("");
            Bank.printMessage("Category not found.");
        }
    }

}
