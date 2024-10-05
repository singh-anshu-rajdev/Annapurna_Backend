package com.annapurna.annapurna.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Testimonial {

    /* The Testimony Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testimony_id")
    private Long id;

    /* The userId */
    @Column(name = "user_id")
    private Long userId;

    /* The message */
    @Column(name = "message")
    private String message;

    /* The Rating */
    @Column(name = "rating")
    private Double rating;

    /* The Deleted Flag */
    @Column(name = "deleted_flag")
    private Boolean deletedFlag;

    /* The Created Ts */
    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    /* The Updated Ts */
    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    /* The Created By */
    @Column(name = "created_by")
    private String createdBy;

    /* The Updated By */
    @Column(name = "updated_by")
    private String updatedBy;

}
