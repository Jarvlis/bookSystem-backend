package com.bhh.booksystem.exception;


import com.bhh.booksystem.common.ErrorCode;

/**
 * Author:Jarvlis
 * Date:2023-08-15
 * Time:16:02
 */
public class BussinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BussinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BussinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public BussinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
