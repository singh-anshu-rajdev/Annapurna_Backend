package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.File;
import com.annapurna.annapurna.Model.Location;
import com.annapurna.annapurna.Model.Shops;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Repository.LocationRepository;
import com.annapurna.annapurna.Repository.ShopsRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.JwtService;
import com.annapurna.annapurna.Service.MailService;
import com.annapurna.annapurna.Service.UserService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * LOGGER_ERROR_REGISTRATION of type String
     */
    private static final String LOGGER_ERROR_REGISTRATION = "Error in registering the user...{}";

    /**
     * LOGGER_ERROR_SHOPS_REGISTRATION of type String
     */
    private static final String LOGGER_ERROR_SHOPS_REGISTRATION = "Error in Registering shops...{}";

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
     * The jwtService of type JwtService
     */
    @Autowired
    JwtService jwtService;

    /**
     * The mailService of type MailService
     */
    @Autowired
    MailService mailService;

    /**
     * The locationRepository of type LocationRepository
     */
    @Autowired
    LocationRepository locationRepository;

    /**
     * The shopsRepository of type ShopsRepository
     */
    @Autowired
    ShopsRepository shopsRepository;

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
        DataValidatingRequestDTO dataValidatingEmail = DataValidatingRequestDTO.builder()
                .emailId(userRegistrationDTO.getEmailId()).build();
        DataValidatingRequestDTO dataValidatingNumber = DataValidatingRequestDTO.builder()
                .emailId(userRegistrationDTO.getPhoneNumber()).build();
        if(checkExistingData(dataValidatingEmail).getIsExisting() || checkExistingData(dataValidatingNumber).getIsExisting()){
            throw new CustomValidationException(ErrorCode.ERR_AP_2014);
        }
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
            LOGGER.error(LOGGER_ERROR_REGISTRATION,ex.getMessage());
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
    public TokenResponseDTO generateAuthToken(LoginRequestDTO loginRequestDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginRequestDTO.getUserId(),loginRequestDTO.getPassword()));

            return TokenResponseDTO.builder()
                    .accessToken(jwtService.generateToken(loginRequestDTO.getUserId()))
                    .refreshToken(jwtService.generateRefreshToken(loginRequestDTO.getUserId()))
                    .userName(loginRequestDTO.getUserId())
                    .build();
        }catch (Exception ex) {
            if(ex.getMessage().equals(ErrorCode.ERR_AP_2015.getMessage())){
                throw new CustomValidationException(ErrorCode.ERR_AP_2015);
            }else if(ex.getMessage().equals(ErrorCode.ERR_AP_2016.getMessage())){
                throw new CustomValidationException(ErrorCode.ERR_AP_2016);
            }else{
                throw new CustomValidationException(ErrorCode.ERR_AP_2012); // Catching any other exception and throwing a general error
            }
        }
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

    /**
     *
     * @param refreshTokenRequestDTO
     * @return
     */
    @Override
    public TokenResponseDTO generateTokenFromRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        if(Boolean.TRUE.equals(jwtService.validateToken
                (refreshTokenRequestDTO.getRefreshToken(), refreshTokenRequestDTO.getUserName()))){
            return TokenResponseDTO.builder()
                    .accessToken(jwtService.generateToken(refreshTokenRequestDTO.getUserName()))
                    .refreshToken(jwtService.generateRefreshToken(refreshTokenRequestDTO.getUserName()))
                    .userName(refreshTokenRequestDTO.getUserName())
                    .build();
        }else{
            throw new CustomValidationException(ErrorCode.ERR_AP_2006);
        }
    }

    /**
     *
     * @param shopRegistrationRequestDTO
     * @param userCacheDTO
     * @return
     */
    @Override
    public ShopRegistrationResponseDTO shopRegistration(ShopRegistrationRequestDTO shopRegistrationRequestDTO, UserCacheDTO userCacheDTO) {
        ShopRegistrationResponseDTO shopRegistrationResponseDTO = new ShopRegistrationResponseDTO();
        try{
            if(verifyShopRegistration(shopRegistrationRequestDTO,userCacheDTO)){
                shopRegistrationResponseDTO  = ShopRegistrationResponseDTO.builder()
                        .status(AP_Constants.TRUE).message(AP_Constants.SHOP_CREATED_SUCCESSFULLY).build();
            }
            Location location = Location.builder()
                    .lattitude(shopRegistrationRequestDTO.getLattitude())
                    .longitude(shopRegistrationRequestDTO.getLongitude())
                    .desc(AP_Constants.SHOP_CREATION_DESCRIPTION)
                    .address(shopRegistrationRequestDTO.getAddress())
                    .createdBy(userCacheDTO.getUserName())
                    .updatedBy(userCacheDTO.getUserName())
                    .deletedFlag(AP_Constants.FALSE)
                    .createdTs(LocalDateTime.now())
                    .updatedTs(LocalDateTime.now())
                    .build();

            location = locationRepository.save(location);

            Shops shops = Shops.builder()
                    .shopDesc(shopRegistrationRequestDTO.getDescription())
                    .shopMailId(shopRegistrationRequestDTO.getShopMailId())
                    .isMailVerified(AP_Constants.FALSE)
                    .shopName(shopRegistrationRequestDTO.getShopName())
                    .location(location.getId())
                    .pinCode(shopRegistrationRequestDTO.getPinCode())
                    .deletedFlag(AP_Constants.FALSE)
                    .shopPhNumber(shopRegistrationRequestDTO.getShopPhoneNumber())
                    .isphNumberVerified(AP_Constants.FALSE)
                    .shopOwnerId(Integer.parseInt(userCacheDTO.getUserId()))
                    .createdBy(userCacheDTO.getUserName())
                    .updatedBy(userCacheDTO.getUserName())
                    .createdTs(LocalDateTime.now())
                    .updatedTs(LocalDateTime.now())
                    .build();

            shops = shopsRepository.save(shops);
            shopRegistrationResponseDTO.setShopId(shops.getId());

        }catch (Exception ex) {

            LOGGER.error(LOGGER_ERROR_SHOPS_REGISTRATION,ex.getMessage());
            shopRegistrationResponseDTO.setStatus(AP_Constants.FALSE);
            shopRegistrationResponseDTO.setMessage(ex.getMessage());
        }
        return shopRegistrationResponseDTO;
    }

    /**
     *
     * @param shopRegistrationRequestDTO
     * @param userCacheDTO
     * @return
     */
    public Boolean verifyShopRegistration(ShopRegistrationRequestDTO shopRegistrationRequestDTO, UserCacheDTO userCacheDTO){
        if(null==shopRegistrationRequestDTO.getLattitude() ||
                null==shopRegistrationRequestDTO.getLongitude() ||
                null==shopRegistrationRequestDTO.getPinCode() ||
                null==shopRegistrationRequestDTO.getShopName() ||
                null== shopRegistrationRequestDTO.getUserId() ||
                null==shopRegistrationRequestDTO.getShopMailId() ||
                !userCacheDTO.getUserId().equals(shopRegistrationRequestDTO.getUserId())){
            throw new CustomValidationException(ErrorCode.ERR_AP_2006);
        }
        Shops shopEmail = shopsRepository.findByShopMailId(shopRegistrationRequestDTO.getShopMailId());
        if(null!=shopEmail){
            throw new CustomValidationException(ErrorCode.ERR_AP_2020);
        }
        if(null!=shopRegistrationRequestDTO.getShopPhoneNumber()){
            Shops shopNumber = shopsRepository.findByShopPhNumber(shopRegistrationRequestDTO
                    .getShopPhoneNumber());
            if(null!=shopNumber){
                throw new CustomValidationException(ErrorCode.ERR_AP_2022);
            }
        }

        return AP_Constants.TRUE;
    }
}
