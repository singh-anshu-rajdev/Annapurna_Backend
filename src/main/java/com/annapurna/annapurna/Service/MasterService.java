package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.FeatureDataRequestDTO;
import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.MasterDataResponseDTO;
import com.annapurna.annapurna.DTO.UserCacheDTO;
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
}
