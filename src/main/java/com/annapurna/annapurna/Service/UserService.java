package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.UserRegistrationDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     *
     * @param userRegistrationDTO
     * @return
     */
    GeneralResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO);
}
