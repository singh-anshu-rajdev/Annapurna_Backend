package com.annapurna.annapurna.Exception;

public enum ErrorCode implements ErrorHandle{

    /**
     * The Error_Annapurna_2000
     */
    ERR_AP_2000(2000,"Internal Server Error"),
    /**
     * The Error_Annapurna_2001
     */
    ERR_AP_2001(2001,"Invalid Request Content");

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
