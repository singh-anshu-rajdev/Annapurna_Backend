package com.annapurna.annapurna.Utils;

import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.config.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    /**
     * The User Repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param usernameOrEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUserNameOrEmailAndDeletedFlagFalse(usernameOrEmail);

        if(null==user || user.isEmpty() || null==user.get()){
            throw new CustomValidationException(ErrorCode.ERR_AP_2016);
        }

        if(!user.get().getIsEmailVerified() && !user.get().getIsPhoneVerified()){
            throw new CustomValidationException(ErrorCode.ERR_AP_2015);
        }
        // Converting User to UserDetails
        UserDetails userDetails = new UserInfoDetails(user.get());
        return userDetails;
    }
}
