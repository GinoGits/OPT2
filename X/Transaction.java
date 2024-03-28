package X;

// This is the Transaction class
public class Transaction {
    private double amount;
    private String date;
    private String description;
    private Category category;
    private SavingGoal savingGoal;

    // Constructor
    public Transaction(double amount, String date, String description, Category category, SavingGoal savingGoal) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.savingGoal = savingGoal;
    }

    // Getters
    public double getAmount() {
        return this.amount;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public SavingGoal getSavingGoal() {
        return this.savingGoal;
    }

    public Category getCategory() {
        return this.category;
    }

    // if the category or saving goal is null, return N/A
    // Anders staat er null in de overview
    public String getSavingGoalName() {
        return (savingGoal != null) ? savingGoal.getName() : "N/A";
    }

    public String getCategoryName() {
        return (category != null) ? category.getName() : "N/A";
    }
}
