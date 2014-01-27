package com.zchen.sdp.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebContext {
    private static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest req) {
        request.set(req);
    }

    public static HttpServletRequest getRequest() {
        return request.get();
    }

    public static void removeRequest() {
        request.remove();
    }

    public static void setResponse(HttpServletResponse rep) {
        response.set(rep);
    }

    public static HttpServletResponse getResponse() {
        return response.get();
    }

    public static void removeResponse() {
        response.remove();
    }

    public static  HttpSession getSession() {
        return request.get().getSession();
    }

    public static String getVal(String key) {
        return getRequest().getParameter(key);
    }
}
