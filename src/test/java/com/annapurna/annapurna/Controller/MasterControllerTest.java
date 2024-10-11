package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.MasterService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {MasterController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class MasterControllerTest {

    @Autowired
    private MasterController masterController;

    @MockBean
    private MasterService masterService;

    @MockBean
    private GeneralFunctions generalFunctions;

    /**
     * Method Under Test {@link MasterController#getMasterData(HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    void testGetMasterData() throws Exception{
        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .name("Anshu")
                .emailId("anshusingh@gmail.com")
                .phoneNumber("9876543210")
                .image("c674ec81-0894-4915-a8b9")
                .userName("anshuSingh")
                .build();
        List<FeatureResponseDTO> featureResponseDTOList = List.of(FeatureResponseDTO.builder()
                        .featureName("test")
                        .featureCode("TTT")
                .build());
        MasterDataResponseDTO masterDataResponseDTO = MasterDataResponseDTO.builder()
                .userResponseDTO(userResponseDTO)
                .featureResponseDTOList(featureResponseDTOList)
                .build();

        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("123").userName("anshuSingh").name("Anshu").clientId(1).roleId(1L).build();

        when(masterService.getMasterData(Mockito.any(UserCacheDTO.class))).thenReturn(masterDataResponseDTO);
        when(generalFunctions.getUserCache(Mockito.any(HttpServletRequest.class))).thenReturn(userCacheDTO);
        String content = (new ObjectMapper()).writeValueAsString(userCacheDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/getMasterData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(masterController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(masterDataResponseDTO)));
    }

    /**
     * Method under Test {@link MasterController#createFeature(FeatureDataRequestDTO, HttpServletRequest)}
     *
     * @throws Exception
     */
    @Test
    void testCreateFeature() throws Exception{
        List<FeatureRequestDTO> featureRequestDTO = List.of(FeatureRequestDTO.builder().featureName("test").isLogin(true).build());
        FeatureDataRequestDTO featureDataRequestDTO = FeatureDataRequestDTO.builder()
                .featureRequestDTOList(featureRequestDTO).build();
        GeneralResponseDTO generalResponseDTO = GeneralResponseDTO.builder().message("success").status(true).build();
        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("123").userName("anshuSingh").name("Anshu").clientId(1).roleId(1L).build();

        when(masterService.createFeature(Mockito.any(FeatureDataRequestDTO.class),Mockito.any(UserCacheDTO.class))).thenReturn(generalResponseDTO);
        when(generalFunctions.getUserCache(Mockito.any(HttpServletRequest.class))).thenReturn(userCacheDTO);

        String content = (new ObjectMapper()).writeValueAsString(featureDataRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/createFeature")
                .content(content).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(masterController).build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new Gson().toJson(generalResponseDTO)));
    }
}
