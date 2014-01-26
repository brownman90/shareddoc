package com.zchen.sdp.utils;

import java.util.HashMap;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class ResponseMap extends HashMap {

    public static ResponseMap get() {
        ResponseMap map = new ResponseMap();
        map.put("success", true);
        return map;
    }

    public ResponseMap success() {
        this.put("status", true);
        return this;
    }

    public ResponseMap success(String message) {
        this.put("status", true);
        this.put("message", message);
        return this;
    }

    public ResponseMap failure() {
        this.put("status", false);
        return this;
    }

    public ResponseMap failure(String message) {
        this.put("status", false);
        this.put("message", message);
        return this;
    }

    public ResponseMap setData(Object data) {
        this.put("data", data);
        return this;
    }

}
