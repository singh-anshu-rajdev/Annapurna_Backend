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
    ERR_AP_2004(2004,"File Not Found");

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
