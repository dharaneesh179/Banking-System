package myonlinebanking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Bankaccount {
    private String customerId;
    private String customerName;
    private double balance;

    // Constructor for initializing account from database
    public Bankaccount(String customerId, String customerName, double balance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            updateBalanceInDatabase();
            System.out.println("Deposited successfully! New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw method
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            updateBalanceInDatabase();
            System.out.println("Withdrawn successfully! New balance: " + balance);
            return true;
        } else if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else {
            System.out.println("Insufficient balance!");
        }
        return false;
    }

    // Method to update balance in the database
    private void updateBalanceInDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateQuery = "UPDATE accounts SET Balance = ? WHERE customerId = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setDouble(1, balance); // Save updated balance
            statement.setString(2, customerId); // Identify the correct account
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update balance in the database: " + e.getMessage());
        }
    }

}
