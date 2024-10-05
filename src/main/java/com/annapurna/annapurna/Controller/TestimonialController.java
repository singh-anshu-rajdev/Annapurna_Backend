package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.*;
import com.annapurna.annapurna.Service.TestimonialService;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestimonialController {

    /**
     * The testimonialService of type TestimonialService
     */
    @Autowired
    TestimonialService testimonialService;

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    /**
     *
     * @param testimoniesRequestDTO
     * @return
     */
    @PostMapping("/unsecure/getAllTestimony")
    public ResponseEntity<TestimoniesResponseDTO> getAllTestimony(@RequestBody TestimoniesRequestDTO testimoniesRequestDTO){
        return new ResponseEntity<>(testimonialService.getAllTestimony(testimoniesRequestDTO), HttpStatus.OK);
    }

    /**
     *
     * @param testimonyRequestDTO
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/saveOrEditTestimony")
    public ResponseEntity<TestimonyResponseDTO> saveOrEditTestimony(@RequestBody TestimonyRequestDTO testimonyRequestDTO,
                                                                    HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(testimonialService.saveOrEditTestimony(testimonyRequestDTO,generalFunctions.getUserCache(httpServletRequest)), HttpStatus.OK);
    }
}
