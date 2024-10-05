package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestimonyRequestDTO {

    /* The User Id */
    private Long userId;

    /* The Testimony Id */
    private Long testimonyId;

    /* The Message */
    private String message;

    /* The Rating */
    private Double rating;
}
