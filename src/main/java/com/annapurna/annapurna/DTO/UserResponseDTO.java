package com.annapurna.annapurna.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    /** The User Name*/
    private String userName;

    /** The Name*/
    private String name;

    /** The Image */
    private String image;

    /** The Email Id */
    private String emailId;

    /** The Phone Number*/
    private String phoneNumber;
}
