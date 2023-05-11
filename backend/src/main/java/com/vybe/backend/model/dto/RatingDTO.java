package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Integer id;
    private Double rating;
    private String date;
    private String customerUsername;
    private Integer venueId;

    public Rating toRating() {
        Rating rating = new Rating();
        rating.setRating(this.rating);

        return rating;
    }

    public RatingDTO(Rating rating) {
        this.rating = rating.getRating();
        this.date = rating.getDate().toString();
        this.customerUsername = rating.getRatedBy().getUsername();
        this.venueId = rating.getVenue().getId();
        this.id = rating.getId();
    }

    public static java.util.Set<RatingDTO> toRatingDTOSet(java.util.Set<Rating> ratings) {
        java.util.Set<RatingDTO> ratingDTOS = new java.util.HashSet<>(java.util.Set.of());
        for (Rating rating : ratings) {
            ratingDTOS.add(new RatingDTO(rating));
        }
        return ratingDTOS;
    }

}
