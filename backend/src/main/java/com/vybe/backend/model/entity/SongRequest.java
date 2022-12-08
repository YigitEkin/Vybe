package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * SongRequest class that will be used as a transfer object for song requests
 * @author Harun Can Surav
 */
@Data
@Entity
public class SongRequest {
    /**
     * Unique song request id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Song instance
     * {@link Song}
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
    private Song song;

    /**
     * User who requested the song
     * {@link User}
     */
    @OneToOne
    @JoinColumn(name = "requested_by_username")
    private User requestedBy;

    /**
     * The song's request time
     */
    private String requestDate;

}
