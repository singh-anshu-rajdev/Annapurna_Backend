package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    @GetMapping("/unsecure/welcome")
    public ResponseEntity<String> unsecuretesting(){
        return new ResponseEntity<>("Unsecure API Tested successfully", HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> testing(HttpServletRequest httpServletRequest){
        System.out.println(generalFunctions.getUserCache(httpServletRequest));
        return new ResponseEntity<>("Secure API Tested successfully", HttpStatus.OK);
    }
}
