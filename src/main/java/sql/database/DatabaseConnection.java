package sql.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static java.sql.Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public void connect(String path){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            System.out.println("connected : " + path);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect(){
        try {
            connection.close();
            System.out.println("disconnected : " + connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
