package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.Service.TestService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.GeneralFunctions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TestController {

    /**
     * The generalFunctions of type GeneralFunctions
     */
    @Autowired
    GeneralFunctions generalFunctions;

    @Autowired
    TestService testService;

    @GetMapping("/unsecure/welcome")
    public ResponseEntity<String> unsecuretesting(){
        return new ResponseEntity<>("Unsecure API Tested successfully", HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> testing(HttpServletRequest httpServletRequest){
        System.out.println(generalFunctions.getUserCache(httpServletRequest));
        return new ResponseEntity<>("Secure API Tested successfully", HttpStatus.OK);
    }

    @PostMapping("/uploadExcelSheet")
    public ResponseEntity<String> uploadExcelSheet(@RequestParam("file") MultipartFile file){
        testService.uploadExcelSheet(file);
        return new ResponseEntity<>("Secure API Tested successfully", HttpStatus.OK);
    }

    @GetMapping("/downloadExcelSheet")
    public ResponseEntity<String> downLoadExcelSheet(HttpServletResponse response){
        testService.downLoadExcelSheet(response);
        return new ResponseEntity<>("Template Downloaded Successfully", HttpStatus.OK);
    }
}
