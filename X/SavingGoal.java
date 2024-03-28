package X;
// This is the SavingGoal class

public class SavingGoal {
    private String name;
    private double goal;
    private double balance;

    // Constructor
    public SavingGoal(String name, double goal, double balance) {
        if (goal < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.name = name;
        this.goal = goal;
        this.balance = balance;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public double getGoal() {
        return this.goal;
    }

    public double getBalance() {
        return this.balance;
    }

    // Setters
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getSavingGoal() == this) {
            this.balance += transaction.getAmount();
        }
    }

    // Override the toString method
    // Want anders staat er een @SavingGoal in de overview
    @Override
    public String toString() {
        return this.name;
    }
}
