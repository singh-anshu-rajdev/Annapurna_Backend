package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Model.Features;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FeaturesRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.MasterService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    /**
     * The userRepository of type UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The featuresRepository of type FeaturesRepository
     */
    @Autowired
    private FeaturesRepository featuresRepository;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    private GeneralFunctions generalFunctions;

    /**
     *
     * @param userCacheDTO
     * @return
     */
    public MasterDataResponseDTO getMasterData(UserCacheDTO userCacheDTO){

        // User Response Data setup
        UserResponseDTO userResponseDTO = null;
        if(null!=userCacheDTO){
            User user = userRepository.getUserById(Long.valueOf(userCacheDTO.getUserId()));
            userResponseDTO = UserResponseDTO.builder()
                    .userName(user.getUserName())
                    .name(user.getName())
                    .image(user.getImageUniqueId())
                    .phoneNumber(user.getPhoneNumber())
                    .emailId(user.getEmailId())
                    .build();
        }

        // Feature Data Setup
        List<Features> features = featuresRepository.findAllFeatures();
        if(null!=userCacheDTO){
            features = features.parallelStream()
                    .filter(feature -> null == feature.getIsLogin() ||
                            feature.getIsLogin().equals(AP_Constants.TRUE)
                            ).toList();
        }else{
            features = features.parallelStream()
                    .filter(feature -> null == feature.getIsLogin() ||
                            feature.getIsLogin().equals(AP_Constants.FALSE)
                    ).toList();;
        }
        List<FeatureResponseDTO> featureResponseDTOList = features.parallelStream().map( f-> {
            return FeatureResponseDTO.builder()
                    .featureName(f.getName())
                    .featureCode(f.getCode())
                    .build();
        }).toList();

        // return the response
        return MasterDataResponseDTO.builder()
                .userResponseDTO(userResponseDTO)
                .featureResponseDTOList(featureResponseDTOList)
                .build();
    }

    /**
     *
     * @param featureDataRequestDTO
     * @param userCacheDTO
     * @return
     */
    @Override
    public GeneralResponseDTO createFeature(FeatureDataRequestDTO featureDataRequestDTO, UserCacheDTO userCacheDTO){
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
        generalResponseDTO.setStatus(AP_Constants.TRUE);
        generalResponseDTO.setMessage(AP_Constants.FEATURE_CREATED_SUCCESSFULLY);
        try{
            List<Features> featuresList = featureDataRequestDTO.getFeatureRequestDTOList().parallelStream()
                    .map(fl -> {
                        return Features.builder()
                                .name(fl.getFeatureName())
                                .code(generalFunctions.generateCode())
                                .isLogin(fl.getIsLogin())
                                .isEnabled(AP_Constants.TRUE)
                                .deletedFlag(AP_Constants.FALSE)
                                .createdTs(LocalDateTime.now())
                                .updatedTs(LocalDateTime.now())
                                .createdBy(userCacheDTO.getUserName())
                                .updatedBy(userCacheDTO.getUserName())
                                .build();
                    }).toList();
            featuresRepository.saveAll(featuresList);
        } catch (Exception ex) {
            generalResponseDTO.setStatus(AP_Constants.FALSE);
            generalResponseDTO.setMessage(ex.getMessage());
        }
        return generalResponseDTO;
    }
}
