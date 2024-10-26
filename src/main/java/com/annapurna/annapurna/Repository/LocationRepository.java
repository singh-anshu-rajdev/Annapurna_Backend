package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("select l from Location l where l.id IN :locationIds AND l.deletedFlag = false")
    List<Location> findByLocationIds(@Param("locationIds") List<Integer> locationIds);
}
