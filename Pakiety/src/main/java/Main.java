import auth.AccountManager;
import database.DatabaseConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect("shop.db");

        AccountManager account = new AccountManager(databaseConnection);

        System.out.println(account.authenticate("Adam", "password"));
        System.out.println(account.getAccount("Adam"));

    }
}