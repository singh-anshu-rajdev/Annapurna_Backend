package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.MasterService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MasterController {

    /**
     * The masterService of type MasterService
     */
    @Autowired
    private MasterService masterService;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    private GeneralFunctions generalFunctions;

    /**
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping({"/getMasterData","/unsecure/getMasterData"})
    public ResponseEntity<MasterDataResponseDTO> getMasterData(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(masterService
                .getMasterData(generalFunctions.getUserCache(httpServletRequest)), HttpStatus.OK);
    }

    /**
     *
     * @param featureDataRequestDTO
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/createFeature")
    public ResponseEntity<GeneralResponseDTO> createFeature(@RequestBody FeatureDataRequestDTO featureDataRequestDTO
            , HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(masterService.createFeature(featureDataRequestDTO
                ,generalFunctions.getUserCache(httpServletRequest)),HttpStatus.OK);
    }

    /**
     *
     * @param nearestShopRequestDTO
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getNearestShops")
    public ResponseEntity<NearestShopResponseDTO> getNearestShops(@RequestBody NearestShopRequestDTO nearestShopRequestDTO, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(masterService.getNearestShops(nearestShopRequestDTO,generalFunctions.getUserCache(httpServletRequest)), HttpStatus.OK);
    }
}
