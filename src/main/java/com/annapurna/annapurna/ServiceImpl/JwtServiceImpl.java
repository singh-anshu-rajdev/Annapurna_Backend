package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.DTO.UserCacheDTO;
import com.annapurna.annapurna.Exception.CustomValidationException;
import com.annapurna.annapurna.Exception.ErrorCode;
import com.annapurna.annapurna.Model.User;
import com.annapurna.annapurna.Repository.UserRepository;
import com.annapurna.annapurna.Service.JwtService;
import com.annapurna.annapurna.Utils.AP_Constants;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static com.annapurna.annapurna.config.EncryptionDecryptionConfig.*;

@Service
public class JwtServiceImpl implements JwtService {

    /**
     *  Logger instance to log important events and errors in the service.
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    /**
     * The TOKEN_BODY_ERROR_MESSAGE of type String
     */
    private static final String TOKEN_BODY_ERROR_MESSAGE = "Error in getting tokenBody...{}";

    /**
     * The TOKEN_BODY_ERROR_MESSAGE of type String
     */
    private static final String REFRESH_TOKEN_ERROR_MESSAGE = "Error in getting refresh token...{}";

    /**
     * The TOKEN_BODY_ERROR_MESSAGE of type String
     */
    private static final String TOKEN_USERNAME_ERROR_MESSAGE = "Error in getting userName...{}";

    /* The userRepository of type UserRepository */
    @Autowired
    UserRepository userRepository;


    /**
     * Secret key for Authentication
     */
    @Value("${authentication.secret.key}")
    private String secretKey;

    /**
     *
     * @param userNameOrEmail
     * @return
     */
    @Override
    public String generateToken(String userNameOrEmail){
        Map<String, Object> claims = new HashMap<>();
        String subject = userNameOrEmail;
        try{
            subject = tokenBodyByUserName(userNameOrEmail);
        }catch (Exception ex){
            logger.error(TOKEN_BODY_ERROR_MESSAGE,userNameOrEmail);
        }
        return createToken(claims,subject);
    }

    /**
     *
     * @param userNameOrEmail
     * @return
     */
    @Override
    public String generateRefreshToken(String userNameOrEmail){
        Map<String,Object> claims = new HashMap<>();
        String subject = null;
        try{
            subject = encrypt(userNameOrEmail,generateKey());
        }catch (Exception e){
            logger.error(REFRESH_TOKEN_ERROR_MESSAGE,userNameOrEmail);
        }
        return refreshToken(claims,subject);

    }

    /**
     *
     * @param claims
     * @param subject
     * @return
     */
    private String refreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @param token
     * @param userName
     * @return
     */
    @Override
    public Boolean validateToken(String token, String userName) {
        String subject = null;
        try {
            subject = decrypt(extractUserName(token), generateKey());
        } catch (Exception ex) {
            logger.error(REFRESH_TOKEN_ERROR_MESSAGE,ex.getMessage());
        }
        return (userName.equals(subject) && !isTokenExpired(token));
    }

    /**
     *
     * @param claims
     * @param userNameOrEmail
     * @return
     */
    private String createToken(Map<String, Object> claims, String userNameOrEmail) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userNameOrEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @return
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param token
     * @return
     */
    private String extractSubject(String token){
        return extractClaims(token, Claims::getSubject);
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public String extractUserName(String token){
        String subjectBody =  extractClaims(token, Claims::getSubject);
        try{
            return fetchUserNameFromGson(subjectBody);
        }catch (Exception ex){
            logger.error(TOKEN_USERNAME_ERROR_MESSAGE,subjectBody);
        }
        return subjectBody;
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    /**
     *
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @param userDetails
     * @return
     */
    @Override
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     *
     * @param userName
     * @return
     */
    private String tokenBodyByUserName(String userName) throws Exception {
        // fetch the user data
        User user = userRepository.getUserByUserNameOrEmailAndDeletedFlagFalse(userName).get();
        if(null==user){
            throw new CustomValidationException(ErrorCode.ERR_AP_2012);
        }
        // created the userCache
        UserCacheDTO userCacheDTO = new UserCacheDTO();
        userCacheDTO.setUserName(encrypt(userName,generateKey()));
        userCacheDTO.setName(user.getName());
        userCacheDTO.setClientId(user.getClientId());
        userCacheDTO.setUserId(encrypt(user.getId().toString(),generateKey()));;
        if(user.getRoles().equals(AP_Constants.ROLE_USER)){
            userCacheDTO.setRoleId(AP_Constants.USER_ROLE_ID);
        }else{
            userCacheDTO.setRoleId(AP_Constants.ADMIN_ROLE_ID);
        }
        // return the String data of Json
        return new Gson().toJson(userCacheDTO);
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public UserCacheDTO extractUserCacheFromtoken(String token){
        String subjectBody =  extractClaims(token, Claims::getSubject);
        try{
            return fetchUserDetailsFromGson(subjectBody);
        }catch (Exception ex){
            logger.error(TOKEN_USERNAME_ERROR_MESSAGE,subjectBody);
        }
        return null;
    }

    /**
     *
     * @param gsonString
     * @return
     * @throws Exception
     */
    private UserCacheDTO fetchUserDetailsFromGson(String gsonString) throws Exception {
        UserCacheDTO userCacheDTO = new Gson().fromJson(gsonString,UserCacheDTO.class);
        userCacheDTO.setUserId(decrypt(userCacheDTO.getUserId(),generateKey()));
        userCacheDTO.setUserName(decrypt(userCacheDTO.getUserName(),generateKey()));
        return userCacheDTO;
    }

    /**
     *
     * @param gsonString
     * @return
     * @throws Exception
     */
    private String fetchUserNameFromGson(String gsonString) throws Exception {
        UserCacheDTO userCacheDTO = new Gson().fromJson(gsonString,UserCacheDTO.class);
        String userName =  decrypt(userCacheDTO.getUserName(),generateKey());
        return userName;
    }
}
