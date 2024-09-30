package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.DataValidatingRequestDTO;
import com.annapurna.annapurna.DTO.DataValidatingResponseDTO;
import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.UserRegistrationDTO;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.JwtService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

    @ContextConfiguration(classes = {UserServiceImpl.class})
    @ExtendWith(SpringExtension.class)
    @DisabledInAotMode
    public class UserServiceImplTest {


        @Autowired
        UserServiceImpl userServiceImpl;
    
        @MockBean
        FileRepository fileRepository;
    
        @MockBean
        UserRepository userRepository;
    
        @MockBean
        GeneralFunctions generalFunctions;

        @MockBean
        AuthenticationManager authenticationManager;

        @MockBean
        JwtService jwtService;

        /**
         * Method under test : {@link UserServiceImpl#registerUser(UserRegistrationDTO)}
         */
        @Test
        void testRegisterUser(){

            // success Registration
            GeneralResponseDTO generalResponseDTO = GeneralResponseDTO.builder()
                    .status(true)
                    .message("User created Successfully")
                    .build();

            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
            userRegistrationDTO.setUserName("anshusingh");
            userRegistrationDTO.setName("Anshu Singh");
            userRegistrationDTO.setPassword("12345");
            userRegistrationDTO.setPhoneNumber("9876543210");
            userRegistrationDTO.setEmailId("anshusingh@gmail.com");

            GeneralResponseDTO response = userServiceImpl.registerUser(userRegistrationDTO);

            assertNotNull(response);
            assertEquals(generalResponseDTO.getMessage(),response.getMessage());
            assertEquals(generalResponseDTO.getStatus(),response.getStatus());

            when(userRepository.getUserByEmailIdAndDeletedFlagFalse(Mockito.any(String.class))).thenReturn(User.builder().build());
            when(userRepository.getUserByPhoneNumberAndDeletedFlagFalse(Mockito.any(String.class))).thenReturn(User.builder().build());

            // when user is already registered
            assertThrows(CustomValidationException.class, ()->{
                userServiceImpl.registerUser(userRegistrationDTO);
            });

            // Image file not found
            UserRegistrationDTO customExceptionuserRegistrationDTO = new UserRegistrationDTO();
            customExceptionuserRegistrationDTO.setUserName("anshusingh");
            customExceptionuserRegistrationDTO.setName("Anshu Singh");
            customExceptionuserRegistrationDTO.setPassword("12345");
            customExceptionuserRegistrationDTO.setImageId("c674ec81-0894-4915-t6b9");
            customExceptionuserRegistrationDTO.setPhoneNumber("9876543210");
            customExceptionuserRegistrationDTO.setEmailId("anshu@gmail.com");
            assertThrows(CustomValidationException.class, ()->{
                userServiceImpl.registerUser(customExceptionuserRegistrationDTO);
            });

        }

        /**
         * Method under test: {@link UserServiceImpl#checkExistingData(DataValidatingRequestDTO)}
         */
        @Test
        void testCheckExistingData(){

            // Both Email and phone number not present
            assertThrows(CustomValidationException.class,()->{
                userServiceImpl.checkExistingData(DataValidatingRequestDTO.builder().build());
            });

            // Email not present
            DataValidatingResponseDTO falseEmailValidatingResponseDTO = DataValidatingResponseDTO
                    .builder().
                    uniqueValue("anshusingh@gmail.com")
                    .isExisting(false)
                    .build();

            DataValidatingRequestDTO falseEmailValidatingRequestDTO = DataValidatingRequestDTO
                    .builder().
                    emailId("anshusingh@gmail.com")
                    .build();
            DataValidatingResponseDTO emailResponse = userServiceImpl.checkExistingData(falseEmailValidatingRequestDTO);
            assertNotNull(emailResponse);
            assertEquals(falseEmailValidatingResponseDTO.getUniqueValue(),emailResponse.getUniqueValue());
            assertEquals(falseEmailValidatingResponseDTO.getIsExisting(),emailResponse.getIsExisting());

            // phone number not present
            DataValidatingResponseDTO falseNumberValidatingResponseDTO = DataValidatingResponseDTO
                    .builder().
                    uniqueValue("9876543210")
                    .isExisting(false)
                    .build();

            DataValidatingRequestDTO falseNumberValidatingRequestDTO = DataValidatingRequestDTO
                    .builder().
                    phoneNumber("9876543210")
                    .build();
            DataValidatingResponseDTO numberResponse = userServiceImpl.checkExistingData(falseNumberValidatingRequestDTO);
            assertNotNull(numberResponse);
            assertEquals(falseNumberValidatingResponseDTO.getUniqueValue(),numberResponse.getUniqueValue());
            assertEquals(falseNumberValidatingResponseDTO.getIsExisting(),numberResponse.getIsExisting());

            when(userRepository.getUserByEmailIdAndDeletedFlagFalse(Mockito.any(String.class))).thenReturn(User.builder().build());
            when(userRepository.getUserByPhoneNumberAndDeletedFlagFalse(Mockito.any(String.class))).thenReturn(User.builder().build());

            // Email is present
            DataValidatingResponseDTO trueEmailValidatingResponseDTO = DataValidatingResponseDTO
                    .builder().
                    uniqueValue("anshusingh@gmail.com")
                    .isExisting(true)
                    .build();

            DataValidatingRequestDTO trueEmailValidatingRequestDTO = DataValidatingRequestDTO
                    .builder().
                    emailId("anshusingh@gmail.com")
                    .build();
            DataValidatingResponseDTO trueEmailResponse = userServiceImpl.checkExistingData(trueEmailValidatingRequestDTO);
            assertNotNull(trueEmailResponse);
            assertEquals(trueEmailValidatingResponseDTO.getUniqueValue(),trueEmailResponse.getUniqueValue());
            assertEquals(trueEmailValidatingResponseDTO.getIsExisting(),trueEmailResponse.getIsExisting());

            // phone number is present
            DataValidatingResponseDTO trueNumberValidatingResponseDTO = DataValidatingResponseDTO
                    .builder().
                    uniqueValue("9876543210")
                    .isExisting(true)
                    .build();

            DataValidatingRequestDTO trueNumberValidatingRequestDTO = DataValidatingRequestDTO
                    .builder().
                    phoneNumber("9876543210")
                    .build();
            DataValidatingResponseDTO trueNumberResponse = userServiceImpl.checkExistingData(trueNumberValidatingRequestDTO);
            assertNotNull(trueNumberResponse);
            assertEquals(trueNumberValidatingResponseDTO.getUniqueValue(),trueNumberResponse.getUniqueValue());
            assertEquals(trueNumberValidatingResponseDTO.getIsExisting(),trueNumberResponse.getIsExisting());
        }
}
