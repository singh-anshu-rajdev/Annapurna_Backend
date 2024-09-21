package com.annapurna.annapurna.Utils;

import org.springframework.stereotype.Service;

@Service
public class GeneralFunctions {


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
}
