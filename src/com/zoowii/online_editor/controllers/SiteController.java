package com.zoowii.online_editor.controllers;

import com.zoowii.formutils.BindingResult;
import com.zoowii.formutils.Validator;
import com.zoowii.online_editor.forms.ChangePasswordForm;
import com.zoowii.online_editor.forms.LoginForm;
import com.zoowii.online_editor.forms.RegisterForm;
import com.zoowii.online_editor.models.AccountEntity;
import com.zoowii.online_editor.models.CloudFileEntity;
import com.zoowii.online_editor.security.Secured;
import com.zoowii.online_editor.security.SecurityUtils;
import com.zoowii.online_editor.services.IAccountService;
import com.zoowii.online_editor.utils.BCrypt;
import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.annotation.Controller;
import com.zoowii.playmore.annotation.Route;
import com.zoowii.playmore.http.HttpServletRequestWrapper;
import com.zoowii.playmore.security.Authenticated;
import com.zoowii.playmore.template.RenderContext;
import com.zoowii.playmore.template.RenderFactory;
import zuice.annotations.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zoowii on 15/2/8.
 */
@Controller
@Route("")
public class SiteController extends CController {
    @Autowired
    private RenderFactory renderFactory;
    @Autowired
    private Validator validator;
    @Autowired
    private IAccountService accountService;

    @Route(value = {"", "/", "/home"})
    @Authenticated(Secured.class)
    public ActionResult index() {
        accountService.initAccounts();
        List<CloudFileEntity> files = new ArrayList<CloudFileEntity>();
        if (currentUser() != null) {
            files = CloudFileEntity.find.where().eq("author", currentUser()).orderBy("createdTime", false).limit(20).all();
        }
        RenderContext ctx = getBaseRenderContext();
        ctx.put("files", files);
        return ok(renderFactory.create("site/index.vm").render(ctx));
    }

    @Route(value = "/login", methods = Route.GET)
    public ActionResult loginPage() {
        RenderContext ctx = getBaseRenderContext();
        return ok(renderFactory.create("site/login.vm").render(ctx));
    }

    @Route(value = "/login", methods = Route.POST)
    public ActionResult login(HttpServletRequestWrapper request) {
        accountService.initAccounts();
        LoginForm loginForm = request.asForm(LoginForm.class);
        BindingResult validateResult = validator.validate(loginForm);
        if (validateResult.hasErrors()) {
            flash("error", validateResult.getErrors().next().getErrorMessage());
            return redirect(request.getContextPath() + "/login");
        }
        AccountEntity user = accountService.findByUserNameOrEmail(loginForm.getUsername());
        if (user == null || !user.checkPassword(loginForm.getPassword())) {
            flash("error", "找不到用户或用户名密码错误");
            return redirect(request.getContextPath() + "/login");
        }
        request.getSession().setAttribute(SecurityUtils.CURRENT_USERNAME_ATTRIBUTE, user.getUserName());
        user.setLastLoginTime(new Date());
        user.update();
        flash("success", "登录成功!");
        return redirect(request.getContextPath() + "/");
    }

    @Route(value = "/register", methods = Route.POST)
    public ActionResult register(HttpServletRequestWrapper request) {
        accountService.initAccounts();
        RegisterForm registerForm = request.asForm(RegisterForm.class);
        BindingResult validateResult = validator.validate(registerForm);
        if(validateResult.hasErrors()) {
            flash("error", validateResult.getErrors().next().getErrorMessage());
            return redirect(request.getContextPath() + "/register");
        }
        if(AccountEntity.find.where().eq("userName", registerForm.getUsername()).first() != null
                || AccountEntity.find.where().eq("email", registerForm.getEmail()).first() != null) {
            flash("error", "user name or email existed");
            return redirect(request.getContextPath() + "/register");
        }
        AccountEntity user = new AccountEntity();
        user.setEmail(registerForm.getEmail().trim());
        user.setUserName(registerForm.getUsername().trim());
        user.setPassword(BCrypt.hashpw(registerForm.getPassword().trim(), BCrypt.gensalt()));
        user.save();
        flash("success", "Welcome to Online Editor!");
        return redirectToLogin();
    }

    @Route(value = "/register", methods = Route.GET)
    public ActionResult registerPage() {
        return ok(renderFactory.create("site/register.vm").render(getBaseRenderContext()));
    }

    @Route("/logout")
    public ActionResult logout(HttpServletRequestWrapper request) {
        request.getSession().invalidate();
        return redirect("");
    }

    @Route("/profile")
    @Authenticated(Secured.class)
    public ActionResult profile() {
        AccountEntity currentUser = currentUser();
        RenderContext ctx = getBaseRenderContext();
        ctx.put("user", currentUser);
        return ok(renderFactory.create("site/profile.vm").render(ctx));
    }

    @Route(value = "/profile/change_password", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult changePassword(HttpServletRequestWrapper request) {
        AccountEntity currentUser = currentUser();
        ChangePasswordForm form = request.asForm(ChangePasswordForm.class);
        BindingResult validateResult = validator.validate(form);
        if (validateResult.hasErrors()) {
            flash("error", validateResult.getErrors().next().getErrorMessage());
            return redirect(request.getContextPath() + "/");
        }
        if (!currentUser.checkPassword(form.getOldPassword())) {
            flash("error", "旧密码验证错误");
            return redirect(request.getContextPath() + "/profile");
        }
        currentUser.setPassword(BCrypt.hashpw(form.getNewPassword(), BCrypt.gensalt()));
        currentUser.update();
        flash("success", "修改密码成功");
        return redirect(request.getContextPath() + "/profile");
    }

}
