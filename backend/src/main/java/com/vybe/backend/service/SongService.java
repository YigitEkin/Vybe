package com.vybe.backend.service;


import com.vybe.backend.exception.PlaylistNotFoundException;
import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.exception.SongNotFoundException;
import com.vybe.backend.model.dto.SongNodeDTO;
import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.SongNode;
import com.vybe.backend.repository.PlaylistRepository;
import com.vybe.backend.repository.SongNodeRepository;
import com.vybe.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    SongRepository songRepository;
    PlaylistRepository playlistRepository;
    SongNodeRepository songNodeRepository;

    @Autowired
    public SongService(SongRepository songRepository, PlaylistRepository playlistRepository, SongNodeRepository songNodeRepository) {
        this.songNodeRepository = songNodeRepository;
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
    }

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

    // -----Song Node Methods-----
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
    public SongNodeDTO addSongRequest(SongNodeDTO songNodeDTO){
        // if songNode with song id and playlist id exists, update weight
        if (songNodeRepository.existsBySong_IdAndPlaylistId(songNodeDTO.getSongId(), songNodeDTO.getPlaylistId())) {
            SongNode songNode = songNodeRepository.findBySong_IdAndPlaylistId(songNodeDTO.getSongId(), songNodeDTO.getPlaylistId());
            songNode.setWeight(songNode.getWeight() + 1);
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


}
