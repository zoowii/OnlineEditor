package com.zoowii.online_editor.controllers;

import com.zoowii.online_editor.models.AccountEntity;
import com.zoowii.online_editor.security.SecurityUtils;
import com.zoowii.online_editor.utils.BlogMetaInfo;
import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.action.ActionResults;
import com.zoowii.playmore.action.BaseController;
import com.zoowii.playmore.http.HttpUtils;
import com.zoowii.playmore.template.RenderContext;

import java.net.HttpURLConnection;

/**
 * Created by zoowii on 15/2/10.
 */
public class CController extends BaseController {
    protected String currentUsername() {
        return SecurityUtils.currentUsername(HttpUtils.getCurrentHttpRequest());
    }

    protected AccountEntity currentUser() {
        return AccountEntity.findByUserNameOrEmail(currentUsername());
    }

    protected RenderContext getBaseRenderContext() {
        RenderContext ctx = new RenderContext();
        ctx.put("isGuest", currentUser() == null);
        ctx.put("currentUser", currentUser());
        ctx.put("currentUserName", currentUsername());
        BlogMetaInfo metaInfo = new BlogMetaInfo();
        ctx.put("metaInfo", metaInfo);
        ctx.put("title", metaInfo.getSiteTitle());
        ctx.put("author", AccountEntity.findByUserNameOrEmail(metaInfo.getAuthor()));
        ctx.put("isAuthor", metaInfo.getAuthor().equals(currentUsername()));
        ctx.put("session", HttpUtils.getCurrentHttpRequest().getSession());
        return ctx;
    }

    protected void flash(String key, Object value) {
        HttpUtils.getCurrentHttpRequest().flash(key, value);
    }

    protected Object flash(String key) {
        return HttpUtils.getCurrentHttpRequest().flash(key);
    }

    protected ActionResult redirect(String url) {
        return ActionResults.ok("Redirect").setStatusCode(HttpURLConnection.HTTP_MOVED_TEMP).setHeader("Location", url);
    }
}
