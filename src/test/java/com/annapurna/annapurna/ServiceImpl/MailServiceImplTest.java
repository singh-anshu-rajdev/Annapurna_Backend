package com.annapurna.annapurna.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.VerificationDTO;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.OtpVerificationRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Utils.AsyncService;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MailServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MailServiceImplTest {
    @MockBean
    private AsyncService asyncService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @MockBean
    private OtpVerificationRepository otpVerificationRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test:
     * {@link MailServiceImpl#sendOtpForVerification(VerificationDTO)}
     */
    @Test
    void testSendOtpForVerification() throws MailException {
        // Arrange
        doNothing().when(asyncService).saveOtpVerification(Mockito.<String>any(), Mockito.<Integer>any());
        doNothing().when(javaMailSender).send(Mockito.<SimpleMailMessage>any());

        // Act
        GeneralResponseDTO actualSendOtpForVerificationResult = mailServiceImpl
                .sendOtpForVerification(new VerificationDTO());

        // Assert
        verify(asyncService).saveOtpVerification(isNull(), eq(448094));
        verify(javaMailSender).send(isA(SimpleMailMessage.class));
        assertEquals("Mail sent Successfully", actualSendOtpForVerificationResult.getMessage());
        assertTrue(actualSendOtpForVerificationResult.getStatus());
    }

    /**
     * Method under test:
     * {@link MailServiceImpl#sendOtpForVerification(VerificationDTO)}
     */
    @Test
    void testSendOtpForVerification2() throws MailException {
        // Arrange
        doNothing().when(asyncService).saveOtpVerification(Mockito.<String>any(), Mockito.<Integer>any());
        doThrow(new CustomValidationException(ErrorCode.ERR_AP_2000)).when(javaMailSender)
                .send(Mockito.<SimpleMailMessage>any());

        // Act
        GeneralResponseDTO actualSendOtpForVerificationResult = mailServiceImpl
                .sendOtpForVerification(new VerificationDTO());

        // Assert
        verify(asyncService).saveOtpVerification(isNull(), eq(500765));
        verify(javaMailSender).send(isA(SimpleMailMessage.class));
        assertEquals("Failed to sent the Mail", actualSendOtpForVerificationResult.getMessage());
        assertFalse(actualSendOtpForVerificationResult.getStatus());
    }

    /**
     * Method under test:
     * {@link MailServiceImpl#sendOtpForVerification(VerificationDTO)}
     */
    @Test
    void testSendOtpForVerification3() {
        // Arrange and Act
        GeneralResponseDTO actualSendOtpForVerificationResult = mailServiceImpl.sendOtpForVerification(null);

        // Assert
        assertEquals("Failed to sent the Mail", actualSendOtpForVerificationResult.getMessage());
        assertFalse(actualSendOtpForVerificationResult.getStatus());
    }

    /**
     * Method under test: {@link MailServiceImpl#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp() {
        // Arrange, Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.verifyOtp(new VerificationDTO()));
    }

    /**
     * Method under test: {@link MailServiceImpl#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp2() {
        // Arrange
        User user = new User();
        user.setClientId(1);
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeletedFlag(true);
        user.setEmailId("42");
        user.setId(1L);
        user.setImageUniqueId("abc");
        user.setIsEmailVerified(true);
        user.setIsPhoneVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRoles("Roles");
        user.setUpdatedBy("2020-03-01");
        user.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setUserName("janedoe");
        when(userRepository.getUserByEmailIdDeletedFlagFalse(Mockito.<String>any())).thenReturn(user);
        VerificationDTO verificationDTO = VerificationDTO.builder().mail("Mail").otp(1).phoneNumber("6625550144").build();

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.verifyOtp(verificationDTO));
        verify(userRepository).getUserByEmailIdDeletedFlagFalse(eq("Mail"));
    }

    /**
     * Method under test: {@link MailServiceImpl#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp3() {
        // Arrange
        when(userRepository.getUserByEmailIdDeletedFlagFalse(Mockito.<String>any()))
                .thenThrow(new CustomValidationException(ErrorCode.ERR_AP_2000));
        VerificationDTO verificationDTO = VerificationDTO.builder().mail("Mail").otp(1).phoneNumber("6625550144").build();

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.verifyOtp(verificationDTO));
        verify(userRepository).getUserByEmailIdDeletedFlagFalse(eq("Mail"));
    }

    /**
     * Method under test: {@link MailServiceImpl#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp4() {
        // Arrange
        User user = new User();
        user.setClientId(1);
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeletedFlag(true);
        user.setEmailId("42");
        user.setId(1L);
        user.setImageUniqueId("abc");
        user.setIsEmailVerified(true);
        user.setIsPhoneVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRoles("Roles");
        user.setUpdatedBy("2020-03-01");
        user.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setUserName("janedoe");
        when(userRepository.getUserByPhoneNumberDeletedFlagFalse(Mockito.<String>any())).thenReturn(user);
        VerificationDTO.VerificationDTOBuilder verificationDTOBuilder = mock(VerificationDTO.VerificationDTOBuilder.class);
        when(verificationDTOBuilder.mail(Mockito.<String>any())).thenReturn(VerificationDTO.builder());
        VerificationDTO verificationDTO = verificationDTOBuilder.mail("Mail").otp(1).phoneNumber("6625550144").build();

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.verifyOtp(verificationDTO));
        verify(verificationDTOBuilder).mail(eq("Mail"));
        verify(userRepository).getUserByPhoneNumberDeletedFlagFalse(eq("6625550144"));
    }

    /**
     * Method under test: {@link MailServiceImpl#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp5() {
        // Arrange
        when(userRepository.getUserByPhoneNumberDeletedFlagFalse(Mockito.<String>any()))
                .thenThrow(new CustomValidationException(ErrorCode.ERR_AP_2000));
        VerificationDTO.VerificationDTOBuilder verificationDTOBuilder = mock(VerificationDTO.VerificationDTOBuilder.class);
        when(verificationDTOBuilder.mail(Mockito.<String>any())).thenReturn(VerificationDTO.builder());
        VerificationDTO verificationDTO = verificationDTOBuilder.mail("Mail").otp(1).phoneNumber("6625550144").build();

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.verifyOtp(verificationDTO));
        verify(verificationDTOBuilder).mail(eq("Mail"));
        verify(userRepository).getUserByPhoneNumberDeletedFlagFalse(eq("6625550144"));
    }

    /**
     * Method under test: {@link MailServiceImpl#validationOtp(VerificationDTO)}
     */
    @Test
    void testValidationOtp() {
        // Arrange
        User user = new User();
        user.setClientId(1);
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeletedFlag(true);
        user.setEmailId("42");
        user.setId(1L);
        user.setImageUniqueId("abc");
        user.setIsEmailVerified(true);
        user.setIsPhoneVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRoles("Roles");
        user.setUpdatedBy("2020-03-01");
        user.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setUserName("janedoe");
        when(userRepository.getUserByPhoneNumberDeletedFlagFalse(Mockito.<String>any())).thenReturn(user);

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.validationOtp(new VerificationDTO()));
        verify(userRepository).getUserByPhoneNumberDeletedFlagFalse(isNull());
    }

    /**
     * Method under test: {@link MailServiceImpl#validationOtp(VerificationDTO)}
     */
    @Test
    void testValidationOtp2() {
        // Arrange
        when(userRepository.getUserByPhoneNumberDeletedFlagFalse(Mockito.<String>any()))
                .thenThrow(new CustomValidationException(ErrorCode.ERR_AP_2000));

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> mailServiceImpl.validationOtp(new VerificationDTO()));
        verify(userRepository).getUserByPhoneNumberDeletedFlagFalse(isNull());
    }
}
