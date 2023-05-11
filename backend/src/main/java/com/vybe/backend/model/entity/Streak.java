package com.vybe.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Streak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToOne
    @NotNull
    private Venue venue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitDate;

    private int streak;
}
