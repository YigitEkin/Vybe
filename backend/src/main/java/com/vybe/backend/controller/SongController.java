package com.vybe.backend.controller;

import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.model.dto.SongRequestDTO;
import com.vybe.backend.service.SongService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")

// TODO: restrict origins
public class SongController {

    @Resource
    private SongService songService;

    @GetMapping("/{songId}")
    public com.vybe.backend.model.dto.SongDTO getSong(@PathVariable Integer songId) {
        return songService.getSong(songId);
    }

    @GetMapping()
    public java.util.List<SongDTO> getAllSongs() {
        return songService.getAllSongs();
    }

    // get song requests for a song
    @GetMapping("/{songId}/songRequests")
    public java.util.List<SongRequestDTO> getSongRequests(@PathVariable Integer songId) {
        return songService.getSongRequestsBySongId(songId);
    }
}
