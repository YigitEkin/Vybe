package com.vybe.backend.model.entity;

import java.util.List;

/**
 * Analytics class that holds possible statistics and badges
 * @author Oğuz Ata Çal
 */
public class Analytics {
    /**
     * List of badges acquired 
     */
    private List<String> badges;

    /**
     * points of the owner of the analytics instance
     */
    private Integer points;

    /**
     * Increases the points by a variable amount
     * @param points amount of points to increase
     * @return TRUE if the points were increased successfully, FALSE otherwise
     */
    public Integer increasePoints(Integer points) {
        return null;
    }
}
