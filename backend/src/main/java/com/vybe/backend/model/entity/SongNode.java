package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * SongNode class that will be used as a node in the priority queue
 * @author Harun Can Surav
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongNode implements Comparable<SongNode> {
    public SongNode(Integer id, Integer playlistId, Song song, Double weight){
        this.id = id;
        this.playlistId = playlistId;
        this.song = song;
        this.weight = weight;
        this.requestDate = new Date();
    }

    /**
     * Unique id of the song node
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * reference to the playlist id that the song node belongs to
     */
    // force the playlist id to be not null
    private Integer playlistId;

    /**
     * Song instance
     * {@link Song}
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "song_id")
    private Song song;
    
    /**
     * Weight of the song in queue
     */
    private Double weight;

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    /**
     * compare to method for comparable interface
     * @param songNode the other song node to compare to
     * @return the result of the compare operation
     */
    public int compareTo(SongNode songNode){
        // first compares the weight, for equal weights, compares the request date, the one with the earlier date is bigger one
        if (this.weight > songNode.weight){
            return 1;
        } else if (this.weight < songNode.weight){
            return -1;
        } else {
            return songNode.requestDate.compareTo(this.requestDate);
        }
    }
}
