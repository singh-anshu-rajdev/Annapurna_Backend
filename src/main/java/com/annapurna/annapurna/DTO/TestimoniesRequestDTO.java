package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestimoniesRequestDTO {

    /* The Page Number */
    private Integer pageNumber;

    /* The Number of Records*/
    private Integer records;
}
