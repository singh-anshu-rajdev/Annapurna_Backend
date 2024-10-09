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
public class FeatureDataRequestDTO {

    /** The FeatureRequestDTO List */
    List<FeatureRequestDTO> featureRequestDTOList;
}
