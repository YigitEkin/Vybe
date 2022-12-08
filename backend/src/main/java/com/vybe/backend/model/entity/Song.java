package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Song class that will be used to represent a song in the database
 * @author Harun Can Surav
 */
@Data
@Entity
public class Song {
    /**
     * The song's id
     */
    @Id
    private Integer id;
    /**
     * The song's name
     */
    private String name;
    /**
     * The song's artist
     */
    private String artist;
    /**
     * Album art url
     */
    // TODO: Decide on Object type
    private String albumArt;
    /**
     * The song's link in SoundTrackYourBrand
     */
    private String link;
}
