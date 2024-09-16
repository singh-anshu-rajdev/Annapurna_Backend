package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File,Long> {

    File findByIdAndDeletedFlagFalse(Long fileId);
}
