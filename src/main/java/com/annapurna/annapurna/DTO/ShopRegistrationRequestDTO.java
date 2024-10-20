package com.annapurna.annapurna.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopRegistrationRequestDTO {

    /* The shop Id */
    private Integer id;

    /* The user Id */
    private String userId;

    /* The shop shopName */
    private String shopName;

    /* The shop ownerName */
    private String ownerName;

    /* The shop lattitude */
    private Double lattitude;

    /* The shop longitude */
    private Double longitude;

    /* The shop shopPhoneNumber */
    private String shopPhoneNumber;

    /* The shop shopEmailId */
    private String shopMailId;

    /* The shop pinCode */
    private Long pinCode;

    /* The shop address */
    private String address;

    /* The shop description */
    private String description;
}
