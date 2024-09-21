package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.VerificationDTO;
import com.annapurna.annapurna.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping("/unsecure/sendOtp")
    public ResponseEntity<GeneralResponseDTO> sendOtpForVerification(@RequestBody VerificationDTO verificationDTO){
        return new ResponseEntity<>(mailService.sendOtpForVerification(verificationDTO), HttpStatus.OK);
    }

    @PostMapping("/unsecure/verifyOtp")
    public ResponseEntity<GeneralResponseDTO> verifyOtp(@RequestBody VerificationDTO verificationDTO){
        return new ResponseEntity<>(mailService.verifyOtp(verificationDTO), HttpStatus.OK);
    }
}
