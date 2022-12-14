package com.example.takeoutproject.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor // all argument constructor
@NoArgsConstructor // no argument constructor
// @Builder
public class JsonData implements Serializable {
    /**
     * 1 success, else fail
     */
    private Integer code;
    /**
     * data
     */
    private Object data;
    /**
     * description
     */
    private String msg;

    // success
    public static JsonData buildSuccess() {
        return new JsonData(1, null, null);
    }

    // success with given data
    public static JsonData
    buildSuccess(Object data) {
        return new JsonData(1, data, null);
    }

    // success with given data, msg
    public static JsonData
    buildSuccess(Object data, String msg) {
        return new JsonData(1, data, msg);
    }

    // fail with given msg
    public static JsonData buildError(String msg) {
        return new JsonData(0, null, msg);
    }

    // fail with given msg and code
    public static JsonData buildError(String msg, Integer code) {
        return new JsonData(code, null, msg);
    }
}