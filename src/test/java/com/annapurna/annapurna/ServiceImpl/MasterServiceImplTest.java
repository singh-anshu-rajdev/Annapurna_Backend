package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Model.Features;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FeaturesRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ContextConfiguration(classes = {MasterServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class MasterServiceImplTest {

    @Autowired
    MasterServiceImpl masterServiceImpl;

    @MockBean
    UserRepository userRepository;

    @MockBean
    FeaturesRepository featuresRepository;

    @MockBean
    GeneralFunctions generalFunctions;

    /**
     * Method under Test {@link MasterServiceImpl#getMasterData(UserCacheDTO)}
     */
    @Test
    void testGetMasterData(){

        User user = User.builder()
                .userName("anshuSingh").id(1L).name("Anshu Singh").imageUniqueId("c674ec81-0894-4915-a8b9")
                .emailId("anshusingh@gmail.com").phoneNumber("9876543210").build();

        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("123").userName("anshuSingh").name("Anshu").clientId(1).roleId(1L).build();

        List<Features> features = List.of(Features.builder()
                .isEnabled(true).code("tt").name("test").id(1).build());

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .name("Anshu Singh")
                .emailId("anshusingh@gmail.com")
                .phoneNumber("9876543210")
                .image("c674ec81-0894-4915-a8b9")
                .userName("anshuSingh")
                .build();
        List<FeatureResponseDTO> featureResponseDTOList = List.of(FeatureResponseDTO.builder()
                .featureName("test")
                .featureCode("tt")
                .build());

        MasterDataResponseDTO masterDataResponseDTO = MasterDataResponseDTO.builder()
                .featureResponseDTOList(featureResponseDTOList).userResponseDTO(userResponseDTO).build();

        MasterDataResponseDTO testMasterDataResponseDTO = MasterDataResponseDTO.builder()
                .featureResponseDTOList(featureResponseDTOList).userResponseDTO(null).build();

        when(userRepository.getUserById(Mockito.any(Long.class))).thenReturn(user);
        when(featuresRepository.findAllFeatures()).thenReturn(features);
        when(featuresRepository.findByIsLoginFalse(Mockito.any(Boolean.class))).thenReturn(features);

        MasterDataResponseDTO responseDTO = masterServiceImpl.getMasterData(userCacheDTO);
        assertNotNull(responseDTO);
        assertEquals("Getting Master Data",masterDataResponseDTO,responseDTO);

        MasterDataResponseDTO response = masterServiceImpl.getMasterData(null);
        assertNotNull(response);
        assertEquals("Getting Master Data",testMasterDataResponseDTO,response);
    }

    /**
     * Method under Test {@link MasterServiceImpl#createFeature(FeatureDataRequestDTO, UserCacheDTO)}
     */
    @Test
    void testCreateFeature(){
        List<FeatureRequestDTO> featureRequestDTO = List.of(FeatureRequestDTO.builder()
                .featureName("test").isLogin(true).build());
        FeatureDataRequestDTO featureDataRequestDTO = FeatureDataRequestDTO.builder()
                .featureRequestDTOList(featureRequestDTO).build();

        UserCacheDTO userCacheDTO = UserCacheDTO.builder()
                .userId("123").userName("anshuSingh").name("Anshu").clientId(1).roleId(1L).build();

        GeneralResponseDTO generalResponseDTO = GeneralResponseDTO.builder()
                .status(true).message("Feature created Successfully").build();

        when(featuresRepository.saveAll(Mockito.any(List.class))).thenReturn(null);
        GeneralResponseDTO responseDTO = masterServiceImpl.createFeature(featureDataRequestDTO,userCacheDTO);
        assertNotNull(responseDTO);
        assertEquals("Feature created Successfully",generalResponseDTO,responseDTO);

    }
}
