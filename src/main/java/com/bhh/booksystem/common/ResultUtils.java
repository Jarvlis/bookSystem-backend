package com.bhh.booksystem.common;

/**
 * Author:Jarvlis
 * Date:2023-08-15
 * Time:13:42
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(0, data, "OK");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message, String descrition){
        return new BaseResponse(errorCode.getCode(), null,message, descrition);
    }

    public static<T>  BaseResponse<T> error(ErrorCode errorCode, T data, String message){
        return new BaseResponse(errorCode.getCode(), data ,message, null);
    }

    /**
     * 失败
     * @param code
     * @return
     */
    public static BaseResponse error(int code,String message, String descrition){
        return new BaseResponse(code, null, message, descrition);
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String descrition){
        return new BaseResponse(errorCode.getCode(), null, errorCode.getMessage(), descrition);
    }
}
