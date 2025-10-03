package com.springcrm.dto;

/**
 * Generic API response wrapper
 */
public class ApiResponse<T> {
    
    private String message;
    private T data;
    private boolean success;
    
    // Constructors
    public ApiResponse() {}
    
    public ApiResponse(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, true);
    }
    
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(message, null, true);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, false);
    }
    
    // Getters and Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
