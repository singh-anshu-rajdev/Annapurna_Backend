package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.Features;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeaturesRepository extends JpaRepository<Features,Integer> {

    /**
     *
     * @param isLogin
     * @return
     */
    @Query("select f from Features f where f.isLogin = :isLogin AND f.deletedFlag = false")
    List<Features> findByIsLoginFalse(@Param("isLogin") Boolean isLogin);

    /**
     *
     * @return
     */
    @Query("select f from Features f where f.deletedFlag = false")
    List<Features> findAllFeatures();
}
