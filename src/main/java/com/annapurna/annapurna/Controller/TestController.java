package com.annapurna.annapurna.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/unsecure/welcome")
    public ResponseEntity<String> unsecuretesting(){
        return new ResponseEntity<>("Unsecure API Tested successfully", HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> testing(){
        return new ResponseEntity<>("Secure API Tested successfully", HttpStatus.OK);
    }
}
