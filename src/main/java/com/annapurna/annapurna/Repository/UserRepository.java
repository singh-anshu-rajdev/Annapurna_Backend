package com.annapurna.annapurna.Repository;

import com.annapurna.annapurna.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     *
     * @param userName
     * @return
     */
    @Query("SELECT u FROM User u WHERE (u.emailId = :userName OR u.userName = :userName) AND u.deletedFlag = false")
    Optional<User> getUserByUserNameOrEmailAndDeletedFlagFalse(@Param("userName") String userName);


    /**
     *
     * @param emailId
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.emailId = :emailId AND u.isEmailVerified = true AND u.deletedFlag = false")
    User getUserByEmailIdDeletedFlagFalse(@Param("emailId") String emailId);

    /**
     *
     * @param emailId
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.emailId = :emailId AND u.deletedFlag = false")
    User getUserByEmailIdANDDeletedFlagFalse(@Param("emailId") String emailId);

    /**
     *
     * @param phoneNumber
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber And u.isPhoneVerified = true AND u.deletedFlag = false")
    User getUserByPhoneNumberDeletedFlagFalse(@Param("phoneNumber") String phoneNumber);

    /**
     *
     * @param phoneNumber
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND u.deletedFlag = false")
    User getUserByPhoneNumberANDDeletedFlagFalse(@Param("phoneNumber") String phoneNumber);


}
