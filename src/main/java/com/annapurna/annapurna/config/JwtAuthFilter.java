package com.annapurna.annapurna.config;

import com.annapurna.annapurna.Service.JwtService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.annapurna.annapurna.Utils.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    /**
     * The Jwt Service
     */
    @Autowired
    private JwtService jwtService;

    /**
     * The User Info Service
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Retrieve the Authorization header
        String authHeader = request.getHeader(AP_Constants.AUTHORIZATION);
        String token = null;
        String userNameOrEmail = null;

        // Check if the header starts with "Bearer "
        if(null!=authHeader &&authHeader.startsWith(AP_Constants.BEARER)){
            token = authHeader.substring(AP_Constants.NUMBER_SEVEN); // Extract token
            userNameOrEmail = jwtService.extractUserName(token);  // Extract username from token
        }

        // If the token is valid and no authentication is set in the context
        if(null!=userNameOrEmail && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userInfoService.loadUserByUsername(userNameOrEmail);

            // Validate token and set authentication
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request,response);

    }
}
