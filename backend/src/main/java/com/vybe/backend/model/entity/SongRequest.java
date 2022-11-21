package com.vybe.backend.model.entity;
/**
 * SongRequest class that will be used as a transfer object for song requests
 * @author Harun Can Surav
 */
public class SongRequest {
    /**
     * Song instance
     * {@link Song}
     */
    private Song song;
    /**
     * User who requested the song
     * {@link User}
     */
    private User requestedBy;
    /**
     * The song's request time
     */
    private String requestDate;

}
