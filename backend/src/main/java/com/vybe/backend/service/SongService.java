package com.vybe.backend.service;


import com.vybe.backend.DTO.SongDTO;
import com.vybe.backend.exception.SongNotFoundException;
import com.vybe.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // get all songs
    public List<SongDTO> getAllSongs() {
        return songRepository.findAll().stream().map(SongDTO::new).collect(Collectors.toList());
    }

    // get song by id
    public SongDTO getSong(int id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        return new SongDTO(songRepository.findById(id).get());
    }

    // get song by name
    public SongDTO getSong(String name) {
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
    public SongDTO updateSong(int id, SongDTO songDTO) {
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
    public void deleteSong(int id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        songRepository.deleteById(id);
    }
}
