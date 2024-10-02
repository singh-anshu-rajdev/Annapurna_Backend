package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.UserCacheDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface JwtService {

    /**
     *
     * @param userNameOrEmail
     * @return
     */
    String generateToken(String userNameOrEmail);

    /**
     *
     * @param token
     * @param userDetails
     * @return
     */
    Boolean validateToken(String token, UserDetails userDetails);

    /**
     *
     * @param token
     * @return
     */
    String extractUserName(String token);

    /**
     *
     * @param token
     * @return
     */
    Date extractExpiration(String token);

    /**
     *
     * @param token
     * @return
     */
    UserCacheDTO extractUserCacheFromtoken(String token);

    /**
     *
     * @param userNameOrEmail
     * @return
     */
    String generateRefreshToken(String userNameOrEmail);

    /**
     *
     * @param token
     * @param userName
     * @return
     */
    Boolean validateToken(String token, String userName);
}
