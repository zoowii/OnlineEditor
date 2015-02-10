package com.zoowii.online_editor.security;

import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.http.HttpServletRequestWrapper;

/**
 * Created by zoowii on 15/1/25.
 */
public class ApiSecured extends Secured {

    @Override
    public ActionResult onUnauthorized(HttpServletRequestWrapper request) {
        return null; // TODO
    }
}
