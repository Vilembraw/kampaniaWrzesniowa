package sql.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import sql.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    private DatabaseConnection databaseConnection;

    public AccountManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    public void register(String username, String password){
        try {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(insertSQL);
            statement.setString(1,username);
            statement.setString(2,hashedPassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean authenticate(String username, String password){
        try {
            String insertSQL = "SELECT username, password FROM users WHERE username LIKE ? ";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(insertSQL);
            statement.setString(1,username);
//            statement.setString(2,password);

            if (!statement.execute()) throw new RuntimeException("SELECT failed");

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new AuthenticationException("No such user");
            }

            return resultSet.getString(2).equals(password);


        } catch (SQLException | AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }

    public Account getAccount(String username){
        try {
            String insertSQL = "SELECT username, password FROM users WHERE username LIKE ? ";

            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.execute();

            if (!statement.execute()) throw new RuntimeException("SELECT failed");

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new AuthenticationException("No such user");
            }

            int id =  resultSet.getInt("id");
            return new Account(id,username);



        } catch (SQLException | AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }
}
