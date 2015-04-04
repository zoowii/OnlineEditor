package com.zoowii.online_editor.controllers;

import com.zoowii.formutils.Validator;
import com.zoowii.jpa_utils.extension.Paginator;
import com.zoowii.online_editor.models.AccountEntity;
import com.zoowii.online_editor.models.BucketEntity;
import com.zoowii.online_editor.security.Secured;
import com.zoowii.online_editor.utils.Common;
import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.annotation.Controller;
import com.zoowii.playmore.annotation.Route;
import com.zoowii.playmore.http.HttpServletRequestWrapper;
import com.zoowii.playmore.security.Authenticated;
import com.zoowii.playmore.template.RenderContext;
import com.zoowii.playmore.template.RenderFactory;
import org.apache.commons.lang3.tuple.Pair;
import zuice.annotations.Autowired;

import java.util.List;

/**
 * Created by zoowii on 15/4/3.
 */
@Controller
@Route("/bucket")
public class BucketController extends CController {
    @Autowired
    private RenderFactory renderFactory;
    @Autowired
    private Validator validator;

    @Route("")
    @Authenticated(Secured.class)
    public ActionResult index(HttpServletRequestWrapper request) {
        AccountEntity currentUser = currentUser();
        Paginator paginator = new Paginator();
        paginator.setPage(Common.tryParseInt(request.getParameter("page"), 1));
        paginator.setPageSize(Common.tryParseInt(request.getParameter("page_size"), 20));
        paginator = paginator.eq("owner", currentUser);
        paginator.getOrders().add(Pair.of("createdTime", false));
        List<BucketEntity> bucketEntities = BucketEntity.find.findByPaginator(paginator);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("buckets", bucketEntities);
        ctx.put("paginator", paginator);
        return ok(renderFactory.create("bucket/list.vm").render(ctx));
    }

    @Route(value = "/create", methods = Route.GET)
    @Authenticated(Secured.class)
    public ActionResult createPage() {
        return ok(renderFactory.create("bucket/create.vm").render(getBaseRenderContext()));
    }

    @Route(value = "/create", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult create(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        String name = request.getParameter("name");
        if (name == null || name.trim().length() < 4 || name.trim().length() > 40) {
            flash("error", "bucket name's length must be [4, 40] characters");
            return redirect(urlFor(BucketController.class, "createPage"));
        }
        BucketEntity bucketEntity = BucketEntity.find.where().eq("name", name).first();
        if (bucketEntity != null) {
            flash("error", String.format("bucket %s exists!", name));
            return redirect(urlFor(BucketController.class, "createPage"));
        }
        bucketEntity = new BucketEntity();
        bucketEntity.setName(name);
        bucketEntity.setOwner(user);
        bucketEntity.save();
        flash("success", String.format("Created bucket %s successfully!", name));
        return redirect(urlFor(BucketController.class, "index"));
    }

}
