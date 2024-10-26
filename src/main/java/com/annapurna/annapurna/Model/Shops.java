package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shops{

    /* The shop Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Integer id;

    /* The shop Name */
    @Column(name = "shop_name")
    private String shopName;

    /* The shop owner id */
    @Column(name = "owner_id")
    private Integer shopOwnerId;

    /* The shop phone Number */
    @Column(name = "shop_phNumber")
    private String shopPhNumber;

    /*  isphNumberVerified Flag */
    @Column(name = "is_ph_num_verified")
    private Boolean isphNumberVerified;

    /* The shop Mail Id */
    @Column(name = "shop_mail_Id")
    private String shopMailId;

    @Column(name = "shop_rating")
    private Double shopRating;

    /* The isMailVerified Flag */
    @Column(name = "is_mail_verified")
    private Boolean isMailVerified;

    /* The shop pincode */
    @Column(name = "pincode")
    private Long pinCode;

    /* The shop location */
    @Column(name = "location_id_fk")
    private Integer location;

    /* The shop Description */
    @Column(name = "shop_desc")
    private String shopDesc;

    /* The Deleted Flag */
    @Column(name = "deleted_flag")
    private Boolean deletedFlag;

    /* The Created By */
    @Column(name = "created_by")
    private String createdBy;

    /* The Created TimeStamp */
    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    /* The Updated By */
    @Column(name = "updated_by")
    private String updatedBy;

    /*The Updated TimeStamp */
    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

}
