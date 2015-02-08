package com.zoowii.online_editor.security;

import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.http.HttpServletRequestWrapper;
import com.zoowii.playmore.security.Authenticator;

/**
 * Created by zoowii on 15/1/25.
 */
public class ApiSecured extends Authenticator {
    public static final String CURRENT_USERNAME_ATTRIBUTE = "current_username";

    @Override
    public String getUsername(HttpServletRequestWrapper request) {
        return (String) request.getSession().getAttribute(CURRENT_USERNAME_ATTRIBUTE);
    }

    @Override
    public ActionResult onUnauthorized(HttpServletRequestWrapper request) {
        return null;
    }
}
