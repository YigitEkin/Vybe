package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Streak;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreakDTO {
    private Integer streak;
    private Date lastVisitDate;
    private String venueName;
    private String username;

    public StreakDTO(Streak streak){
        this.streak = streak.getStreak();
        this.lastVisitDate = streak.getLastVisitDate();
        this.venueName = streak.getVenue().getName();
        this.username = streak.getCustomer().getUsername();
    }
}
