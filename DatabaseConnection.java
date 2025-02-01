package myonlinebanking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system"; // Replace 'bankingdb' with your actual database name
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "1234"; // Replace with your MySQL password

    // Method to establish and return a database connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
