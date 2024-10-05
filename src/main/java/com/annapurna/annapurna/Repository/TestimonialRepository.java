package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.Testimonial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {

    /**
     *
     * @param pageable
     * @return
     */
    @Query("select tm from Testimonial tm where tm.deletedFlag = false")
    List<Testimonial> getAllTestimonies(Pageable pageable);

    /**
     *
     * @return
     */
    @Query("select count(tm) as count from Testimonial tm where tm.deletedFlag = false")
    Integer countAllTestimonies();

    /**
     *
     * @param userId
     * @return
     */
    @Query("select tm from Testimonial tm where tm.userId = :userId AND tm.deletedFlag = false")
    Testimonial getTestimonyByUserId(@Param("userId") Long userId);

    /**
     *
     * @param id
     * @return
     */
    @Query("select tm from Testimonial tm where tm.id = :id AND tm.deletedFlag = false")
    Testimonial getTestimonyById(@Param("id") Long id);

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    @Query("select tm from Testimonial tm where tm.id = :id AND tm.userId =:userId AND tm.deletedFlag = false")
    Testimonial getTestimonyByIdAndUserId(@Param("id") Long id,@Param("userId")Long userId);
}
