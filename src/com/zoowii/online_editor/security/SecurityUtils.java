package com.zoowii.online_editor.security;

import com.zoowii.playmore.http.HttpServletRequestWrapper;

/**
 * Created by zoowii on 15/2/10.
 */
public class SecurityUtils {
    public static final String CURRENT_USERNAME_ATTRIBUTE = "current_username";
    public static String currentUsername(HttpServletRequestWrapper request) {
        return (String) request.getSession().getAttribute(CURRENT_USERNAME_ATTRIBUTE);
    }
}
