package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.SongNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongNodeDTO {
    private Integer playlistId;
    private Integer songId;
    private Double weight;

    public SongNodeDTO(SongNode songNode){
        this.playlistId = songNode.getPlaylistId();
        this.songId = songNode.getSong().getId();
        this.weight = songNode.getWeight();
    }

    public SongNodeDTO(SongRequestDTO songRequestDTO){
        this.playlistId = songRequestDTO.getPlaylistId();
        this.songId = songRequestDTO.getSongId();
        this.weight = songRequestDTO.getCoinCost();
    }
}
