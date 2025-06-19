package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String path) throws SQLException {
        DriverManager.getConnection("jdbc:sqlite:" + path);
        System.out.println("Połączono");
    }
    public void disconnect() throws SQLException {
        connection.close();
        System.out.println("Rozłączono");
    }
}
