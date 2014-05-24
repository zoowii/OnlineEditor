package com.zoowii.online_editor.utils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpContext {
    HttpServletRequest request
    HttpServletResponse response

    public HttpContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request
        this.response = response
    }

    public HttpContext() {

    }
}
