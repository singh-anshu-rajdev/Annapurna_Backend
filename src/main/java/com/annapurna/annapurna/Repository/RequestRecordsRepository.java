package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.RequestRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RequestRecordsRepository extends JpaRepository<RequestRecords,Long> {
}
