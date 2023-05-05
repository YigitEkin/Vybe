package com.vybe.backend.controller;

import com.vybe.backend.model.dto.SongNodeDTO;
import com.vybe.backend.model.dto.SongRequestDTO;
import com.vybe.backend.service.SongService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/songRequests")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SongRequestController {

    @Resource
    private SongService songService;

    // get all song requests
    @GetMapping()
    public List<SongRequestDTO> getAllSongRequests() {
        return songService.getAllSongRequests();
    }

    // create a song request
    @PostMapping()
    public SongNodeDTO createSongRequest(@RequestBody SongRequestDTO songRequestDTO) {
        return songService.addSongRequest(songRequestDTO);
    }
}
