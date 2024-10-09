package com.annapurna.annapurna.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureRequestDTO {

    /** The Feature Name*/
    @NotNull
    private String featureName;

    /** The Login Allowed*/
    @NotNull
    private Boolean isLogin;
}
