package com.annapurna.annapurna.Utils;

import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.OtpVerification;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.OtpVerificationRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableAsync
public class AsyncService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    /**
     * The LOGGER_MESSAGE_MAIL_FAILURE of type String
     */
    private static final String LOGGER_MESSAGE_MAIL_FAILURE = "Error in sending otp verification mail - {}";


    /**
     * The userRepository of type UserRepository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The otpVerificationRepository of type OtpVerificationRepository
     */
    @Autowired
    OtpVerificationRepository otpVerificationRepository;

    /**
     * SENDER's MAIL ADDRESS
     */
    @Value("${spring.mail.username}")
    String fromMail;

    /**
     * THE Java Mail Sender of type JavaMailSender
     */
    @Autowired
    JavaMailSender javaMailSender;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    /**
     *
     * @param id
     * @param otp
     */
    @Async
    public void saveOtpVerification(String id,Integer otp){

        // Deactivating the existing OTPS
        List<OtpVerification> otps = otpVerificationRepository.findByUniqueType(id);
        otps.parallelStream().forEach(ot ->{
            ot.setIsActive(AP_Constants.FALSE);
            ot.setUpdatedBy(AP_Constants.DEFAULT_USER);
            ot.setUpdatedTs(LocalDateTime.now());

        });
        otpVerificationRepository.saveAll(otps);

        // creating a new otp
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setOtp(otp);
        otpVerification.setUniqueType(id);
        otpVerification.setIsActive(AP_Constants.TRUE);
        otpVerification.setCreatedBy(AP_Constants.DEFAULT_USER);
        otpVerification.setCreatedTs(LocalDateTime.now());
        otpVerification.setDeletedFlag(AP_Constants.FALSE);
        otpVerification.setValidTs(LocalDateTime.now().plusMinutes(30));
        otpVerification.setUpdatedTs(LocalDateTime.now());
        otpVerification.setUpdatedBy(AP_Constants.DEFAULT_USER);

        otpVerificationRepository.save(otpVerification);
        return;
    }

    /**
     *
     * @param userType
     */
    @Async
    public void sendWelcomeMail(String userType) {
        try{

            // verifying the user
            User user = userRepository.getUserByEmailIdANDDeletedFlagFalse(userType);
            if(null!=user){
                user.setIsEmailVerified(true);
                user.setUpdatedBy(AP_Constants.DEFAULT_USER);
                user.setUpdatedTs(LocalDateTime.now());
                userRepository.save(user);

                // sending the mail
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(userType);

                // setting subject
                mailMessage.setSubject(AP_Constants.WELCOMING_SUBJECT);

                // setting Body
                String message = generalFunctions.buildRegistrationSuccessContent(user.getName());
                mailMessage.setText(message);
                mailMessage.setFrom(fromMail);
                javaMailSender.send(mailMessage);
            }else{
                if(userType.contains(AP_Constants.AT_THE_RATE)){
                    throw new CustomValidationException(ErrorCode.ERR_AP_2007);
                }else{
                    throw new CustomValidationException(ErrorCode.ERR_AP_2008);
                }
            }
        }catch (Exception ex){
            logger.error(LOGGER_MESSAGE_MAIL_FAILURE, ex.getMessage());
        }
    }
}
