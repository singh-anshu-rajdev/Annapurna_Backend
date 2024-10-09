package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterDataResponseDTO {

    /** The UserResponseDTO */
    private UserResponseDTO userResponseDTO;

    /** The List of FeatureResponseDTO */
    private List<FeatureResponseDTO> featureResponseDTOList;
}
