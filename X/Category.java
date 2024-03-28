package X;

// This is the Category class
public class Category {
    private String name;

    // Constructor
    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Override the toString method
    // Anders staat er een @Catgory in de overview
    @Override
    public String toString() {
        return this.name;
    }
}
