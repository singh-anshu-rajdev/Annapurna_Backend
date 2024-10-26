package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopsResponseDTO {

    /* The Shop Name */
    private String shopName;

    /* The shop Distance*/
    private Double shopDist;

    /* The shop Rating */
    private Double shopRating;

    /* The shop Description */
    private String shopDesc;

    /* The shop MailId */
    private String shopMailId;

    /* The shop Phone Number */
    private String shopPhNumber;
}
