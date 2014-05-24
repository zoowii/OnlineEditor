package com.zoowii.online_editor.security

import com.alibaba.fastjson.JSON
import com.zoowii.online_editor.utils.HttpContext

class AjaxSecured extends Security.Authenticator {
    @Override
    def getUsername(HttpContext context) {
        return context.request.session.username
    }

    @Override
    def onUnauthorized(HttpContext context) {
        def res = [success: false, data: 'login required', code: -2]
        context.response.writer.append(JSON.toJSONString(res))
    }
}
