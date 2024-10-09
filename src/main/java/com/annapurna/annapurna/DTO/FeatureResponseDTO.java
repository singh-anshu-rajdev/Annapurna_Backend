package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureResponseDTO {

    /** The Feature Name */
    private String featureName;

    /** The Feature Code*/
    private String featureCode;
}
