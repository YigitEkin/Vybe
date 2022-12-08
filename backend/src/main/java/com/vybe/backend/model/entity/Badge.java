package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Badge class that is similar to an achievement
 * @author Oğuz Ata Çal
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    /**
     * Unique name of the badge
     */
    @Id
    private String name;

    /**
     * Description of the badge
     */
    private String description;


    /**
     * Icon of the badge
     */
     // TODO: decide of the type of the object
    @Transient
    private Object image;
}
