package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * SongNode class that will be used as a node in the priority queue
 * @author Harun Can Surav
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongNode implements Comparable<SongNode> {
    /**
     * Unique id of the song node
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * reference to the playlist id that the song node belongs to
     */
    private Integer playlist_id;

    /**
     * Song instance
     * {@link Song}
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
    private Song song;
    
    /**
     * Weight of the song in queue
     */
    private Double weight;

    /**
     * compare to method for comparable interface
     * @param songNode the other song node to compare to
     * @return the result of the compare operation
     */
    public int compareTo(SongNode songNode){
        return Integer.compare(this.song.getId(), songNode.song.getId());
    }
}
