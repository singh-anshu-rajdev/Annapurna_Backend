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
public class NearestShopResponseDTO {

    /* The shop Response List */
    private List<ShopsResponseDTO> shopsResponseDTOList;

    /* The Total Number Of Page */
    private Integer totalNumberOfRecords;

    /* The Current Page Number */
    private Integer currentPageNumber;
}
