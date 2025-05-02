package bankproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseActions {

    public boolean registerUser(String username, String rawPassword) {
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(rawPassword, salt);
    
        String sql = "INSERT INTO users (username, password, salt, balance) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.setDouble(4, 0.0);
            stmt.executeUpdate();
            return true;
    
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") || e.getMessage().contains("PRIMARY")) {
                System.out.println("Username already exists.");
            } else {
                System.out.println("Error: " + e.getMessage());
            }
            return false;
        }
    }

    public User login(String username, String inputPassword) {
        String sql = "SELECT password, salt, balance FROM users WHERE username = ?";
    
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String storedHash = rs.getString("password");
                String salt = rs.getString("salt");
                double balance = rs.getDouble("balance");
    
                String inputHash = PasswordUtils.hashPassword(inputPassword, salt);
                if (inputHash.equals(storedHash)) {
                    return new User(username, balance);
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    
        return null;  // login failed
    }

    public boolean updateBalance(User user) {
        String sql = "UPDATE users SET balance = ? WHERE username = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setDouble(1, user.checkBalance());
            stmt.setString(2, user.getUsername());
            stmt.executeUpdate();
            return true;
    
        } catch (SQLException e) {
            System.out.println("Failed to update balance: " + e.getMessage());
            return false;
        }
    }
}