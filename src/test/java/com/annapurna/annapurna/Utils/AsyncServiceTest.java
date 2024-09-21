package com.annapurna.annapurna.Utils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.OtpVerificationRepository;
import com.annapurna.annapurna.Repository.UserRepository;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AsyncService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AsyncServiceTest {
    @Autowired
    private AsyncService asyncService;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private OtpVerificationRepository otpVerificationRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AsyncService#saveOtpVerification(String, Integer)}
     */
    @Test
    void testSaveOtpVerification() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        asyncService.saveOtpVerification("42", 1);
    }

    /**
     * Method under test: {@link AsyncService#saveOtpVerification(String, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveOtpVerification2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        when(otpVerificationRepository.findByUniqueType(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        asyncService.saveOtpVerification(AP_Constants.DEFAULT_USER, 1);

        // Assert
        verify(otpVerificationRepository).findByUniqueType(eq("user"));
    }

    /**
     * Method under test: {@link AsyncService#sendWelcomeMail(String)}
     */
    @Test
    void testSendWelcomeMail() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        asyncService.sendWelcomeMail("User Type");
    }

    /**
     * Method under test: {@link AsyncService#sendWelcomeMail(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSendWelcomeMail2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        User user = new User();
        user.setClientId(1);
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeletedFlag(true);
        user.setEmailId("42");
        user.setId(1L);
        user.setImageId(1L);
        user.setIsEmailVerified(true);
        user.setIsPhoneVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRoles("Roles");
        user.setUpdatedBy("2020-03-01");
        user.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setUserName("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setClientId(1);
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setDeletedFlag(true);
        user2.setEmailId("42");
        user2.setId(1L);
        user2.setImageId(1L);
        user2.setIsEmailVerified(true);
        user2.setIsPhoneVerified(true);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRoles("Roles");
        user2.setUpdatedBy("2020-03-01");
        user2.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setUserName("janedoe");
        when(userRepository.getUserByEmailIdANDDeletedFlagFalse(Mockito.<String>any())).thenReturn(user2);

        // Act
        asyncService.sendWelcomeMail("com.annapurna.annapurna.Utils.AsyncServiceUser Type");

        // Assert
        verify(userRepository)
                .getUserByEmailIdANDDeletedFlagFalse(eq("com.annapurna.annapurna.Utils.AsyncServiceUser Type"));
        verify(userRepository).save(isA(User.class));
    }
}
