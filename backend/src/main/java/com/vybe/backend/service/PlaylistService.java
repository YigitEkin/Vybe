package com.vybe.backend.service;


import com.vybe.backend.model.dto.PlaylistCreationDTO;
import com.vybe.backend.model.dto.PlaylistDTO;
import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.exception.InputException;
import com.vybe.backend.exception.PlaylistNotFoundException;
import com.vybe.backend.exception.SongNotFoundException;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.PlaylistRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.VenueRepository;
import com.vybe.backend.util.SoundtrackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaylistService {
    PlaylistRepository playlistRepository;
    VenueRepository venueRepository;
    SongRepository songRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, VenueRepository venueRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.venueRepository = venueRepository;
        this.songRepository = songRepository;
    }

    // create playlist
    public PlaylistDTO createPlaylist(PlaylistCreationDTO playlistCreationDTO) {
        Venue venue = venueRepository.findById(playlistCreationDTO.getVenueId()).orElseThrow(() -> new VenueNotFoundException("Venue with id: " + playlistCreationDTO.getVenueId() + " not found"));
        Playlist playlist = playlistRepository.save(playlistCreationDTO.toPlaylist());
        venue.setPlaylist(playlist);
        playlist.setVenue(venue);
        return new PlaylistDTO(playlist);
    }

    // get playlist
    public PlaylistDTO getPlaylist(Integer id) {
        return new PlaylistDTO(playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found")));
    }

    // get all playlists
    public List<PlaylistDTO> getAllPlaylists() {
        return playlistRepository.findAll().stream().map(PlaylistDTO::new).collect(Collectors.toList());
    }

    // get playlist of venue
    public PlaylistDTO getPlaylistOfVenue(Integer venueId) {
        return new PlaylistDTO(playlistRepository.findByVenueId(venueId).orElseThrow(() -> new PlaylistNotFoundException("Playlist of venue with id: " + venueId + " not found")));
    }


    // delete playlist
    public void deletePlaylist(Integer id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        Venue venue = venueRepository.findById(playlist.getVenue().getId()).orElseThrow(() -> new VenueNotFoundException("Venue with id: " + playlist.getVenue().getId() + " not found"));

        venue.setPlaylist(null);
        playlistRepository.delete(playlist);
    }

    // add song to default playlist from song id
    public SongDTO addSongToDefaultPlaylist(Integer id, Integer songId) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        Song song = songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException("Song with id: " + songId + " not found"));

        playlist.getDefaultPlaylist().add(song);

        return new SongDTO(song);
    }

    // remove song from default playlist from song id
    public SongDTO removeSongFromDefaultPlaylist(Integer id, Integer songId) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        Song songToRemove = songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException("Song with id: " + songId + " not found"));
        playlist.getDefaultPlaylist().removeIf(song -> song.getId().equals(songId));

        return new SongDTO(songToRemove);
    }

    // get default playlist of venue
    public List<SongDTO> getDefaultPlaylistOfVenue(Integer venueId) {
        return playlistRepository.findByVenueId(venueId).orElseThrow(() -> new PlaylistNotFoundException("Playlist of venue with id: " + venueId + " not found")).getDefaultPlaylist().stream().map(SongDTO::new).collect(Collectors.toList());
    }

    // set mode of playlist
    public PlaylistDTO setModeOfPlaylist(Integer id, String mode) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        playlist.setCurrentMode(mode);
        return new PlaylistDTO(playlistRepository.save(playlist));
    }

    // add banned genres to playlist
    public PlaylistDTO addBannedGenresToPlaylist(Integer id, List<String> bannedGenres) {
        if (bannedGenres == null)
            throw new InputException("Banned genres list cannot be null");
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        playlist.getBannedGenres().addAll(bannedGenres);
        return new PlaylistDTO(playlistRepository.save(playlist));
    }

    // remove banned genres from playlist
    public PlaylistDTO removeBannedGenresFromPlaylist(Integer id, List<String> bannedGenres) {
        if (bannedGenres == null)
            throw new InputException("Banned genres list cannot be null");
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        playlist.getBannedGenres().removeAll(bannedGenres);
        return new PlaylistDTO(playlistRepository.save(playlist));
    }

    // add permitted genres to playlist
    public PlaylistDTO addPermittedGenresToPlaylist(Integer id, List<String> permittedGenres) {
        if (permittedGenres == null)
            throw new InputException("Permitted genres list cannot be null");
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        playlist.getPermittedGenres().addAll(permittedGenres);
        return new PlaylistDTO(playlistRepository.save(playlist));
    }

    // remove permitted genres from playlist
    public PlaylistDTO removePermittedGenresFromPlaylist(Integer id, List<String> permittedGenres) {
        if (permittedGenres == null)
            throw new InputException("Permitted genres list cannot be null");
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        playlist.getPermittedGenres().removeAll(permittedGenres);
        return new PlaylistDTO(playlistRepository.save(playlist));
    }

    // add all songs returned from soundtrackyourbrand to song repository then add them to default playlist
    public List<SongDTO> addAllSongsToDefaultPlaylist(Integer id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " + id + " not found"));
        List<HashMap<String, String>> songs = SoundtrackUtil.getTracksOnPlaylist(playlist.getDefaultPlaylistId(), playlist.getVenue().getToken());
        List<SongDTO> songDTOs = new ArrayList<>();
        for (HashMap<String,String> song : songs) {
            if(song.get("isAvailable").equals("false"))
                continue;
            SongDTO songDTO = new SongDTO();
            songDTO.setName(song.get("trackName"));
            songDTO.setArtist(song.get("artistNames"));
            songDTO.setLink(song.get("imageUrl"));
            Song song1 = songRepository.save(songDTO.toSong());
            playlist.getDefaultPlaylist().add(song1);
            songDTO = new SongDTO(song1);
            songDTOs.add(songDTO);

            addSongToDefaultPlaylist(id, songDTO.getId());
        }

        return songDTOs;
    }

    public List<SongDTO> getSongsOfDefaultPlaylist(Integer venueId) {
        Playlist playlist = playlistRepository.findByVenueId(venueId).orElseThrow(() -> new PlaylistNotFoundException("Playlist of venue with id: " + venueId + " not found"));
        return playlist.getDefaultPlaylist().stream().map(SongDTO::new).collect(Collectors.toList());
    }


    // TODO: add get next music method


}
