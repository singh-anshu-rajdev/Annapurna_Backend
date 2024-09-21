package com.annapurna.annapurna.Controller;

import static org.mockito.Mockito.when;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.VerificationDTO;
import com.annapurna.annapurna.Service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MailController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MailControllerTest {
    @Autowired
    private MailController mailController;

    @MockBean
    private MailService mailService;

    /**
     * Method under test:
     * {@link MailController#sendOtpForVerification(VerificationDTO)}
     */
    @Test
    void testSendOtpForVerification() throws Exception {
        // Arrange
        GeneralResponseDTO buildResult = GeneralResponseDTO.builder()
                .message("Not all who wander are lost")
                .status(true)
                .build();
        when(mailService.sendOtpForVerification(Mockito.<VerificationDTO>any())).thenReturn(buildResult);

        VerificationDTO verificationDTO = new VerificationDTO();
        verificationDTO.setMail("Mail");
        verificationDTO.setOtp(1);
        verificationDTO.setPhoneNumber("6625550144");
        String content = (new ObjectMapper()).writeValueAsString(verificationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/unsecure/sendOtp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(mailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":true,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link MailController#verifyOtp(VerificationDTO)}
     */
    @Test
    void testVerifyOtp() throws Exception {
        // Arrange
        GeneralResponseDTO buildResult = GeneralResponseDTO.builder()
                .message("Not all who wander are lost")
                .status(true)
                .build();
        when(mailService.verifyOtp(Mockito.<VerificationDTO>any())).thenReturn(buildResult);

        VerificationDTO verificationDTO = new VerificationDTO();
        verificationDTO.setMail("Mail");
        verificationDTO.setOtp(1);
        verificationDTO.setPhoneNumber("6625550144");
        String content = (new ObjectMapper()).writeValueAsString(verificationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/unsecure/verifyOtp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(mailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":true,\"message\":\"Not all who wander are lost\"}"));
    }
}
