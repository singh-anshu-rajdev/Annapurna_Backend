package com.annapurna.annapurna.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.config.UserInfoDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserInfoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserInfoServiceTest {
    @Autowired
    private UserInfoService userInfoService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link UserInfoService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        // Arrange
        User user = new User();
        user.setClientId(1);
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setDeletedFlag(true);
        user.setEmailId("42");
        user.setId(1L);
        user.setImageUniqueId("abc");
        user.setIsEmailVerified(true);
        user.setIsPhoneVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRoles("Roles");
        user.setUpdatedBy("2020-03-01");
        user.setUpdatedTs(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setUserName("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.getUserByUserNameOrEmailAndDeletedFlagFalse(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        UserDetails actualLoadUserByUsernameResult = userInfoService.loadUserByUsername("janedoe");

        // Assert
        verify(userRepository).getUserByUserNameOrEmailAndDeletedFlagFalse(eq("janedoe"));
        assertTrue(actualLoadUserByUsernameResult instanceof UserInfoDetails);
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities instanceof List);
        GrantedAuthority getResult = ((List<? extends GrantedAuthority>) authorities).get(0);
        assertTrue(getResult instanceof SimpleGrantedAuthority);
        assertEquals("Roles", getResult.toString());
        assertEquals("Roles", getResult.getAuthority());
        assertNull(actualLoadUserByUsernameResult.getPassword());
        assertNull(actualLoadUserByUsernameResult.getUsername());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }

    /**
     * Method under test: {@link UserInfoService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.getUserByUserNameOrEmailAndDeletedFlagFalse(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("janedoe"));
        verify(userRepository).getUserByUserNameOrEmailAndDeletedFlagFalse(eq("janedoe"));
    }
}
