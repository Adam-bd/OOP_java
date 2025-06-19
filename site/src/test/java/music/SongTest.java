package music;

import database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    @BeforeAll
    static void connect(){
        DatabaseConnection.connect("src/main/resources/songs.db","songs");
    }

    @Test
    void testSongIndex() throws SQLException {
        DatabaseConnection.connect("src/main/resources/songs.db","songs");
        Optional<Song> optionalSong = Song.Persistence.read(1);
        Song expectedSong = new Song("The Beatles", "Hey Jude", 431);
        assertTrue(optionalSong.isPresent());
        assertEquals(expectedSong, optionalSong.get());
    }

    @Test
    void testWrongSongIndex() throws SQLException {
        Optional<Song> optionalSong = Song.Persistence.read(-1);
        assertFalse(optionalSong.isPresent());
    }

    @AfterAll
    static void disconnect(){
        DatabaseConnection.disconnect("songs");
    }

    static Stream<Arguments> dataPreparer(){
        return Stream.of(Arguments.of(5, new Song("Queen", "Bohemian Rhapsody", 355)),
                Arguments.of(10, new Song("The Who", "My Generation", 198)),
                Arguments.of(12, new Song("The Temptations", "My Girl", 174)));
    }

    @ParameterizedTest
    @MethodSource("dataPreparer")
    void testSongIndexes(int index, Song song) throws SQLException {
        Optional<Song> optionalSong = Song.Persistence.read(index);
        assertTrue(optionalSong.isPresent());
        assertEquals(song, optionalSong.get());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/songs.csv", numLinesToSkip = 1)
    void testSongIndexesWithCSV(int index, String artist, String title, int length) throws SQLException {
        Optional<Song> optionalSong = Song.Persistence.read(index);
        assertTrue(optionalSong.isPresent());
        Song song = new Song(artist, title, length);
        assertEquals(song, optionalSong.get());
    }
}
