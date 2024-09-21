package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpVerification {

    /* The id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long id;

    /* The otp */
    @Column(name = "otp")
    private Integer otp;

    /* The unique_type */
    @Column(name = "unique_type")
    private String uniqueType;

    /* The valid TimeStamp */
    @Column(name = "valid_ts")
    private LocalDateTime validTs;

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

    /* The Is Active */
    @Column(name = "is_active")
    private Boolean isActive;

    /* The Deleted Flag */
    @Column(name = "deleted_flag")
    private Boolean deletedFlag;
}
