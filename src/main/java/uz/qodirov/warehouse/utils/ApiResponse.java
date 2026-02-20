package uz.qodirov.warehouse.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private T data;
    private Map<String, Object> meta;


    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }


}
