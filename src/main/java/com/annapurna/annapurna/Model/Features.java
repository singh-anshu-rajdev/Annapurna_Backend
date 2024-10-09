package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "features")
public class Features {

    /** The Feature Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Integer id;

    /** The Feature Name */
    @Column(name = "feature_name")
    private String name;

    /** The Feature Code */
    @Column(name = "feature_code")
    private String code;

    /** Is User Login */
    @Column(name = "is_login")
    private Boolean isLogin;

    /** The Enabled Flag*/
    @Column(name = "isEnabled")
    private Boolean isEnabled;

    /** The Deleted Flag*/
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
