package X;

import java.util.List;
import java.util.Scanner;

// This is the SpaarRekening class
public class SpaarRekening extends BankAccount {

    // Constructor
    public SpaarRekening() {
        super();
    }

    // Override the deductAmount method
    @Override
    public void deductAmount(double amount) {
    }

    // Override the addTransaction method
    // Dit is de implementatie voor de SpaarRekening
    // Deze is anders want we moeten de balance van de spaardoelen aanpassen
    @Override
    public void addTransaction(Transaction transaction) {
        SavingGoal selectedGoal = transaction.getSavingGoal();

        if (selectedGoal != null) {
            // Add the deposit amount to the selected saving goal's balance
            selectedGoal.setBalance(selectedGoal.getBalance() + transaction.getAmount());
            Bank.printMessage("\u001B[32m€ " + Math.abs(transaction.getAmount()) +
                    " processed for the selected Saving Goal: " + selectedGoal.getName() + "\u001B[0m");
        } else {
            // Handle if no saving goal is selected
            Bank.printMessage("\u001B[32mTransaction added but not associated with a specific Saving Goal.\u001B[0m");
        }
    }

    // Voeg een spaardoel toe aan de spaarrekening
    public static void addSavingGoal(Scanner scanner, User loggedInUser) {
        Bank.printMessage("");
        Bank.printMessage("Enter saving goal details:");
        Bank.printMessage("-------------------------");
        System.out.print("Name: ");
        String goalName = Bank.readStringInput(scanner);
        System.out.print("Amount: ");
        double goalAmount = Bank.readDoubleInput(scanner);

        SavingGoal newGoal = new SavingGoal(goalName, goalAmount, 0.0);
        loggedInUser.getSpaarRekening().addSavingGoal(newGoal); // Add saving goal for the logged-in user

        Bank.printMessage("");
        Bank.printMessage("Saving goal added for user " + loggedInUser.getUsername() + ". Details below..");
        Bank.printMessage("Name: " + goalName + " | Amount: " + goalAmount);
    }

    // Delete a saving goal from the spaarrekening
    public static void deleteSavingGoal(Scanner scanner, User loggedInUser) {
        Bank.printMessage("");
        Bank.printMessage("Select a savings goal:");
        Bank.printMessage("0. None (No savings goal)");
        List<SavingGoal> DepositSavingGoals = loggedInUser.getSpaarRekening().getSavingGoals();
        for (int i = 0; i < DepositSavingGoals.size(); i++) {
            Bank.printMessage((i + 1) + ". " + DepositSavingGoals.get(i).getName()
                    + " Amount: €" + DepositSavingGoals.get(i).getGoal());
        }

        Bank.printMessage("");
        System.out.print("Select savings goal (Enter the corresponding number): ");
        int selectedGoalIndex = Bank.readIntegerInput(scanner);

        if (selectedGoalIndex == 0) {
            Bank.printMessage("");
            Bank.printMessage("Canceled");
        } else if (selectedGoalIndex > 0 && selectedGoalIndex <= DepositSavingGoals.size()) {
            SavingGoal goalToDelete = DepositSavingGoals.get(selectedGoalIndex - 1);
            loggedInUser.getSpaarRekening().getSavingGoals().remove(goalToDelete);
            Bank.printMessage("");
            Bank.printMessage("Saving goal '" + goalToDelete.getName() + "' deleted successfully.");
        } else {
            Bank.printMessage("");
            Bank.printMessage("Saving goal not found.");
        }
    }
}
