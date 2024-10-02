package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {

    /* The userName */
    private String userName;

    /* The accessToken */
    private String accessToken;

    /* The refreshToken */
    private String refreshToken;
}
