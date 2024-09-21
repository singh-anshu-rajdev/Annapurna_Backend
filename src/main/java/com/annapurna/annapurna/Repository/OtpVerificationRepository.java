package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification,Long> {

    /**
     *
     * @param type
     * @param validTs
     * @return
     */
    @Query("SELECT ov FROM OtpVerification ov WHERE ov.uniqueType = :type AND ov.deletedFlag = false"
            + " AND ov.validTs > :validTs AND ov.isActive = true")
    List<OtpVerification> findByUniqueTypeAndValidTs(@Param("type") String type, @Param("validTs") LocalDateTime validTs);

    /**
     *
     * @param type
     * @return
     */
    @Query("SELECT ov FROM OtpVerification ov WHERE ov.uniqueType = :type AND ov.deletedFlag = false"
            + " AND ov.isActive = true")
    List<OtpVerification> findByUniqueType(@Param("type") String type);

}
