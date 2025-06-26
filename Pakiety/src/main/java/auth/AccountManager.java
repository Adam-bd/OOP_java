package auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    DatabaseConnection connection;
    public AccountManager(DatabaseConnection connection) {
        this.connection = connection;
    }

    public int register(String name, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.getConnection()
                .prepareStatement("SELECT * FROM auth_account WHERE name = ?;");
        preparedStatement.setString(1, name);

        preparedStatement.execute();
        if(preparedStatement.getResultSet().next())
            throw new RuntimeException("User " + name + " already exists.");
        preparedStatement = connection.getConnection().prepareStatement("INSERT INTO auth_account(name, password) VALUES(?, ?);");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public boolean authenticate(String name, String password) throws SQLException {
        PreparedStatement statement = connection.getConnection()
                .prepareStatement("SELECT password FROM auth_account WHERE name = ?;");

        statement.setString(1, name);
        statement.execute();

        ResultSet result = statement.getResultSet();
        if(result.next()){
            String hashedPassword = result.getString("password");
            BCrypt.Result cryptResult = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
            return cryptResult.verified;
        } else{
            throw new RuntimeException("No such user " + name);
        }
    }

    public Account getAccount(String name) throws SQLException {
        PreparedStatement statement = connection.getConnection()
                .prepareStatement("SELECT id, name FROM auth_account WHERE name = ?;");

        statement.setString(1, name);
        statement.execute();

        ResultSet result = statement.getResultSet();
        if(result.next()){
            int id = result.getInt("id");
            return new Account(id, name);
        } else{
            throw new RuntimeException("No such user " + name);
        }
    }
}
