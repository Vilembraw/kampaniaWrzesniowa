import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pliki.Playlist;
import pliki.Song;
import pliki.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    //1b
    @Test
    public void testIsEmpty(){
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());

    }

    //1c

    @Test
    public void testSize(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("dupson","gowno",60));
//        assertTrue(playlist.size() == 1);
        assertEquals(1,playlist.size());
    }

    //1d

    @Test
    public void testOneSong(){
        Playlist playlist = new Playlist();
        Song newSong = new Song("dupson","gowno",60);
        playlist.add(newSong);
        assertEquals(newSong, playlist.getFirst());

    }

    //1e

    @Test
    public void testEqualSong(){
        Playlist playlist = new Playlist();
        Song newSong = new Song("Atr", "OOO", 180);
        playlist.add(newSong);
        Song testSong = new Song("Atr", "OOO", 180);
        assertEquals(testSong, newSong);
    }

    //1f

    @Test
    public void testAtSecond(){
        Playlist playlist = new Playlist();
        Song testSong1 = new Song("Atr1", "1", 32);
        Song testSong2 = new Song("Atr2", "2", 43);
        Song testSong3 = new Song("Atr3", "3", 20);

        playlist.add(testSong1);
        playlist.add(testSong2);
        playlist.add(testSong3);

        assertEquals(testSong2,playlist.atSecond(33));

    }

    //1g

    @Test
    public void testAtSecondException(){
        Playlist playlist = new Playlist();
        Song testSong1 = new Song("Atr1", "1", 32);
        Song testSong2 = new Song("Atr2", "2", 43);
        Song testSong3 = new Song("Atr3", "3", 20);

        playlist.add(testSong1);
        playlist.add(testSong2);
        playlist.add(testSong3);

        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(300));
        assertEquals(indexOutOfBoundsException.getMessage(),"Duration too long");
    }

    //1h
    @Test
    public void testNegativeDuration(){
        Playlist playlist = new Playlist();
        Song testSong1 = new Song("Atr1", "1", 32);
        Song testSong2 = new Song("Atr2", "2", 43);
        Song testSong3 = new Song("Atr3", "3", 20);

        playlist.add(testSong1);
        playlist.add(testSong2);
        playlist.add(testSong3);

        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-30));
        assertEquals(indexOutOfBoundsException.getMessage(),"Negative duration");

    }

    //2b

    //cos ten kuerwski beforeall nie dzialal
    @BeforeAll
    public static void openDatabase(){
        DatabaseConnection.connect("C:\\Users\\Vilembraw\\IdeaProjects\\proj1\\src\\main\\java\\pliki\\songs.db","");
    }

    @Before
    public void openDatabase1(){
        DatabaseConnection.connect("C:\\Users\\Vilembraw\\IdeaProjects\\proj1\\src\\main\\java\\pliki\\songs.db","");
    }

    //2b
    @Test
    public void testRead(){
//        DatabaseConnection.connect("C:\\Users\\Vilembraw\\IdeaProjects\\proj1\\src\\main\\java\\pliki\\songs.db","");

        Optional<Song> testSong = Song.Peristence.read(1);
        Song expected = new Song("The Beatles", "Hey Jude", 431);
        assertEquals(expected,testSong.get());
    }

    //2c
    @Test
    public void testReadWrong(){

        Optional<Song> testSong = Song.Peristence.read(1);
        Song expected = new Song("The Beatles", "Hey Jude", 431);
//        assertEquals(expected,testSong.get());
        assertFalse(testSong.isPresent());
    }

    //2d
    static Stream<Arguments> songDataProvider() {
        return Stream.of(
                Arguments.of(1, new Song("The Beatles", "Hey Jude", 431)),
                Arguments.of(2, new Song("Thea Rolling Stones", "(I Can't Get No) Satisfaction", 224)),
                Arguments.of(3, new Song("Led Zeppelin", "Stairway to Heaven", 482))
        );
    }

    @ParameterizedTest
    @MethodSource("songDataProvider")
    public void testReadStream(int id, Song expected){
        Optional<Song> testSong = Song.Peristence.read(id);
        assertEquals(expected,testSong.get());
    }



    @After
    public void closeDatabase(){
        DatabaseConnection.disconnect("");
    }

    //2d
}
