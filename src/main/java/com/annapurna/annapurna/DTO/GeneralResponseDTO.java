package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GeneralResponseDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralResponseDTO {

    /* The Status */
    private Boolean status;

    /* The Messsage*/
    private String message;
}
