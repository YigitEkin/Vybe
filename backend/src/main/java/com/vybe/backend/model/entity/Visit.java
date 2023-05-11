package com.vybe.backend.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
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
