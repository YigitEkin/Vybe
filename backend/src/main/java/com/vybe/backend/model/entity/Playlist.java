package com.vybe.backend.model.entity;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Playlist {
    private PriorityQueue<SongNode> requestedSongs;
    private Queue<Song> defaultPlaylist;
    private List<String> permittedGenres;
    private Song currentlyPlayingSong;
    private String currentMode;

    public Boolean addGenre(String genre) {
        return null;
    }

    public Boolean removeGenre(String genre) {
        return null;
    }

    public Boolean addSong(Song song) {
        return null;
    }

    public Boolean addSongRequest(SongRequest songRequest) {
        return null;
    }

    public Song playNextSong() {
        return null;
    }
}
