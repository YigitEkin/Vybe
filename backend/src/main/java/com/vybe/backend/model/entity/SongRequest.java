package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * SongRequest class that will be used as a transfer object for song requests
 * @author Harun Can Surav
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private Customer requestedBy;

    @OneToOne
    @JoinColumn(name = "requested_in_venue_id")
    private Venue requestedInVenue;

    /**
     * The song's request time
     */
    private Date requestDate;

    private Double coinCost;

}
