package com.annapurna.annapurna.Exception;

public enum ErrorCode implements ErrorHandle{

    /**
     * The Error_Annapurna_2000
     */
    ERR_AP_2000(2000,"Internal Server Error"),

    /**
     * The Error_Annapurna_2001
     */
    ERR_AP_2001(2001,"Invalid Request Content"),

    /**
     * The Error_Annapurna_2002
     */
    ERR_AP_2002(2002,"Error in uploading Image"),

    /**
     * The Error_Annapurna_2003
     */
    ERR_AP_2003(2003,"Error in downloading Image"),

    /**
     * The Error_Annapurna_2004
     */
    ERR_AP_2004(2004,"File Not Found"),

    /**
     * The Error_Annapurna_2005
     */
    ERR_AP_2005(2005,"Invalid Otp"),

    /**
     * The Error_Annapurna_2006
     */
    ERR_AP_2006(2006,"Invalid Request"),

    /**
     * The Error_Annapurna_2007
     */
    ERR_AP_2007(2007,"Email Not Found"),

    /**
     * The Error_Annapurna_2008
     */
    ERR_AP_2008(2008,"Phone Number not Found"),

    /**
     * The Error_Annapurna_2008
     */
    ERR_AP_2009(2009,"Email is already Verified"),

    /**
     * The Error_Annapurna_2008
     */
    ERR_AP_2010(2010,"Phone Number is already Verified"),

    /**
     * The Error_Annapurna_2011
     */
    ERR_AP_2011(2011,"Image not found"),

    /**
     * The Error_Annapurna_2012
     */
    ERR_AP_2012(2012,"Invalid Username or password"),

    /**
     * The Error_Annapurna_2013
     */
    ERR_AP_2013(2013,"No nearby Shops Found"),

    /**
     * The Error_Annapurna_2014
     */
    ERR_AP_2014(2014,"User already Registered"),

    /**
     * The Error_Annapurna_2015
     */
    ERR_AP_2015(2015,"User Not verified. please verify the user and try again!"),

    /**
     * The Error_Annapurna_2016
     */
    ERR_AP_2016(2016,"User Not Found"),

    /**
     * The Error_Annapurna_2017
     */
    ERR_AP_2017(2017,"Error in fetching the Testimonial Data"),

    /**
     * The Error_Annapurna_2018
     */
    ERR_AP_2018(2018,"Error in Saving/Editing the Testimonial Data"),

    /**
     * The Error_Annapurna_2019
     */
    ERR_AP_2019(2019,"Testimonial Data Not found"),

    /**
     * The Error_Annapurna_2020
     */
    ERR_AP_2020(2020,"Email Already Registered"),

    /**
     * The Error_Annapurna_2021
     */
    ERR_AP_2021(2021,"Shop Mail Already Verified"),

    /**
     * The Error_Annapurna_2022
     */
    ERR_AP_2022(2022,"Phone Number already registered"),

    /**
     * The Error_Annapurna_2023
     */
    ERR_AP_2023(2023,"Shop details not found"),

    ;
    /**
     * The ErrCode of type Integer
     */
    private final Integer errCode;

    /**
     * The message of type String
     */
    private final String message;

    /**
     *
     * @param errCode
     * @param message
     */
    ErrorCode(Integer errCode,String message){
        this.errCode = errCode;
        this.message = message;
    }

    /**
     *
     * @return ErrCode
     */
    @Override
    public Integer getErrorCode() {
        return this.errCode;
    }

    /**
     *
     * @return message
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
