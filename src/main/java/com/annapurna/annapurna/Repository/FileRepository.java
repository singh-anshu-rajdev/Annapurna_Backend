package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long>  {

    File findByUniqueIdAndDeletedFlagFalse(String fileId);

    @Query("SELECT f.id FROM File f ORDER BY f.createdTs DESC")
    List<Long> findLastCreatedId(Pageable pageable);
}
