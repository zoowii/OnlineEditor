package com.zoowii.online_editor.security;

import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.action.ActionResults;
import com.zoowii.playmore.http.HttpServletRequestWrapper;
import com.zoowii.playmore.security.Authenticator;

import java.net.HttpURLConnection;

/**
 * Created by zoowii on 15/2/10.
 */
public class Secured extends Authenticator {


    @Override
    public String getUsername(HttpServletRequestWrapper request) {
        return SecurityUtils.currentUsername(request);
    }

    @Override
    public ActionResult onUnauthorized(HttpServletRequestWrapper request) {
        return ActionResults.ok("You need login").setStatusCode(HttpURLConnection.HTTP_MOVED_TEMP).setHeader("Location", request.getContextPath() + "/login");
    }
}
