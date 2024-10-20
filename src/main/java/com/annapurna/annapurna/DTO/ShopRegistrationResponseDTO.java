package com.annapurna.annapurna.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopRegistrationResponseDTO {

    /* The shop Id */
    private Integer shopId;

    /* The status */
    private Boolean status;

    /* The message */
    private String message;
}
