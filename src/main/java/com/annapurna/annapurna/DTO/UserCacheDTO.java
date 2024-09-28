package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCacheDTO {

    private String userName;
    private String userId;
    private String name;
    private Long roleId;
    private Integer clientId;
}
