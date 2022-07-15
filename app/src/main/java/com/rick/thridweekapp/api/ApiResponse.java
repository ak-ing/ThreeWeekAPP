package com.rick.thridweekapp.api;

/**
 * Created by Rick on 2022/7/14 15:17.
 * God bless my code!
 */
public class ApiResponse<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> Error(String msg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.msg = msg;
        response.code = 400;
        return response;
    }

    public static <T> ApiResponse<T> Success(T data) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.data = data;
        response.code = 200;
        return response;
    }

    public interface Result<T> {
        void onResult(ApiResponse<T> dataResult);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
