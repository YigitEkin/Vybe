package com.vybe.backend.service;


import com.vybe.backend.exception.*;
import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.model.dto.SongNodeDTO;
import com.vybe.backend.model.dto.SongRequestDTO;
import com.vybe.backend.model.entity.*;
import com.vybe.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.http.HttpTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Resource SongRepository songRepository;
    @Resource PlaylistRepository playlistRepository;
    @Resource SongNodeRepository songNodeRepository;
    @Resource SongRequestRepository songRequestRepository;
    @Resource CustomerRepository customerRepository;
    @Resource VenueRepository venueRepository;
    @Resource WalletRepository walletRepository;


    // ************** Song Methods ************** //
    // get all songs
    public List<SongDTO> getAllSongs() {
        return songRepository.findAll().stream().map(SongDTO::new).collect(Collectors.toList());
    }

    // get song by id
    public SongDTO getSong(Integer id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        return new SongDTO(songRepository.findById(id).get());
    }

    // get song by name
    public SongDTO getSongByName(String name) {
        if (!songRepository.existsByName(name)) {
            throw new SongNotFoundException("Song with name: " + name + " not found");
        }
        return new SongDTO(songRepository.findByName(name));
    }

    // add song
    public SongDTO addSong(SongDTO songDTO) {
        if (songRepository.existsByName(songDTO.getName())) {
            throw new SongNotFoundException("Song with name: " + songDTO.getName() + " already exists");
        }
        return new SongDTO(songRepository.save(songDTO.toSong()));
    }

    // update song
    public SongDTO updateSong(Integer id, SongDTO songDTO) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        SongDTO songDTO1 = new SongDTO(songRepository.findById(id).get());
        songDTO1.setName(songDTO.getName());
        songDTO1.setArtist(songDTO.getArtist());
        songDTO1.setAlbumArt(songDTO.getAlbumArt());
        songDTO1.setLink(songDTO.getLink());
        return new SongDTO(songRepository.save(songDTO1.toSong()));
    }

    // delete song
    public void deleteSong(Integer id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        songRepository.deleteById(id);
    }

    // save all songs
    public List<SongDTO> saveAllSongs(List<SongDTO> songDTOs) {
        return songRepository.saveAll(songDTOs.stream().map(SongDTO::toSong).collect(Collectors.toList())).stream().map(SongDTO::new).collect(Collectors.toList());
    }

    // get next x songs in queue
    public List<SongDTO> getNextXSongs(Integer venueId, Integer x) {
        if (!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        return venue.getPlaylist().getCurrentQueue(x).stream().map(SongDTO::new).collect(Collectors.toList());
    }

    // ************** Song Node Methods ************** //
    // create song node
    public SongNodeDTO createSongNode(SongNodeDTO songNodeDTO) {
        if (!songRepository.existsById(songNodeDTO.getSongId())) {
            throw new SongNotFoundException("Song with id: " + songNodeDTO.getSongId() + " not found");
        }
        if (!playlistRepository.existsById(songNodeDTO.getPlaylistId())) {
            throw new PlaylistNotFoundException("Playlist with id: " + songNodeDTO.getPlaylistId() + " not found");
        }
        //

        Song song = songRepository.findById(songNodeDTO.getSongId()).get();
        Playlist playlist = playlistRepository.findById(songNodeDTO.getPlaylistId()).get(); // might delete
        SongNode songNode = new SongNode(0, songNodeDTO.getPlaylistId(), song, songNodeDTO.getWeight());
        playlist.addSongRequest(songNode);
        playlistRepository.save(playlist);

        return new SongNodeDTO(songNode);
    }

    // update weight of song node
    public SongNodeDTO updateSongNodeWeight(Integer id, Double weight) {
        if (!songNodeRepository.existsById(id)) {
            throw new SongNotFoundException("Song Node with id: " + id + " not found");
        }
        SongNode songNode = songNodeRepository.findById(id).get();
        songNode.setWeight(weight);
        return new SongNodeDTO(songNodeRepository.save(songNode));
    }

    // add request
    public SongNodeDTO addSongRequest(SongRequestDTO songRequestDTO){
        // save the song request
        if(!customerRepository.existsById(songRequestDTO.getRequestedByUsername()))
            throw new CustomerNotFoundException("Customer with username: " + songRequestDTO.getRequestedByUsername() + " not found");

        if(!venueRepository.existsById(songRequestDTO.getRequestedInVenueId()))
            throw new VenueNotFoundException("Venue with id: " + songRequestDTO.getRequestedInVenueId() + " not found");

        if(!songRepository.existsById(songRequestDTO.getSongId()))
            throw new SongNotFoundException("Song with id: " + songRequestDTO.getSongId() + " not found");

        Customer customer = customerRepository.findById(songRequestDTO.getRequestedByUsername()).get();
        Wallet wallet = customer.getWallet();
        if(wallet.getBalance() < songRequestDTO.getCoinCost())
            throw new NotEnoughCoinsException("Customer with username: " + customer.getUsername() + " does not have enough coins");
        wallet.setBalance(wallet.getBalance() - songRequestDTO.getCoinCost());
        wallet.setTotalSpent(wallet.getTotalSpent() + songRequestDTO.getCoinCost());
        Venue venue = venueRepository.findById(songRequestDTO.getRequestedInVenueId()).get();
        Song song = songRepository.findById(songRequestDTO.getSongId()).get();
        songRequestDTO.setRequestDate(new Date());
        SongRequest songRequest = new SongRequest(0, song, customer, venue, songRequestDTO.getRequestDate(), songRequestDTO.getCoinCost());
        songRequestRepository.save(songRequest);
        walletRepository.save(wallet);
        // create song node according to song request
        SongNodeDTO songNodeDTO = new SongNodeDTO(songRequestDTO);
        songNodeDTO.setPlaylistId(venue.getPlaylist().getId());
        return addSongNode(songNodeDTO);
    }

    public SongNodeDTO addSongNode(SongNodeDTO songNodeDTO){
        // if songNode with song id and playlist id exists, update weight
        if (songNodeRepository.existsBySong_IdAndPlaylistId(songNodeDTO.getSongId(), songNodeDTO.getPlaylistId())) {
            SongNode songNode = songNodeRepository.findBySong_IdAndPlaylistId(songNodeDTO.getSongId(), songNodeDTO.getPlaylistId());
            songNode.setWeight(songNode.getWeight() + songNodeDTO.getWeight());
            return new SongNodeDTO(songNodeRepository.save(songNode));
        }
        // else create song node
        return createSongNode(songNodeDTO);
    }

    // delete song node
    public void deleteSongNode(Integer id) {
        if (!songNodeRepository.existsById(id)) {
            throw new SongNotFoundException("Song Node with id: " + id + " not found");
        }
        songNodeRepository.deleteById(id);
    }

    // get all song nodes
    public List<SongNodeDTO> getAllSongNodes() {
        return songNodeRepository.findAll().stream().map(SongNodeDTO::new).collect(Collectors.toList());
    }

    // get all song nodes in requested playlist of a specific venue sorted with compareTo
    public List<SongNodeDTO> getAllSongNodesByVenueId(Integer id) {
        if (!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        Venue venue = venueRepository.findById(id).get();
        List<SongNode> songNodes = venue.getPlaylist().getRequestedSongs().stream().sorted().toList();
        return songNodes.stream().map(SongNodeDTO::new).collect(Collectors.toList());
    }

    // ************** Song Request Methods ************** //
    // get song requests by venue id
    public List<SongRequestDTO> getSongRequestsByVenueId(Integer id) {
        if (!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        return songRequestRepository.findByRequestedInVenueId(id).stream().map(SongRequestDTO::new).collect(Collectors.toList());
    }

    // get song requests by customer username
    public List<SongRequestDTO> getSongRequestsByCustomerUsername(String username) {
        if (!customerRepository.existsById(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return songRequestRepository.findByRequestedByUsername(username).stream().map(SongRequestDTO::new).collect(Collectors.toList());
    }

    // get song requests by song id
    public List<SongRequestDTO> getSongRequestsBySongId(Integer id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        return songRequestRepository.findBySongId(id).stream().map(SongRequestDTO::new).collect(Collectors.toList());
    }

    // get all song requests
    public List<SongRequestDTO> getAllSongRequests() {
        return songRequestRepository.findAll().stream().map(SongRequestDTO::new).collect(Collectors.toList());
    }
}
