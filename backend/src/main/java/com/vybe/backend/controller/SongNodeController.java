package com.vybe.backend.controller;

import com.vybe.backend.service.SongService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/songNodes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SongNodeController {

    @Resource
    private SongService songService;

    // get all song nodes
    @GetMapping()
    public java.util.List<com.vybe.backend.model.dto.SongNodeDTO> getAllSongNodes() {
        return songService.getAllSongNodes();
    }
}
