package com.zoowii.online_editor.controllers;

import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.action.BaseController;
import com.zoowii.playmore.annotation.Controller;
import com.zoowii.playmore.annotation.Route;

/**
 * Created by zoowii on 15/2/8.
 */
@Controller
@Route("")
public class SiteController extends BaseController {
    @Route(value = {"", "/", "/home"})
    public ActionResult index() {
        return ok("hello");
    }
}
