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
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    /* The File Name*/
    @Column(name = "file_name")
    private String fileName;

    /* The File Type*/
    @Column(name = "file_type")
    private String fileType;

    /* The Type */
    @Column(name = "type")
    private String type;

    /* The File Data */
    @Lob
    @Column(name = "file_data",columnDefinition = "LONGBLOB")
    private byte[] fileData;

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

    /* The Deleted Flag */
    @Column(name = "deleted_flag")
    private Boolean deletedFlag;
}
