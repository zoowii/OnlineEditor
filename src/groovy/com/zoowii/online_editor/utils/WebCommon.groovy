package com.zoowii.online_editor.utils

import javax.servlet.http.HttpServletRequest

class WebCommon {

    def static isAjaxRequest(HttpServletRequest request) {
        def accept = request.getHeader('X-Requested-With')
        return request.contentType?.contains('XMLHttpRequest')
    }
}
