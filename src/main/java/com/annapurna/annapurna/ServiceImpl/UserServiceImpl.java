package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.File;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.JwtService;
import com.annapurna.annapurna.Service.UserService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * The fileRepository of type FileRepository
     */
    @Autowired
    FileRepository fileRepository;

    /**
     * The userRepository of type UserRepository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    /**
     * The authenticationManager of type AuthenticationManager
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * The userDetailsService of type UserDetailsService
     */
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * The jwtService of type JwtService
     */
    @Autowired
    JwtService jwtService;

    /**
     *
     * @param userRegistrationDTO
     * @return
     */
    @Override
    public GeneralResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(null!=userRegistrationDTO.getImageId()){
            File file = fileRepository.findByUniqueIdAndDeletedFlagFalse(userRegistrationDTO.getImageId());
            if(null==file || !file.getFileType().contains(AP_Constants.IMAGE)){
                throw new CustomValidationException(ErrorCode.ERR_AP_2011);
            }
        }

        GeneralResponseDTO response = new GeneralResponseDTO();
        response.setMessage(AP_Constants.USER_CREATED_SUCCESSFULLY);
        response.setStatus(AP_Constants.TRUE);
        try{
            User user = new User();
            user.setName(userRegistrationDTO.getName());
            user.setUserName(userRegistrationDTO.getUserName());
            user.setImageUniqueId(userRegistrationDTO.getImageId());
            user.setPassword(generalFunctions.passwordEncoder(userRegistrationDTO.getPassword()));
            user.setClientId(AP_Constants.USER_CLIENT_ID);
            user.setRoles(AP_Constants.ROLE_USER);
            user.setEmailId(userRegistrationDTO.getEmailId());
            user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
            user.setIsEmailVerified(AP_Constants.FALSE);
            user.setIsPhoneVerified(AP_Constants.FALSE);
            user.setCreatedBy(AP_Constants.DEFAULT_USER);
            user.setCreatedTs(LocalDateTime.now());
            user.setDeletedFlag(AP_Constants.FALSE);
            user.setUpdatedBy(AP_Constants.ROLE_USER);
            user.setUpdatedTs(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception ex) {
            LOGGER.error("Error in registering the user - {}",ex.getMessage());
            response.setMessage(AP_Constants.USER_REGISTRATION_FAILED);
            response.setStatus(AP_Constants.FALSE);
        }
        return response;
    }

    /**
     *
     * @param loginRequestDTO
     * @return
     */
    @Override
    public String generateAuthToken(LoginRequestDTO loginRequestDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginRequestDTO.getUserId(),loginRequestDTO.getPassword()));
        }catch (Exception ex){
            throw new CustomValidationException(ErrorCode.ERR_AP_2012);
        }
        return jwtService.generateToken(loginRequestDTO.getUserId());
    }

    /**
     *
     * @param dataValidatingRequestDTO
     * @return
     */
    @Override
    public DataValidatingResponseDTO checkExistingData(DataValidatingRequestDTO dataValidatingRequestDTO) {

        DataValidatingResponseDTO response = new DataValidatingResponseDTO();

        // checking the request Body
        if (null == dataValidatingRequestDTO ||
                (null == dataValidatingRequestDTO.getEmailId()
                        && null == dataValidatingRequestDTO.getPhoneNumber())) {
            throw new CustomValidationException(ErrorCode.ERR_AP_2006);
        }

        User user = null;
        // validating the data
        if (null != dataValidatingRequestDTO.getEmailId()) {
            response.setUniqueValue(dataValidatingRequestDTO.getEmailId());
            user = userRepository.getUserByEmailIdAndDeletedFlagFalse(dataValidatingRequestDTO.getEmailId());
        } else {
            response.setUniqueValue(dataValidatingRequestDTO.getPhoneNumber());
            user = userRepository.getUserByPhoneNumberAndDeletedFlagFalse(dataValidatingRequestDTO.getPhoneNumber());
        }

        // check for existence
        if (null == user) {
            response.setIsExisting(AP_Constants.FALSE);
        } else {
            response.setIsExisting(AP_Constants.TRUE);
        }
        return  response;
    }
}
