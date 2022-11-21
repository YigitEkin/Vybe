package com.vybe.backend.model.entity;

/**
 * SongNode class that will be used as a node in the priority queue
 * @author Harun Can Surav
 */
public class SongNode {
    /**
     * Song instance
     * {@link Song}
     */
    private Song song;
    /**
     * Weight of the song in queue
     */
    private Double weight;
}
