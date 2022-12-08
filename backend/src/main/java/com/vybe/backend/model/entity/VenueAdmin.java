package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * VenueAdmin class, representation of venue admins in the database
 * @author Harun Can Surav
 */
@Entity
@Data
@AllArgsConstructor
public class VenueAdmin extends User {

}
