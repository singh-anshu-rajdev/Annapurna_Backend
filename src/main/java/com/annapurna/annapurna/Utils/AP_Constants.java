package com.annapurna.annapurna.Utils;

import java.util.List;

public final class AP_Constants {

    /**
     * The DEFAULT_FILE_TYPE of type String
     */
    public static final String DEFAULT_FILE_TYPE = "profile";

    /**
     * The OTP_VERIFICATION_SUBJECT of type String
     */
    public static final String OTP_VERIFICATION_SUBJECT = "Thanks for Registering with Annapurna";

    /**
     * The WELCOMING_SUBJECT of type String
     */
    public static final String WELCOMING_SUBJECT = "Welcome to Annapurna";

    /**
     * The DEFAULT_USER of type String
     */
    public static final String DEFAULT_USER = "user";

    /**
     * The TRUE of type BOOLEAN
     */
    public static final Boolean TRUE = true;

    /**
     * The FALSE of type BOOLEAN
     */
    public static final Boolean FALSE = false;

    /**
     * The MAIL_SEND_SUCCESS_MESSAGE of type String
     */
    public static final String MAIL_SEND_SUCCESS_MESSAGE = "Mail sent Successfully";

    /**
     * The MAIL_SEND_FAILURE_MESSAGE of type String
     */
    public static final String MAIL_SEND_FAILURE_MESSAGE = "Failed to sent the Mail";

    /**
     * The USER_VERIFIED of type String
     */
    public static final String USER_VERIFIED = "User verified Successfully";

    /**
     * The AT_THE_RATE of type String
     */
    public static final String AT_THE_RATE = "@";

    /**
     * The USER_CLIENT_ID of type Integer
     */
    public static final Integer USER_CLIENT_ID = 1;

    /**
     * The ROLE_USER of type String
     */
    public static final String ROLE_USER = "ROLE_USER";

    /**
     * The USER_CREATED_SUCCESSFULLY of type String
     */
    public static final String USER_CREATED_SUCCESSFULLY = "User created Successfully";

    /**
     * The FEATURE_CREATED_SUCCESSFULLY of type String
     */
    public static final String FEATURE_CREATED_SUCCESSFULLY = "Feature created Successfully";

    /**
     * The USER_REGISTRATION_FAILED of type String
     */
    public static final String USER_REGISTRATION_FAILED = "User registration failed";

    /**
     * The IMAGE of type String
     */
    public static final String IMAGE = "image";

    /**
     * The USER_ROLE_ID of type Long
     */
    public static final Long USER_ROLE_ID = 2L;

    /**
     * The ADMIN_ROLE_ID of type Long
     */
    public static final Long ADMIN_ROLE_ID = 1L;

    /**
     * The ENCODED_DECODED_ALGORITHM of type String
     */
    public static final String ENCODED_DECODED_ALGORITHM = "AES";

    /**
     * The AUTHORIZATION of type String
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * The BEARER of type String
     */
    public static final String BEARER = "Bearer ";

    /**
     * The NUMBER_SEVEN of type Integer
     */
    public static final Integer NUMBER_SEVEN = 7;

    /**
     * The HEADER_LIST_FOR_BULK_UPLOAD of type List
     */
    public static final List<String> HEADER_LIST_FOR_BULK_UPLOAD = List.of("Full Name","EmailId","Phone Number","User Name","Password");


    /**
     * Initiate a new AP Constants
     */
    private AP_Constants(){
        // private Constructor
    }
}
