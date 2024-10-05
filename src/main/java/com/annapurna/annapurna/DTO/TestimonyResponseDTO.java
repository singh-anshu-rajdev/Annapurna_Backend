package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestimonyResponseDTO {

    /* The Name */
    private Long testimonyId;

    /* The user Image */
    private String image;

    /* The Message */
    private String message;

    /* The Rating */
    private Double rating;
}
