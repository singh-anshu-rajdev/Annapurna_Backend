package com.annapurna.annapurna.Service;

import com.annapurna.annapurna.DTO.*;
import org.springframework.stereotype.Service;

@Service
public interface TestimonialService {

    /**
     *
     * @param testimoniesRequestDTO
     * @return
     */
    TestimoniesResponseDTO getAllTestimony(TestimoniesRequestDTO testimoniesRequestDTO);

    /**
     *
     * @param testimonyRequestDTO
     * @param userCacheDTO
     * @return
     */
    TestimonyResponseDTO saveOrEditTestimony(TestimonyRequestDTO testimonyRequestDTO, UserCacheDTO userCacheDTO);
}
