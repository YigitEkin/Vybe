package com.vybe.backend.service;


import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.model.dto.SongNodeDTO;
import com.vybe.backend.model.dto.VenueCreationDTO;
import com.vybe.backend.model.dto.VenueDTO;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.PlaylistRepository;
import com.vybe.backend.repository.SongNodeRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.VenueRepository;
import com.vybe.backend.util.SoundtrackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    VenueRepository venueRepository;
    SongNodeRepository songNodeRepository;
    PlaylistRepository playlistRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository, SongNodeRepository songNodeRepository, PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
        this.venueRepository = venueRepository;
        this.songNodeRepository = songNodeRepository;
    }

    // add venue
    public VenueDTO addVenue(VenueCreationDTO venueCreationDTO) {
        return new VenueDTO(venueRepository.save(venueCreationDTO.toVenue()));
    }

    // get venue
    public VenueDTO getVenue(int id) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        return new VenueDTO(venueRepository.findById(id).get());
    }

    // get all venues
    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream().map(VenueDTO::new).collect(Collectors.toList());
    }

    public VenueDTO getVenue(String name) {
        if(!venueRepository.existsByName(name)) {
            throw new VenueNotFoundException("Venue with name: " + name + " not found");
        }
        return new VenueDTO(venueRepository.findByName(name));
    }

    // update venue
    public VenueDTO updateVenue(int id, VenueCreationDTO venueCreationDTO) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        VenueDTO venueDTO = new VenueDTO(venueRepository.findById(id).get());
        venueDTO.setName(venueCreationDTO.getName());
        venueDTO.setDescription(venueCreationDTO.getDescription());
        venueDTO.setLocation(venueCreationDTO.getLocation());
        return new VenueDTO(venueRepository.save(venueDTO.toVenue()));
    }

    // delete venue
    public void deleteVenue(int id) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        venueRepository.deleteById(id);
    }

    // play song from request list
    public SongDTO getNextSong(Integer venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        Song song = venue.getPlaylist().playNextSong();
        // find the playlist and update the currentMode
        Playlist playlist = playlistRepository.findById(venue.getPlaylist().getId()).get();
        playlist.setCurrentMode(venue.getPlaylist().getCurrentMode());
        playlistRepository.save(playlist);
        venueRepository.save(venue);
        return new SongDTO(song);
    }

    public Song startSong(Integer venueId) {
        if (!venueRepository.existsById(venueId))
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");

        SongDTO nextSong = getNextSong(venueId);
        Venue venue = venueRepository.findById(venueId).get();
        String playlistId;

        if (venue.getPlaylist().getCurrentMode().equals("request")) {
            playlistId = venue.getPlaylist().getRequestPlaylistId();
            songNodeRepository.deleteById(songNodeRepository.findBySong_IdAndPlaylistId(nextSong.getId(), venue.getPlaylist().getId()).getId());
        }
        else
            playlistId = venue.getPlaylist().getDefaultPlaylistId();

        String soundzoneId = venue.getSoundzoneId();
        String token = venue.getToken();
        String name = nextSong.getName();
        int index = SoundtrackUtil.findIndexOfSongInPlaylist(playlistId, name, token);
        System.out.println("playing song in index: " + index);
        SoundtrackUtil.playSong(playlistId, index, Collections.singletonList(soundzoneId), token);

        return nextSong.toSong();
    }

}
