package com.vybe.backend.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerUsername;
    private Integer venueId;
    private String venueName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate; // not necessary for creation, will be set by the backend
}
