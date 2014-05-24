package onlineeditor

import com.zoowii.online_editor.security.Authenticated
import com.zoowii.online_editor.security.Secured
import com.zoowii.online_editor.security.Security
import com.zoowii.online_editor.utils.Common
import com.zoowii.online_editor.utils.UserCommon
import grails.transaction.Transactional

@Transactional
class SiteController extends BaseController {

    def beforeInterceptor = [action: this.&auth, except: ['loginPage', 'login', 'index', 'register', 'doRegister']]

    static allowedMethods = [login: 'POST', doRegister: 'POST']

    def index() {
        def files = []
        if (currentUser()) {
            files = CloudFile.findAll('from CloudFile where author=? order by createdTime desc', [currentUser()], [max: 10])
        }
        render view: 'index', model: [files: files]
    }

    def loginPage() {
        render view: 'login'
    }

    def register() {
        render view: 'register'
    }

    def doRegister() {
        String username = params.username
        String email = params.email
        String password = params.password
        if (Account.findByUserName(username) || Account.findByEmail(email)) {
            flash.error = 'user name or email existed'
            redirect(action: 'register')
            return
        }
        if (!password || password.length() < 5 || password.length() > 30) {
            flash.error = "new password's length should be [5, 30]"
            redirect(action: 'register')
            return
        }
        def user = new Account(userName: username, email: email, aliasName: username)
        user.salt = Common.randomString(10)
        user.password = UserCommon.encryptPassword(password, user.salt)
        user.validate()
        if (user.hasErrors()) {
            flash.error = user.getErrors()
            redirect(action: 'register')
            return
        }
        user.save()
        flash.success = 'Welcome to Online Editor!'
        redirectToLogin()
    }

    def logout() {
        session.invalidate()
        redirect(action: 'index')
    }

    def login() {
        Account.initAccounts()
        String username = params.username
        def password = params.password
        def user = Account.findByUserNameOrEmailByOne(username)
        // TODO: add login log info
        if (user == null) {
            flash.error = "Can't find user ${username}"
            redirectToLogin()
            return
        }
        if (!user.password.equals(UserCommon.encryptPassword(password, user.salt))) {
            flash.error = 'wrong username/email or password'
            redirectToLogin()
            return
        }
        loginAsUser(user)
        flash.success = 'Login successfully!'
        return redirect(controller: 'site', action: 'index')
    }

    @Authenticated(Secured)
    def profile() {
        def user = currentUser()
//        if (user == null) {
//            redirectToLogin()
//            return
//        }
        [user: user]
    }

    def doChangePassword() {
        def user = currentUser()
        if (user == null) {
            flash.error = 'login required'
            redirectToLogin()
            return
        }
        String oldPassword = params.old_password
        String newPassword = params.new_password
        if (!user.checkPassword(oldPassword)) {
            flash.error = 'wrong password'
            redirect(action: 'profile')
            return
        }
        if (!newPassword || newPassword.length() < 5 || newPassword.length() > 30) {
            flash.error = "new password's length should be [5, 30]"
            redirect(action: 'profile')
            return
        }
        user.password = UserCommon.encryptPassword(newPassword, user.salt)
        user.save()
        flash.success = 'Change password successfully!'
        redirect(action: 'profile')
    }
}
