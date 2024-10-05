package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestimoniesResponseDTO {

    /* The List of Testimony */
    private List<TestimonyResponseDTO> data;

    /* The PageNumber */
    private Integer pageNumber;
    
    /* The number of Records */
    private Integer totalrecords;
}
