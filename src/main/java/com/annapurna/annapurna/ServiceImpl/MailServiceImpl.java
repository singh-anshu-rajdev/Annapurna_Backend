package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.VerificationDTO;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.OtpVerification;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.OtpVerificationRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.MailService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {


    /**
     *  Logger instance to log important events and errors in the service.
     */
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    /**
     * The LOGGER_MESSAGE_MAIL_FAILURE of type String
     */
    private static final String LOGGER_MESSAGE_MAIL_FAILURE = "Error in sending otp verification mail - {}";

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
     * The otpVerificationRepository of type OtpVerificationRepository
     */
    @Autowired
    OtpVerificationRepository otpVerificationRepository;

    /**
     * The userRepository of type UserRepository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The asyncService of type AsyncService
     */
    @Autowired
    AsyncService asyncService;

    /**
     * Sends an email and logs different stages of the process.
     *
     * @param verificationDTO   recipient's email address
     * @throws Exception if email sending fails
     */
    @Override
    public GeneralResponseDTO sendOtpForVerification(VerificationDTO verificationDTO) {

        // Response Body
        GeneralResponseDTO response = new GeneralResponseDTO();
        response.setStatus(AP_Constants.TRUE);
        try{
            Random random = new Random();
            Integer otp = 100000 + random.nextInt(900000);

            // saving otp data asynchronously
            asyncService.saveOtpVerification(verificationDTO.getMail(),otp);

            // sending the mail
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(verificationDTO.getMail());

            // setting subject
            mailMessage.setSubject(AP_Constants.OTP_VERIFICATION_SUBJECT);

            // setting Body
            String message = AP_Constants.OTP_VERIFICATION_BODY + AP_Constants.SPACE + otp;
            mailMessage.setText(message);
            mailMessage.setFrom(fromMail);
            javaMailSender.send(mailMessage);
            response.setMessage(AP_Constants.MAIL_SEND_SUCCESS_MESSAGE);
        }catch (Exception ex){
            logger.error(LOGGER_MESSAGE_MAIL_FAILURE, ex.getMessage());
            response.setStatus(AP_Constants.FALSE);
            response.setMessage(AP_Constants.MAIL_SEND_FAILURE_MESSAGE);
        }

        return response;
    }

    /**
     *
     * @param verificationDTO
     * @return
     */
    @Override
    public GeneralResponseDTO verifyOtp(VerificationDTO verificationDTO) {
        // Response Body
        GeneralResponseDTO response = new GeneralResponseDTO();
        response.setStatus(AP_Constants.TRUE);

        if (null == verificationDTO.getMail() && null == verificationDTO.getPhoneNumber()) {
            throw new CustomValidationException(ErrorCode.ERR_AP_2006);
        }

        // fetch the otp from db
        List<OtpVerification> otps = validationOtp(verificationDTO);

        //Found the otp
        OtpVerification otpVerification = otps.get(0);

        // Deactiving all the Otp of the user
        if (otpVerification.getOtp().equals(verificationDTO.getOtp())) {

            // welcoming mail only for mailId users
            if(null!=verificationDTO.getMail()){
                asyncService.sendWelcomeMail(verificationDTO.getMail());
            }
            response.setMessage(AP_Constants.USER_VERIFIED);
            otps.parallelStream().forEach(ot -> {
                ot.setIsActive(AP_Constants.FALSE);
                ot.setUpdatedBy(AP_Constants.DEFAULT_USER);
                ot.setUpdatedTs(LocalDateTime.now());
            });
            otpVerificationRepository.saveAll(otps);
        } else {
            // otp does not match
            throw new CustomValidationException(ErrorCode.ERR_AP_2005);
        }
        return response;
    }

    public List<OtpVerification> validationOtp(VerificationDTO verificationDTO){
        List<OtpVerification> otps = null;


        if (null != verificationDTO.getMail()) {
            // validation for mail
            User user = userRepository.getUserByEmailIdDeletedFlagFalse(verificationDTO.getMail());
            if(null!=user){
                throw new CustomValidationException(ErrorCode.ERR_AP_2009);
            }
            otps = otpVerificationRepository.findByUniqueTypeAndValidTs(verificationDTO.getMail(), LocalDateTime.now());
            if (null == otps || otps.isEmpty()) {
                throw new CustomValidationException(ErrorCode.ERR_AP_2007); // cannot find otp by mail
            }
        } else {
            // validation for phone number
            User user = userRepository.getUserByPhoneNumberDeletedFlagFalse(verificationDTO.getPhoneNumber());
            if(null!=user){
                throw new CustomValidationException(ErrorCode.ERR_AP_2010);
            }
            otps = otpVerificationRepository.findByUniqueTypeAndValidTs(verificationDTO.getPhoneNumber(), LocalDateTime.now());
            if (null == otps || otps.isEmpty()) {
                throw new CustomValidationException(ErrorCode.ERR_AP_2008); // cannot find otp by Number
            }
        }
        return otps;
    }
}
