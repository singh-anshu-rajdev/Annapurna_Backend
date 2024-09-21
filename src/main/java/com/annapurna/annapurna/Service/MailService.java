package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.VerificationDTO;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    /**
     *
     * @param verificationDTO
     * @return
     */
    GeneralResponseDTO sendOtpForVerification(VerificationDTO verificationDTO);

    /**
     *
     * @param verificationDTO
     * @return
     */
    GeneralResponseDTO verifyOtp(VerificationDTO verificationDTO);
}
