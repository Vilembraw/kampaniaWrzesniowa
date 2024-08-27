package pliki;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int duration) {

    public static class Peristence{
        public static Optional<Song> read(int id){
            String sqlQuery = "SELECT artist, title, length FROM song WHERE id = ?";
            try {
//                DatabaseConnection.connect("songs.db","");
                PreparedStatement statement = DatabaseConnection.getConnection("").prepareStatement(sqlQuery);
                statement.setInt(1,id);

                ResultSet result = statement.executeQuery();
                if(result.next()){
                    return Optional.of(
                            new Song(
                                    result.getString("artist"),
                                    result.getString("title"),
                                    result.getInt("length")
                            )
                    );
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}




