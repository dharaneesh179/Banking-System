package myonlinebanking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
public class BankingMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashMap<String, Bankaccount> accounts = new HashMap<>();

        // Load accounts from the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT customerId, customerName, Balance FROM accounts";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String customerId = resultSet.getString("customerId");
                String customerName = resultSet.getString("customerName");
                double balance = resultSet.getDouble("Balance");

                Bankaccount bankAccount = new Bankaccount(customerId, customerName, balance);
                accounts.put(customerId, bankAccount);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }


        // Main banking logic
        while (true) {
            System.out.print("Enter customer ID: ");
            String customerId = in.next();

            if (accounts.containsKey(customerId)) {
                Bankaccount currentAccount = accounts.get(customerId);
                System.out.println("Welcome, " + currentAccount.getCustomerName() + "!");

                while (true) {
                    System.out.println("\nChoose an option:");
                    System.out.println("1. Check Balance");
                    System.out.println("2. Deposit Money");
                    System.out.println("3. Withdraw Money");
                    System.out.println("4. Switch Account");
                    System.out.println("5. Exit");

                    int choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Your Account Balance: " + currentAccount.getBalance());
                            break;
                        case 2:
                            System.out.print("Enter deposit amount: ");
                            double deposit = in.nextDouble();
                            currentAccount.deposit(deposit);
                            break;
                        case 3:
                            System.out.print("Enter withdrawal amount: ");
                            double withdrawal = in.nextDouble();
                            currentAccount.withdraw(withdrawal);
                            break;
                        case 4:
                            System.out.println("Switching account...");
                            break;
                        case 5:
                            System.out.println("Thank you for using our banking system. Goodbye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid option! Please try again.");
                    }
                    if (choice == 4) break;
                }
            } else {
                System.out.println("Invalid Account ID. Please try again!");
            }
        }
    }
}
