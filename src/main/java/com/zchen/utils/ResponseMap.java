package com.zchen.utils;

import java.util.HashMap;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class ResponseMap extends HashMap {

    public static ResponseMap get() {
        return new ResponseMap();
    }

    public ResponseMap success() {
        this.put("success", true);
        return this;
    }

    public ResponseMap success(String message) {
        this.put("success", true);
        this.put("message", message);
        return this;
    }

    public ResponseMap failure(String message) {
        this.put("success", false);
        this.put("message", message);
        return this;
    }

}
