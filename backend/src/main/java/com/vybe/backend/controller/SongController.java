package com.vybe.backend.controller;

import com.vybe.backend.service.SongService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/song")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SongController {

    @Resource
    private SongService songService;

    //@PostMapping("/")
}
