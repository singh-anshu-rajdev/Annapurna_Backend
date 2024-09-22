package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /* Unique Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long Id;

    /* Name */
    @Column(name = "full_name")
    private String name;

    /* Email Id */
    @Column(name = "emailId")
    private String emailId;

    /* Email Id Verification */
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    /* Phone Number */
    @Column(name = "phoneNumber")
    private String phoneNumber;

    /* Phone Number Verification */
    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified;

    /* Image Url */
    @Column(name = "profile_image_id")
    private String imageUniqueId;

    /* User Name */
    @Column(name = "userName")
    private String userName;

    /* Password */
    @Column(name = "password")
    private String password;

    /* Roles */
    @Column(name = "roles")
    private String roles;

    /* Created By */
    @Column(name = "created_by")
    private String createdBy;

    /* Created TimeStamp */
    @Column(name = "created_Ts")
    private LocalDateTime createdTs;

    /* Updated By */
    @Column(name = "updated_by")
    private String updatedBy;

    /* Updated TimeStamp */
    @Column(name = "updated_Ts")
    private LocalDateTime updatedTs;

    /* Deleted Flag */
    @Column(name = "deleted_flag")
    private Boolean deletedFlag;

    /* Client Id */
    @Column(name = "client_id")
    private Integer clientId;
}
