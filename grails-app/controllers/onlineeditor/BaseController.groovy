package onlineeditor

import com.alibaba.fastjson.JSON
import com.zoowii.online_editor.security.Authenticated
import com.zoowii.online_editor.utils.Common
import com.zoowii.online_editor.utils.HttpContext
import com.zoowii.online_editor.utils.WebCommon

class BaseController {
    def getLoginUrl() {
        createLink(controller: 'site', action: 'loginPage')
    }

    def redirectToLogin() {
        redirect(uri: getLoginUrl())
    }

    def loginAsUser(Account account) {
        session.username = account.userName
    }

    def currentUser() {
        if (!session.username) {
            return null
        } else {
            return Account.findByUserName(session.username)
        }
    }

    def auth() {
//        println('action: ' + this.actionName)
//        println(this.controllerClass)
//        println(this.controllerName)
//        def packageName = Common.getPackageOfClass(this.class)
//        println(packageName)
//        Class controllerCls = Common.findControllerClassUnderPackage(packageName, this.controllerName)
//        println(controllerCls)
//        if (controllerCls == null) {
//            return
//        }
//        def method = Common.findClassMethodByName(controllerCls, this.actionName)
//        if (!method) {
//            return
//        }
//        def securityAnnotation = method.getAnnotation(Authenticated)
//        println(securityAnnotation)
//        println(method.getAnnotations().size())
//        for(def anno : method.getAnnotations()) {
//            println(anno)
//        }
//        if (!securityAnnotation) {
//            println("this method ${this.actionName} doesn't need auth")
//            return
//        }
//        def authenticatorCls = securityAnnotation.value()
//        if (!authenticatorCls) {
//            return
//        }
//        def authenticator = (Authenticator) authenticatorCls.newInstance()
//        def ctx = new HttpContext(request, response)
//        if (!authenticator.getUsername(ctx)) {
//            authenticator.onUnauthorized(ctx)
//            return false
//        }
        if (!currentUser()) {
            println(request.getHeaders('Accept'))
            if (WebCommon.isAjaxRequest(request)) {
                ajaxFail('login required')
            } else {
                redirectToLogin()
            }
            return false
        }
    }

    def ajaxResponse(boolean success, Object data, int code) {
        def res = [success: success, data: data, code: code]
        render text: JSON.toJSONString(res), encoding: 'UTF-8'
    }

    def ajaxSuccessWithCode(Object data, int code) {
        ajaxResponse(true, data, code)
    }

    def ajaxSuccess(Object data) {
        ajaxSuccessWithCode(data, 0)
    }

    def ajaxFailWithCode(Object data, int code) {
        ajaxResponse(false, data, code)
    }

    def ajaxFail(Object data) {
        ajaxFailWithCode(data, -1)
    }
}
