package com.annapurna.annapurna.Utils;

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

        if(null==user || null==user.get()){
            throw new UsernameNotFoundException("User not found: " + usernameOrEmail);
        }
        // Converting User to UserDetails
        UserDetails userDetails = new UserInfoDetails(user.get());
        return userDetails;
    }
}
