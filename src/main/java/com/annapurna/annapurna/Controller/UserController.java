package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.UserService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * The userService of type UserService
     */
    @Autowired
    UserService userService;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    /**
     *
     * @param userRegistrationDTO
     * @return
     */
    @PostMapping("/unsecure/userRegistration")
    public ResponseEntity<GeneralResponseDTO> userRegistration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.OK);
    }

    /**
     *
     * @param loginRequestDTO
     * @return
     */
    @PostMapping("/unsecure/login")
    public ResponseEntity<TokenResponseDTO> generateAuthToken(@RequestBody LoginRequestDTO loginRequestDTO){
        return new ResponseEntity<>(userService.generateAuthToken(loginRequestDTO),HttpStatus.OK);
    }

    /**
     *
     * @param refreshTokenRequestDTO
     * @return
     */
    @PostMapping("/unsecure/generateTokenFromRefreshToken")
    public ResponseEntity<TokenResponseDTO> generateTokenFromRefreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return new ResponseEntity<>(userService.generateTokenFromRefreshToken(refreshTokenRequestDTO),HttpStatus.OK);
    }

    /**
     *
     * @param dataValidatingRequestDTO
     * @return
     */
    @PostMapping("/unsecure/checkExistingData")
    public ResponseEntity<DataValidatingResponseDTO> checkExistingData(@RequestBody DataValidatingRequestDTO dataValidatingRequestDTO){
        return new ResponseEntity<>(userService.checkExistingData(dataValidatingRequestDTO),HttpStatus.OK);
    }

    /**
     *
     * @param shopRegistrationRequestDTO
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/shopRegistration")
    public ResponseEntity<ShopRegistrationResponseDTO> shopRegistration(@RequestBody ShopRegistrationRequestDTO shopRegistrationRequestDTO,
                                                               HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(userService.shopRegistration(shopRegistrationRequestDTO
                ,generalFunctions.getUserCache(httpServletRequest)),HttpStatus.OK);
    }
}
