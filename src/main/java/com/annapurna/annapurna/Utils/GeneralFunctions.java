package com.annapurna.annapurna.Utils;

import com.annapurna.annapurna.DTO.UserCacheDTO;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GeneralFunctions {

    /**
     * The fileRepository of type FileRepository
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     * The passwordEncoder of type PasswordEncoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The jwtService of type JwtService
     */
    @Autowired
    private JwtService jwtService;

    /**
     * Method to build plain text content for OTP email
     *
     * @param userName
     * @param otp
     * @return
     */
    public String buildOtpEmailContent(String userName, Integer otp) {
        return "Hi " + userName + ",\n\n"
                + "Thank you for registering with us. Your OTP for verification is: " + otp + "\n\n"
                + "Please enter this OTP to complete the verification process. The OTP is valid for 30 minutes.\n\n\n"
                + "If you have not initiated this mail, You can safely ignore this mail.\n\n"
                + "Best regards,\n"
                + "Annapurna";
    }

    /**
     * Method to build plain text content for registration success email
     *
     * @param userName
     * @return
     */
    public String buildRegistrationSuccessContent(String userName) {
        return "Hi " + userName + ",\n\n"
                + "Congratulations! Your registration was successful.\n"
                + "Thank you for joining. Enjoy your meal with Annapurna!\n\n"
                + "You can now log in to your account and explore our services.\n\n"
                + "Best regards,\n"
                + "Annapurna";
    }

    /**
     * Method generateUniqueIdForProfilePictures
     *
     * @return
     */
    public String generateUniqueIdForProfilePictures(){
        String uniqueId = UUID.randomUUID().toString();
        List<Long> id = fileRepository.findLastCreatedId(PageRequest.of(0,1));
        if(null!=id && !id.isEmpty()){
            uniqueId = uniqueId.substring(0,24) + id.get(0);
        }else{
            uniqueId = uniqueId.substring(0,23) ;
        }
        return uniqueId;
    }

    /**
     *
     * @param password
     * @return
     */
    public String passwordEncoder(String password){
        return passwordEncoder.encode(password);
    }

    /**
     *
     * @param httpServletRequest
     * @return
     */
    public UserCacheDTO getUserCache(HttpServletRequest httpServletRequest){
        UserCacheDTO response = null;
        if(null!=httpServletRequest.getHeader(AP_Constants.AUTHORIZATION)){
            String request = httpServletRequest.getHeader(AP_Constants.AUTHORIZATION).substring(AP_Constants.NUMBER_SEVEN);
            response =  jwtService.extractUserCacheFromtoken(request);
        }
        return response;
    }

    public String generateCode() {
        return UUID.randomUUID().toString().substring(0,3)+UUID.randomUUID().toString().substring(0,3);
    }
}
