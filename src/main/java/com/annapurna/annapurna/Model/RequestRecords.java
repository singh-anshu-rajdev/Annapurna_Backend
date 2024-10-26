package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_records")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long Id;

    @Column(name = "request_body")
    private String requestBody;

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
