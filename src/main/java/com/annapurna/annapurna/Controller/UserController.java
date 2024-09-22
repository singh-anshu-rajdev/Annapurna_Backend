package com.annapurna.annapurna.Controller;

import com.annapurna.annapurna.DTO.GeneralResponseDTO;
import com.annapurna.annapurna.DTO.UserRegistrationDTO;
import com.annapurna.annapurna.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * The userService of type UserService
     */
    @Autowired
    UserService userService;

    /**
     *
     * @param userRegistrationDTO
     * @return
     */
    @PostMapping("/unsecure/userRegistration")
    public ResponseEntity<GeneralResponseDTO> userRegistration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.OK);
    }
}
