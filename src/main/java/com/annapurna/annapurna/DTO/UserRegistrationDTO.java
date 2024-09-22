package com.annapurna.annapurna.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {

    /* Name */
    @NonNull
    private String name;

    /* Email Id */
    @NonNull
    private String emailId;

    /* Phone Number */
    @NonNull
    private String phoneNumber;

    /* Image Unique Id */
    private String imageId;

    /* User Name */
    @NonNull
    private String userName;

    /* Password */
    @NonNull
    private String password;
}
