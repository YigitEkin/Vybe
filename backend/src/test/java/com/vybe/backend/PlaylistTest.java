package com.vybe.backend;

import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.SongNode;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.SongNodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTest {

    private Playlist playlist;

    @Mock
    private SongNodeRepository songNodeRepository;

    @BeforeEach
    public void setup() throws InterruptedException {
        MockitoAnnotations.openMocks(this);

        Venue venue = new Venue();
        venue.setId(1);

        Set<SongNode> requestedSongs = new HashSet<>();
        Song song1 = new Song();
        song1.setName("first song");
        Song song2 = new Song();
        song2.setName("second song");
        Song song3 = new Song();
        song3.setName("third song");
        Song song4 = new Song();
        song4.setName("fourth song");
        Song song5 = new Song();
        song5.setName("fifth song");
        Song song6 = new Song();
        song6.setName("sixth song");
        Song song7 = new Song();
        song7.setName("seventh song");
        Song song8 = new Song();
        song8.setName("eight song");



        requestedSongs.add(new SongNode(0,0,  song1, 3.0));
        Thread.sleep(1000);
        requestedSongs.add(new SongNode(1,0,  song2, 2.0));
        Thread.sleep(1000);
        requestedSongs.add(new SongNode(2,0,  song3, 1.0));
        Thread.sleep(1000);
        requestedSongs.add(new SongNode(3,0,  song4, 3.0));
        Thread.sleep(1000);
        requestedSongs.add(new SongNode(4,0,  song5, 2.0));
        Thread.sleep(1000);

        Set<Song> defaultPlaylist = new HashSet<>();
        defaultPlaylist.add(song6);
        defaultPlaylist.add(song7);
        defaultPlaylist.add(song8);

        Set<String> permittedGenres = new HashSet<>();
        permittedGenres.add("pop");
        permittedGenres.add("rock");

        Set<String> bannedGenres = new HashSet<>();
        bannedGenres.add("country");

        playlist = new Playlist();
        playlist.setId(1);
        playlist.setDefaultPlaylistId("0");
        playlist.setRequestPlaylistId("1");
        playlist.setVenue(venue);
        playlist.setDefaultPlaylistIndex(0);
        playlist.setRequestedSongs(requestedSongs);
        playlist.setDefaultPlaylist(defaultPlaylist);
        playlist.setPermittedGenres(permittedGenres);
        playlist.setBannedGenres(bannedGenres);
        playlist.setCurrentMode("request");

    }

    @Test
    public void testPlayNextSongWithRequestedSongs() {
        // Arrange

        // Act
        Song result = playlist.playNextSong(songNodeRepository);

        // Assert
        assertEquals("first song", result.getName());
        assertEquals("request", playlist.getCurrentMode());
    }

    @Test
    public void testGetCurrentQueue() {
        // Arrange

        // Act
        List<Song> result = playlist.getCurrentQueue(8);

        // Assert
        assertEquals(8, result.size());
        assertEquals("first song", result.get(0).getName());
        assertEquals("fourth song", result.get(1).getName());
        assertEquals("second song", result.get(2).getName());
        assertEquals("fifth song", result.get(3).getName());
        assertEquals("third song", result.get(4).getName());
    }

    // test that the getCurrentQueue method returns the output of repeated calls to playNextSong
    @Test
    public void testGetCurrentQueueWithPlayNextSong() {
        // Arrange

        // Act
        List<Song> result2 = playlist.getCurrentQueue(8);

        List<Song> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            result.add(playlist.playNextSong(songNodeRepository));
        }



        // Assert
        assertEquals(8, result.size());
        assertEquals(result.get(0), result2.get(0));
        assertEquals(result.get(1), result2.get(1));
        assertEquals(result.get(2), result2.get(2));
        assertEquals(result.get(3), result2.get(3));
        assertEquals(result.get(4), result2.get(4));
        assertEquals(result.get(5), result2.get(5));
        assertEquals(result.get(6), result2.get(6));
        assertEquals(result.get(7), result2.get(7));
    }

}
