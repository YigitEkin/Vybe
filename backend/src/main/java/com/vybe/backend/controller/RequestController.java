package com.vybe.backend.controller;

import com.vybe.backend.model.dto.SongNodeDTO;
import com.vybe.backend.model.dto.SongRequestDTO;
import com.vybe.backend.service.SongService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/request")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class RequestController {
    @Resource
    private SongService songService;
    @PostMapping
    public SongNodeDTO requestSong(@RequestBody SongRequestDTO songRequestDTO) {
        return songService.addSongRequest(songRequestDTO);
    }
}
