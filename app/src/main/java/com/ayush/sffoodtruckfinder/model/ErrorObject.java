package com.ayush.sffoodtruckfinder.model;

import com.ayush.sffoodtruckfinder.utils.AppConstant;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class ErrorObject {

    private String code;
    private String message;
    private Boolean error;

    public ErrorObject(String errorCode, String errorMessage) {
        super();
        this.code = errorCode;
        this.message = errorMessage;
    }

    public ErrorObject() {
        this(AppConstant.IErrorCode.defaultErrorCode,
                AppConstant.IErrorMessage.defaultErrorMessage);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return code;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        if (message != null) {
            return message;
        } else if (code != null) {
            return code;
        } else {
            return AppConstant.IErrorMessage.defaultErrorMessage;
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.code = errorCode;
    }

    public Boolean getStatus() {
        return error;
    }

}

