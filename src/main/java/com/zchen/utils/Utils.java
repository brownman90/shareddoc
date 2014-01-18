package com.zchen.utils;

import java.util.HashMap;

/**
 * @author Zhouce Chen
 * @version Jan 18, 2014
 */
public class Utils {
    public static HashMap<String, Object> emptyMap(){
        return new HashMap<String, Object>();
    }

    public static String slashExchange(String value){
        return value.replaceAll("\\\\", "/");
    }
}
