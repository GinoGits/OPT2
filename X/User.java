package X;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.List;
import java.util.Scanner;

// This is the User class
public class User {
    private String username;
    private int userId;
    private String password;
    private Rekening rekening;
    private SpaarRekening spaarRekening;

    // Constructor
    public User(String username, int userId, String password) {
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

    // Zo maak je een rekening / spaarrekening aan
    public void createAccount() {
        this.rekening = new Rekening();
        this.spaarRekening = new SpaarRekening();
    }

    // Getters
    public String getUsername() {
        return this.username;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    // Authenticate user
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    // Hash the password
    private static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password); // Using SHA-256 hashing algorithm provided by Apache Commons Codec
    }

    // Validate password
    public boolean isValidPassword(String inputPassword) {
        // Validate input password against the stored hashed password
        String hashedInputPassword = hashPassword(inputPassword);
        return this.password.equals(hashedInputPassword);
    }

    // Check if password is strong
    public static boolean isPasswordStrong(String password) {
        // Password must be at least 8 characters long
        return password.length() >= 8;
    }

    // Create a new user
    public static User createUser(Scanner scanner, int userIdCounter) {
        Bank.printMessage("");
        Bank.printMessage("Enter username:");
        String username = Bank.readStringInput(scanner);

        if (username.trim().isEmpty()) {
            Bank.printMessage("");
            Bank.printMessage("Error: Username cannot be empty. Account creation failed.");
            return null;
        }

        Bank.printMessage("");
        Bank.printMessage("Create a password:");
        String password = Bank.readStringInput(scanner);

        Bank.printMessage("");
        Bank.printMessage("Confirm password:");
        String confirmPassword = Bank.readStringInput(scanner);

        if (!password.equals(confirmPassword)) {
            Bank.printMessage("");
            Bank.printMessage("Passwords do not match. Account creation failed.");
            return null;
        }

        if (!isPasswordStrong(password)) {
            Bank.printMessage("");
            Bank.printMessage("Error: Password must be at least 8 characters long. Account creation failed");
            return null;
        }

        Bank.printMessage("");
        Bank.printMessage("Account created successfully for user: " + username);
        String hashedPassword = DigestUtils.sha256Hex(password);
        return new User(username, userIdCounter, hashedPassword); // Hash the password before storing
    }

    // Login
    public static User login(Scanner scanner, User[] users) {
        Bank.printMessage("");
        Bank.printMessage("Enter username:");
        String username = Bank.readStringInput(scanner);
        Bank.printMessage("");
        Bank.printMessage("Enter password:");
        String password = Bank.readStringInput(scanner);

        for (User user : users) {
            if (user != null && user.getUsername().equals(username) && user.isValidPassword(password)) {
                Bank.printMessage("");
                Bank.printMessage("\u001B[32mLogin successful. Welcome, " + username + "!\u001B[0m");
                return user;
            }
        }

        Bank.printMessage("");
        Bank.printMessage("\u001B[31mInvalid username or password. Login failed.\u001B[0m");
        Bank.printMessage("");
        return null;
    }

    // Change username
    public static void changeUsername(Scanner scanner, User loggedInUser) {
        Bank.printMessage("");
        Bank.printMessage("Current username: " + loggedInUser.getUsername());
        Bank.printMessage("");
        Bank.printMessage("Enter new username:");

        String newUsername;
        do {
            newUsername = Bank.readStringInput(scanner);
            if (newUsername.trim().isEmpty()) {
                Bank.printMessage("Error: Username cannot be empty. Please enter a valid username:");
            }
        } while (newUsername.trim().isEmpty());

        loggedInUser.setUsername(newUsername);
        Bank.printMessage("");
        Bank.printMessage("Username changed successfully.");
    }

    // Change password
    public static void changePassword(Scanner scanner, User loggedInUser) {
        Bank.printMessage("");
        Bank.printMessage("Enter current password:");
        String currentPassword = Bank.readStringInput(scanner);

        if (!loggedInUser.isValidPassword(currentPassword)) {
            Bank.printMessage("");
            Bank.printMessage("Incorrect current password. Password change failed.");
            return;
        }

        Bank.printMessage("");
        Bank.printMessage("Enter new password:");
        String newPassword = Bank.readStringInput(scanner);

        if (!isPasswordStrong(newPassword)) {
            Bank.printMessage("");
            Bank.printMessage("Error: Password must be at least 8 characters long.");
            return;
        }

        Bank.printMessage("");
        Bank.printMessage("Confirm new password:");
        String confirmPassword = Bank.readStringInput(scanner);

        if (!newPassword.equals(confirmPassword)) {
            Bank.printMessage("");
            Bank.printMessage("Passwords do not match. Password change failed.");
        } else {
            loggedInUser.setPassword(newPassword);
            Bank.printMessage("");
            Bank.printMessage("Password changed successfully.");
        }
    }

    // Delete accounts
    public static boolean deleteAccount(Scanner scanner, User loggedInUser, List<User> users) {
        Bank.printMessage("");
        Bank.printMessage("Enter your password to proceed with account deletion:");
        String password = Bank.readStringInput(scanner);
        boolean isDeleted = false;

        if (loggedInUser.isValidPassword(password)) {
            Bank.printMessage("");
            Bank.printMessage("\u001B[33mWARNING: This action cannot be undone.\u001B[0m");

            Bank.printMessage("\u001B[31mAre you sure you want to delete your account? (Y/N)\u001B[0m");
            Bank.printMessage("");
            String choice = Bank.readStringInput(scanner);

            if (choice.equalsIgnoreCase("Y")) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i) != null && users.get(i).getUsername().equals(loggedInUser.getUsername())) {
                        users.remove(i);
                        Bank.printMessage("");
                        Bank.printMessage("Account deleted successfully.");
                        isDeleted = true;
                    }
                }
            } else {
                Bank.printMessage("");
                Bank.printMessage("Account deletion cancelled.");
            }
        } else {
            Bank.printMessage("");
            Bank.printMessage("Incorrect password. Account deletion failed.");
        }
        // Return true if account is deleted, false otherwise
        // hierdoor wordt de gebruiker uitgelogd of niet
        return isDeleted;
    }

    // Getters & Setters
    // Rekening en spaarrekening zijn per user uniek
    public Rekening getRekening() {
        return this.rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public SpaarRekening getSpaarRekening() {
        return this.spaarRekening;
    }

    public void setSpaarRekening(SpaarRekening spaarRekening) {
        this.spaarRekening = spaarRekening;
    }

}
