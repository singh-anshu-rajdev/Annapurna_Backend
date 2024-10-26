package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.*;
import org.springframework.stereotype.Service;

@Service
public interface MasterService {

    /**
     *
     * @param userCacheDTO
     * @return
     */
    public MasterDataResponseDTO getMasterData(UserCacheDTO userCacheDTO);

    /**
     *
     * @param featureDataRequestDTO
     * @param userCacheDTO
     * @return
     */
    GeneralResponseDTO createFeature(FeatureDataRequestDTO featureDataRequestDTO, UserCacheDTO userCacheDTO);

    /**
     *
     * @param nearestShopRequestDTO
     * @param userCache
     * @return
     */
    NearestShopResponseDTO getNearestShops(NearestShopRequestDTO nearestShopRequestDTO, UserCacheDTO userCache);
}
