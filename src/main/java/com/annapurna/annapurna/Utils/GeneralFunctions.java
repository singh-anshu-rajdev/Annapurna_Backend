package com.annapurna.annapurna.Utils;

import com.annapurna.annapurna.DTO.UserCacheDTO;
import com.annapurna.annapurna.Repository.FileRepository;
import com.annapurna.annapurna.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import java.util.List;
import java.util.UUID;

@Service
public class GeneralFunctions {

    @Value("${geocoding.api.key}")
    private String GEOCODING_API_KEY;

    @Value("${geocoding.api.url}")
    private String GEOCODING_URL;
    /**
     * The fileRepository of type FileRepository
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     * The passwordEncoder of type PasswordEncoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The jwtService of type JwtService
     */
    @Autowired
    private JwtService jwtService;

    /**
     * Method to build plain text content for OTP email
     *
     * @param userName
     * @param otp
     * @return
     */
    public String buildOtpEmailContent(String userName, Integer otp) {
        return "Hi " + userName + ",\n\n"
                + "Thank you for registering with us. Your OTP for verification is: " + otp + "\n\n"
                + "Please enter this OTP to complete the verification process. The OTP is valid for 30 minutes.\n\n\n"
                + "If you have not initiated this mail, You can safely ignore this mail.\n\n"
                + "Best regards,\n"
                + "Annapurna";
    }

    /**
     * Method to build plain text content for registration success email
     *
     * @param userName
     * @return
     */
    public String buildRegistrationSuccessContent(String userName) {
        return "Hi " + userName + ",\n\n"
                + "Congratulations! Your registration was successful.\n"
                + "Thank you for joining. Enjoy your meal with Annapurna!\n\n"
                + "You can now log in to your account and explore our services.\n\n"
                + "Best regards,\n"
                + "Annapurna";
    }

    /**
     * Method generateUniqueIdForProfilePictures
     *
     * @return
     */
    public String generateUniqueIdForProfilePictures(){
        String uniqueId = UUID.randomUUID().toString();
        List<Long> id = fileRepository.findLastCreatedId(PageRequest.of(0,1));
        if(null!=id && !id.isEmpty()){
            uniqueId = uniqueId.substring(0,24) + id.get(0);
        }else{
            uniqueId = uniqueId.substring(0,23) ;
        }
        return uniqueId;
    }

    /**
     *
     * @param password
     * @return
     */
    public String passwordEncoder(String password){
        return passwordEncoder.encode(password);
    }

    /**
     *
     * @param httpServletRequest
     * @return
     */
    public UserCacheDTO getUserCache(HttpServletRequest httpServletRequest){
        UserCacheDTO response = null;
        if(null!=httpServletRequest.getHeader(AP_Constants.AUTHORIZATION)){
            String request = httpServletRequest.getHeader(AP_Constants.AUTHORIZATION).substring(AP_Constants.NUMBER_SEVEN);
            response =  jwtService.extractUserCacheFromtoken(request);
        }
        return response;
    }

    /**
     *
     * @return
     */
    public String generateCode() {
        return UUID.randomUUID().toString().substring(0,3)+UUID.randomUUID().toString().substring(0,3);
    }

    /**
     *
     * @param userLat
     * @param userLon
     * @param shopLat
     * @param shopLon
     * @return
     */
    public double haversine(double userLat, double userLon, double shopLat, double shopLon) {

        double dLat = Math.toRadians(shopLat - userLat);
        double dLon = Math.toRadians(shopLon - userLon);
        userLat = Math.toRadians(userLat);
        shopLat = Math.toRadians(shopLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(userLat) * Math.cos(shopLat) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AP_Constants.EARTH_RADIUS_IN_KM * c;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public String getPinCode(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        String url = GEOCODING_URL + latitude + "," + longitude + "&key=" + GEOCODING_API_KEY;

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Parse JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            JsonArray results = jsonResponse.getAsJsonArray("results");
            for (JsonElement result : results) {
                JsonArray addressComponents = result.getAsJsonObject().getAsJsonArray("address_components");
                for (JsonElement component : addressComponents) {
                    JsonObject componentObj = component.getAsJsonObject();
                    JsonArray types = componentObj.getAsJsonArray("types");
                    for (JsonElement type : types) {
                        if (type.getAsString().equals("postal_code")) {
                            return componentObj.get("long_name").getAsString();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Pincode not found";
    }
}
