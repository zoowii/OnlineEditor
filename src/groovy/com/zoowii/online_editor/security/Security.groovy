package com.zoowii.online_editor.security

import com.zoowii.online_editor.utils.HttpContext

public interface Security {

    public abstract class Authenticator {
        abstract def getUsername(HttpContext context);

        abstract def onUnauthorized(HttpContext context);
    }
}