package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataValidatingResponseDTO {

    /* The uniqueValue */
    private String uniqueValue;

    /* The isExisting */
    private Boolean isExisting;
}
