package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataValidatingRequestDTO {

    /* The phoneNumber */
    private String phoneNumber;

    /* The emailId */
    private String emailId;
}
