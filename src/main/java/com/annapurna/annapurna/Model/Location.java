package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    /* The location Id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    /* The location Description */
    @Column(name = "location_desc")
    private String desc;

    /* The lattitude */
    @Column(name = "lattitude")
    private Double lattitude;

    /* The Longitude */
    @Column(name = "longitude")
    private Double longitude;

    /* The Address */
    @Column(name = "address")
    private String address;

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
