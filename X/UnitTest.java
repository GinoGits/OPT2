package X;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Scanner;

public class UnitTest {

    @Test
    public void testUser() {
        // Create a new user object
        User user = new User("TestUser", 1, "password");

        // Check if the user object is created successfully
        assertEquals("TestUser", user.getUsername());
    }

    @Test
    public void testCategory() {
        // Create a new category object
        Category category = new Category("TestCategory");

        // Check if the category object is created successfully
        assertEquals("TestCategory", category.getName());
    }

    @Test
    public void testSavingGoal() {
        // Create a new saving goal object
        SavingGoal savingGoal = new SavingGoal("TestGoal", 1000.0, 1000.0);

        // Check if the saving goal object is created successfully
        assertEquals("TestGoal", savingGoal.getName());
        assertEquals(1000.0, savingGoal.getBalance(), 0.001);
    }

    @Test
    public void testTransaction() {
        // Create a new category, saving goal and transaction object
        Category category = new Category("TestCategory");
        SavingGoal savingGoal = new SavingGoal("TestGoal", 1000.0, 0.0);
        Transaction transaction = new Transaction(100.0, "2022-01-01", "TestTransaction", category, savingGoal);

        // Check if the transaction object is created successfully
        assertEquals(100.0, transaction.getAmount(), 0.001);
        assertEquals("2022-01-01", transaction.getDate());
        assertEquals("TestTransaction", transaction.getDescription());
    }

    @Test
    public void testRekeningCreation() {
        // Create a new rekening object
        Rekening rekening = new Rekening();

        // Check if the rekening object is created successfully
        assertNotNull(rekening);
        assertEquals(0.0, rekening.getBalance(), 0.001);
    }

    @Test
    public void testDeleteCategoryFromRekening() {
        // Create a new user, rekening and category objects
        User user = new User("TestUser", 1, "password");
        user.setRekening(new Rekening());
        Rekening rekening = user.getRekening();
        Category category = new Category("TestCategory");

        // Add the category to the rekening
        rekening.addCategory(category);

        // Delete the category from the rekening
        rekening.deleteCategory(new Scanner("1\n"), user);

        // Check if the category is deleted successfully
        List<Category> categories = rekening.getCategories();
        assertFalse(categories.contains(category));
    }

    @Test
    public void testAddSavingGoalToSpaarRekening() {
        // Create a new user, spaarrekening and saving goal objects
        User user = new User("TestUser", 1, "password");
        SpaarRekening spaarRekening = new SpaarRekening();
        SavingGoal newGoal = new SavingGoal("Vacation", 1000.0, 0.0);

        // Add the saving goal to the spaarrekening
        spaarRekening.addSavingGoal(newGoal);

        // Check if the saving goal is added successfully
        List<SavingGoal> savingGoals = spaarRekening.getSavingGoals();
        assertTrue(savingGoals.contains(newGoal));
    }

    @Test
    public void testDeleteSavingGoalFromSpaarRekening() {
        // Create a new user, spaarrekening, saving goal objects
        User user = new User("TestUser", 1, "password");
        user.setSpaarRekening(new SpaarRekening());
        SpaarRekening spaarRekening = user.getSpaarRekening();
        SavingGoal goal = new SavingGoal("car", 1000.0, 0.0);

        // Add the saving goal to the spaarrekening
        spaarRekening.addSavingGoal(goal);

        // Delete the saving goal from the spaarrekening
        spaarRekening.deleteSavingGoal(new Scanner("1\n"), user);

        // Check if the saving goal is deleted successfully
        List<SavingGoal> savingGoals = spaarRekening.getSavingGoals();
        assertFalse(savingGoals.contains(goal));
    }

    @Test
    public void testChangeUsernameForUser() {
        // Create a new user object
        User user = new User("TestUser", 1, "password");

        // Change the username of the user
        user.setUsername("NewUsername");

        // Check if the username is changed successfully
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    public void testChangePasswordForUser() {
        // Create a new user object
        User user = new User("TestUser", 1, "password");

        // Change the password of the user
        user.setPassword("newPassword");

        // Since the password is hashed, we compare the hashed version
        String hashedVersion = ("5c29a959abce4eda5f0e7a4e7ea53dce4fa0f0abbe8eaa63717e2fed5f193d31");

        // Check if the password is changed successfully
        assertEquals(hashedVersion, user.getPassword());
    }

    @Test
    public void testAddCategoryToRekening() {
        // Create a new user, rekening and category objects
        User user = new User("TestUser", 1, "password");
        Rekening rekening = new Rekening();
        Category newCategory = new Category("Shopping");

        // Add the category to the rekening
        rekening.addCategory(newCategory);

        // Check if the category is added successfully
        List<Category> categories = rekening.getCategories();
        assertTrue(categories.contains(newCategory));
    }

    @Test
    public void testAddTransactionWithCategoryAndSavingGoal() {
        // Create a new category, saving goal and transaction objects
        Category category = new Category("TestCategory");
        SavingGoal savingGoal = new SavingGoal("TestGoal", 1000.0, 0.0);
        Transaction transaction = new Transaction(200.0, "2022-01-03", "TestTransaction", category, savingGoal);

        // Add the transaction to the rekening
        Rekening rekening = new Rekening();
        rekening.addTransaction(transaction);

        // Check if the transaction is added successfully
        assertEquals(200.0, rekening.getBalance(), 0.001);
        assertTrue(rekening.getTransactions().contains(transaction));
    }

    @Test
    public void testAddMultipleSavingGoalsToSpaarRekening() {
        // Create a new spaarrekening object
        SpaarRekening spaarRekening = new SpaarRekening();

        // Create two saving goal objects
        SavingGoal goal1 = new SavingGoal("Goal1", 1000.0, 0.0);
        SavingGoal goal2 = new SavingGoal("Goal2", 500.0, 0.0);

        // Add the saving goals to the spaarrekening
        spaarRekening.addSavingGoal(goal1);
        spaarRekening.addSavingGoal(goal2);

        // Check if the saving goals are added successfully
        List<SavingGoal> savingGoals = spaarRekening.getSavingGoals();
        assertEquals(2, savingGoals.size());
        assertTrue(savingGoals.contains(goal1));
        assertTrue(savingGoals.contains(goal2));
    }

    @Test
    public void testChangeUsernameAndPasswordForUser() {
        // Create a new user object
        User user = new User("TestUser", 1, "password");

        // Change the username and password of the user
        user.setUsername("NewUsername");
        user.setPassword("newPassword");

        // Since the password is hashed, we compare the hashed version
        String hashedVersion = ("5c29a959abce4eda5f0e7a4e7ea53dce4fa0f0abbe8eaa63717e2fed5f193d31");

        // Check if the password is changed successfully
        assertEquals(hashedVersion, user.getPassword());
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    public void testAddTransactionWithNullCategoryAndSavingGoal() {
        // Create a new transaction object
        Transaction transaction = new Transaction(200.0, "2022-01-03", "TestTransaction", null, null);

        // Add the transaction to the rekening
        Rekening rekening = new Rekening();
        rekening.addTransaction(transaction);

        // Check if the transaction is added successfully
        assertEquals(200.0, rekening.getBalance(), 0.001);
        assertTrue(rekening.getTransactions().contains(transaction));
    }

    @Test
    public void testAddTransactionWithNullDescriptionToRekening() {
        // Create a new category and transaction objects
        Category category = new Category("TestCategory");
        Transaction transaction = new Transaction(200.0, "2022-01-03", null, category, null);

        // Add the transaction to the rekening
        Rekening rekening = new Rekening();
        rekening.addTransaction(transaction);

        // Check if the transaction is added successfully
        assertEquals(200.0, rekening.getBalance(), 0.001);
        assertTrue(rekening.getTransactions().contains(transaction));
    }

    @Test
    public void testNegativeTransactionInRekening() {
        // Set the initial balance and negative transaction amount
        double initialBalance = 1000.0;
        double negativeTransactionAmount = -500.0;
        double expectedBalance = initialBalance + negativeTransactionAmount;

        // Create a new rekening object and add an initial balance
        Rekening rekening = new Rekening();
        rekening.addTransaction(new Transaction(initialBalance, "2022-01-01", "Initial Balance", null, null));

        // Add the negative transaction to the rekening
        Transaction transaction = new Transaction(negativeTransactionAmount, "2022-01-02", "Negative Transaction", null,
                null);
        rekening.addTransaction(transaction);

        // Check if the negative transaction is added successfully
        assertEquals(expectedBalance, rekening.getBalance(), 0.001);
        assertTrue(rekening.getTransactions().contains(transaction));
    }

}
