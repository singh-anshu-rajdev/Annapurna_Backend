package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     *
     * @param userRegistrationDTO
     * @return
     */
    GeneralResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO);

    /**
     *
     * @param loginRequestDTO
     * @return
     */
    TokenResponseDTO generateAuthToken(LoginRequestDTO loginRequestDTO);

    /**
     *
     * @param dataValidatingRequestDTO
     * @return
     */
    DataValidatingResponseDTO checkExistingData(DataValidatingRequestDTO dataValidatingRequestDTO);

    /**
     *
     * @param refreshTokenRequestDTO
     * @return
     */
    TokenResponseDTO generateTokenFromRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
