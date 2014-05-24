package com.zoowii.online_editor.security

import com.zoowii.online_editor.utils.HttpContext

class Secured extends Security.Authenticator {
    @Override
    def getUsername(HttpContext context) {
        return context.request.session.username
    }

    @Override
    def onUnauthorized(HttpContext context) {
        context.response.sendRedirect('/site/loginPage')
    }
}
