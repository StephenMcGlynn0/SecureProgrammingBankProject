package bankproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:bank.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createDatabase() {
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement()) {

            String sql = "-- Drop the table if it exists\r\n" + //
                                "DROP TABLE IF EXISTS users;\r\n" + //
                                "\r\n" + //
                                "-- Create the table\r\n" + //
                                "CREATE TABLE users (\r\n" + //
                                "    username TEXT PRIMARY KEY,\r\n" + //
                                "    password TEXT NOT NULL,\r\n" + //
                                "    salt TEXT NOT NULL,\r\n" + // 'salt'
                                "    balance REAL NOT NULL\r\n" + //
                                ");\r\n" + //
                                "\r\n" + //
                                "-- Insert seed data\r\n" + //
                                "INSERT INTO users (username, password, salt, balance) VALUES\r\n" + //
                                "('test', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 100.23),\r\n" + //
                                "('BOI16589', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 100.23),\r\n" + //
                                "('BOI23658', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 2000.36),\r\n" + //
                                "('BOI41235', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 500.99),\r\n" + //
                                "('BOI87854', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 123.65),\r\n" + //
                                "('BOI11236', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 6587.21),\r\n" + //
                                "('BOI65214', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 5000.00),\r\n" + //
                                "('BOI66985', '0f7a81b923ced72bd518414028edc38062d1b40b5e0cc56e40e1ca9a71c3f33649aca2081bf65866eda56b324d9b899caa0ae54789163b6fa11d8ca528c26643', 'e3a1f9b2c487d6ea7c5f8a4e193b2c1a', 86.35);"; 

            stmt.executeUpdate(sql);
            System.out.println("users table created and populated.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}