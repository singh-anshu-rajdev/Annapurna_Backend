package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearestShopRequestDTO {

    /* The userId */
    private String userId;

    /* The Lattitude */
    private Double lattitude;

    /* The Longitude */
    private Double longitude;

    /* The page Number */
    private Integer pageNumber;

    /* The Number of Records */
    private Integer NumberOfRecords;
}
