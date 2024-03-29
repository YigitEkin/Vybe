package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Admin class that will be permitted to adjust the system
 * @author Oğuz Ata Çal
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    /**
     * Unique username
     */
    @Id
    private String username;

    /**
     * password
     */
    private String password;
}
