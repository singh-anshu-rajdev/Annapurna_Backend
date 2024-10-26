package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.Shops;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopsRepository extends JpaRepository<Shops,Integer> {

    /**
     *
     * @param shopId
     * @return
     */
    @Query("select s from Shops s where s.id = :shopId AND s.deletedFlag = false")
    Shops findByShopId(@Param("shopId") Integer shopId);

    /**
     *
     * @param pinCodeStart
     * @param pinCodeEnd
     * @param pageable
     * @return
     */
    @Query("select s from Shops s where s.pinCode BETWEEN :pinCodeStart AND :pinCodeEnd AND s.deletedFlag = false")
    List<Shops> findByPincodeAndDeletedFlag(@Param("pinCodeStart") String pinCodeStart,@Param("pinCodeEnd") String pinCodeEnd, Pageable pageable);

    /**
     *
     * @param pinCodeStart
     * @param pinCodeEnd
     * @return
     */
    @Query("select count(*) from Shops s where s.pinCode BETWEEN :pinCodeStart AND :pinCodeEnd AND s.deletedFlag = false")
    Integer countByPincodeAndDeletedFlag(@Param("pinCodeStart") String pinCodeStart,@Param("pinCodeEnd") String pinCodeEnd);
    /**
     *
     * @param shopId
     * @param mailId
     * @return
     */
    @Query("select s from Shops s where s.id = :shopId AND s.shopMailId = :mailId AND s.isMailVerified = true AND s.deletedFlag = false")
    Shops findByShopIdAndMailVerified(@Param("shopId") Integer shopId,@Param("mailId") String mailId);

    /**
     *
     * @param mailId
     * @return
     */
    @Query("select s from Shops s where s.shopMailId = :mailId AND s.isMailVerified = true AND s.deletedFlag = false")
    Shops findByMailIdAndMailVerified(@Param("mailId") String mailId);

    /**
     *
     * @param mailId
     * @return
     */
    @Query("select s from Shops s where s.shopMailId = :mailId AND s.deletedFlag = false")
    Shops findByShopMailId(@Param("mailId") String mailId);

    /**
     *
     * @param shopId
     * @param mailId
     * @return
     */
    @Query("select s from Shops s where s.shopMailId = :mailId AND s.id = :shopId AND s.deletedFlag = false")
    Shops findByIdAndShopMailId(@Param("shopId") Integer shopId,@Param("mailId") String mailId);

    /**
     *
     * @param phNumber
     * @return
     */
    @Query("select s from Shops s where s.shopPhNumber = :phNumber AND s.deletedFlag = false")
    Shops findByShopPhNumber(@Param("phNumber") String phNumber);
}
