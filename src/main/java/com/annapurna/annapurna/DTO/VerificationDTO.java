package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationDTO {

    /* The mail Id */
    private String mail;

    /* The  shop Id */
    private Integer shopId;

    /* The phone Number */
    private String phoneNumber;

    /* The otp */
    private Integer otp;
}
