package X;

import java.util.ArrayList;
import java.util.List;

// This is the abstract class for BankAccount
public abstract class BankAccount {
    protected List<Transaction> transactions;
    private List<Category> categories;
    protected List<SavingGoal> savingGoals;

    // Constructor
    public BankAccount() {
        this.transactions = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.savingGoals = new ArrayList<>();
    }

    // Getters
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public List<SavingGoal> getSavingGoals() {
        return this.savingGoals;
    }

    // Setters
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addSavingGoal(SavingGoal savingGoal) {
        savingGoals.add(savingGoal);
    }

    // Abstract methods
    public abstract void deductAmount(double amount);
}
