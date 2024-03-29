package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Analytics class that holds possible statistics and badges
 * @author Oğuz Ata Çal
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Analytics {
    /**
     * Unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * List of badges acquired 
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "analytics_id")
    private List<Badge> badges;

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
