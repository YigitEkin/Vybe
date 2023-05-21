package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDTO {
    private String image;
    private Long id;

    public ImageDTO(Image image) {
        this.id = image.getId();
        this.image = image.getData();
    }
}
