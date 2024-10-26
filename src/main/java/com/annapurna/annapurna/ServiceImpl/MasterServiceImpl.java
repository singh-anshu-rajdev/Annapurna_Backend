package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.Features;
import com.annapurna.annapurna.Model.Location;
import com.annapurna.annapurna.Model.Shops;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.FeaturesRepository;
import com.annapurna.annapurna.Repository.LocationRepository;
import com.annapurna.annapurna.Repository.ShopsRepository;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.MasterService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.AsyncService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import java.util.stream.Collectors;

@Service
public class MasterServiceImpl implements MasterService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    private final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);

    /**
     * The ERROR_FETCHING_NEAREST_PLACE of type String
     */
    private final String ERROR_FETCHING_NEAREST_PLACE  = "Error in fetching nearest shops...{}";

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
     * The shopsRepository of type ShopsRepository
     */
    @Autowired
    private ShopsRepository shopsRepository;

    /**
     * The locationRepository of type LocationRepository
     */
    @Autowired
    private LocationRepository locationRepository;

    /**
     * The asyncService of type AsyncService
     */
    @Autowired
    private AsyncService asyncService;

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

    /**
     *
     * @param nearestShopRequestDTO
     * @param userCache
     * @return
     */
    @Override
    public NearestShopResponseDTO getNearestShops(NearestShopRequestDTO nearestShopRequestDTO, UserCacheDTO userCache) {
        if(userCache.getUserId().equals(nearestShopRequestDTO.getUserId().toString())){
            throw new CustomValidationException(ErrorCode.ERR_AP_2006);
        }else{
            try{
                asyncService.saveRequestBody((new ObjectMapper()).writeValueAsString(nearestShopRequestDTO),userCache);
                NearestShopResponseDTO nearestShopResponseDTO = new NearestShopResponseDTO();
                List<ShopsResponseDTO> shopsResponseDTOList = new ArrayList<>();

                // Fetch th pinCode
                String pincode = generalFunctions.getPinCode(nearestShopRequestDTO.getLattitude(),nearestShopRequestDTO.getLongitude());
                Pageable pageable = PageRequest.of(nearestShopRequestDTO.getPageNumber(),nearestShopRequestDTO.getNumberOfRecords());
                String pinCodeStart = Integer.toString(Integer.parseInt(pincode)-2);
                String pinCodeEnd = Integer.toString(Integer.parseInt(pincode)+2);

                // Fetch shop Details
                List<Shops> shopsList = shopsRepository.findByPincodeAndDeletedFlag(pinCodeStart,pinCodeEnd,pageable);
                Integer totalRecords = shopsRepository.countByPincodeAndDeletedFlag(pinCodeStart,pinCodeEnd);
                if(null!=shopsList && !shopsList.isEmpty()){
                    List<Integer> locationIds = shopsList.parallelStream().map(Shops::getLocation).toList();

                    // Get the Location Details
                    List<Location> locationList = locationRepository.findByLocationIds(locationIds);
                    Map<Integer,Shops> shopLocationMap = shopsList.parallelStream().collect(Collectors.toMap(Shops::getLocation, Function.identity(),(existing,replace)->existing));
                    List<ShopsResponseDTO> finalShopsResponseDTOList = shopsResponseDTOList;

                    // Get the location Distance and set DTO
                    locationList.parallelStream().forEach(location -> {
                        Double distance = generalFunctions.haversine(nearestShopRequestDTO.getLattitude(),
                                nearestShopRequestDTO.getLongitude(),location.getLattitude(),location.getLongitude());
                        Shops shop = shopLocationMap.get(location.getId());
                        ShopsResponseDTO shopsResponseDTO = ShopsResponseDTO.builder()
                                .shopName(shop.getShopName())
                                .shopPhNumber(shop.getShopPhNumber())
                                .shopRating(shop.getShopRating())
                                .shopDist(distance)
                                .shopMailId(shop.getShopMailId())
                                .shopDesc(shop.getShopDesc())
                                .build();
                        finalShopsResponseDTOList.add(shopsResponseDTO);
                    });

                    // sort the response based on distance
                    shopsResponseDTOList = shopsResponseDTOList.stream().sorted(Comparator
                            .comparingDouble(ShopsResponseDTO::getShopDist)).toList();

                    // Set the response
                    nearestShopResponseDTO.setShopsResponseDTOList(shopsResponseDTOList);
                    nearestShopResponseDTO.setTotalNumberOfRecords(totalRecords);
                    nearestShopResponseDTO.setCurrentPageNumber(nearestShopRequestDTO.getPageNumber());
                    return nearestShopResponseDTO;
                }else{
                    throw new CustomValidationException(ErrorCode.ERR_AP_2013);
                }
            } catch (Exception ex) {
                logger.error(ERROR_FETCHING_NEAREST_PLACE,ex.getMessage());
                throw new CustomValidationException(ErrorCode.ERR_AP_2000);
            }

        }
    }
}
