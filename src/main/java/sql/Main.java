package sql;


import sql.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();

        connection.connect("dupa.db");


        try {
            String createSQLTable = "CREATE TABLE users( "+
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                    "username TEXT NOT NULL,"+
                    "password TEXT NOT NULL)";

//            PreparedStatement statement = connection.getConnection().prepareStatement(createSQLTable);
//            statement.executeUpdate();
            String insertSQL = "INSERT INTO users(username, password)"+
                    "VALUES (?, ?)";
            PreparedStatement statement = connection.getConnection().prepareStatement(insertSQL);
            statement.setString(1,"asd");
            statement.setString(2,"asdsad");
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connection.disconnect();
    }
}
