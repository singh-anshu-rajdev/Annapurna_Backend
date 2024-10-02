package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test : {@link UserController#generateAuthToken(LoginRequestDTO)}
     *
     * @throws Exception
     */
    @Test
    void testGenerateAuthToken() throws Exception {

        // Arrange
        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .userId("test@gmail.com")
                .password("12345")
                .build();
        TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                .accessToken("mocked-token")
                .refreshToken("mocked-refresh-token")
                .userName("anshusingh@gmail.com").build();
        when(userService.generateAuthToken(Mockito.<LoginRequestDTO>any())).thenReturn(tokenResponseDTO);

        String content = (new ObjectMapper()).writeValueAsString(loginRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/unsecure/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(new Gson().toJson(tokenResponseDTO)));
    }

    /**
     * Method under test : {@link UserController#generateTokenFromRefreshToken(RefreshTokenRequestDTO)}
     * @throws Exception
     */
    @Test
    void testGenerateTokenFromRefreshToken() throws Exception {
        TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                .accessToken("mock-token")
                .refreshToken("refresh-mock-token")
                .userName("anshusingh@gmail.com").build();

        when(userService.generateTokenFromRefreshToken(Mockito.<RefreshTokenRequestDTO>any())).thenReturn(tokenResponseDTO);

        RefreshTokenRequestDTO refreshTokenRequestDTO = RefreshTokenRequestDTO.builder()
                .refreshToken("refresh-mock-token")
                .userName("anshusingh@gmail.com").build();
        String request = (new ObjectMapper()).writeValueAsString(refreshTokenRequestDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/unsecure/generateTokenFromRefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(new Gson().toJson(tokenResponseDTO)));
    }
    /**
     * Method under test : {@link UserController#userRegistration(UserRegistrationDTO)}
     *
     * @throws Exception
     */
    @Test
    void testUserRegistration() throws Exception {

        // Arrange
        GeneralResponseDTO generalResponseDTO = GeneralResponseDTO.builder()
                .status(true)
                .message("success")
                .build();
        when(userService.registerUser(Mockito.any(UserRegistrationDTO.class))).thenReturn(generalResponseDTO);
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUserName("anshusingh");
        userRegistrationDTO.setName("Anshu Singh");
        userRegistrationDTO.setPassword("12345");
        userRegistrationDTO.setImageId("c674ec81-0894-4915-a8b9");
        userRegistrationDTO.setPhoneNumber("9876543210");
        userRegistrationDTO.setEmailId("anshu@gmail.com");
        String content = (new ObjectMapper()).writeValueAsString(userRegistrationDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/unsecure/userRegistration")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(generalResponseDTO)));
    }

    /**
     * Method under test : {@link UserController#checkExistingData(DataValidatingRequestDTO)}
     * @throws Exception
     */
    @Test
    void testCheckExistingData() throws Exception {

        // Arrange
        DataValidatingResponseDTO responseDTO = DataValidatingResponseDTO.builder()
                .isExisting(true)
                .uniqueValue("abcdef")
                .build();

        when(userService.checkExistingData(Mockito.any(DataValidatingRequestDTO.class))).thenReturn(responseDTO);

        DataValidatingRequestDTO requestDTO = DataValidatingRequestDTO.builder()
                .emailId("anshu@gmail.com")
                .phoneNumber("9876543210")
                .build();

        String content = (new ObjectMapper()).writeValueAsString(requestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api//unsecure/checkExistingData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(responseDTO)));

    }

}
