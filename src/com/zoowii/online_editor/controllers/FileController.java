package com.zoowii.online_editor.controllers;

import com.alibaba.fastjson.JSONObject;
import com.zoowii.formutils.BindingResult;
import com.zoowii.formutils.Validator;
import com.zoowii.jpa_utils.extension.Paginator;
import com.zoowii.online_editor.forms.AjaxResponse;
import com.zoowii.online_editor.forms.CreateCloudFileForm;
import com.zoowii.online_editor.forms.CreateOrUpdateCloudFileForm;
import com.zoowii.online_editor.models.*;
import com.zoowii.online_editor.security.Secured;
import com.zoowii.online_editor.services.ICloudFileService;
import com.zoowii.online_editor.services.IMarkDownService;
import com.zoowii.online_editor.utils.Common;
import com.zoowii.playmore.action.ActionResult;
import com.zoowii.playmore.annotation.Controller;
import com.zoowii.playmore.annotation.Route;
import com.zoowii.playmore.http.HttpServletRequestWrapper;
import com.zoowii.playmore.security.Authenticated;
import com.zoowii.playmore.template.RenderContext;
import com.zoowii.playmore.template.RenderFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.tuple.Pair;
import zuice.annotations.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zoowii on 15/4/4.
 */
@Controller
@Route("/file")
public class FileController extends CController {
    @Autowired
    private RenderFactory renderFactory;
    @Autowired
    private Validator validator;
    @Autowired
    private IMarkDownService markDownService;
    @Autowired
    private ICloudFileService cloudFileService;

    @Route("/list")
    @Authenticated(Secured.class)
    public ActionResult list(HttpServletRequestWrapper request) {
        AccountEntity currentUser = currentUser();
        Paginator paginator = new Paginator();
        paginator.setPage(Common.tryParseInt(request.getParameter("page"), 1));
        paginator.setPageSize(Common.tryParseInt(request.getParameter("page_size"), 20));
        paginator = paginator.eq("author", currentUser);
        paginator.getOrders().add(Pair.of("lastUpdatedTime", false));
        List<CloudFileEntity> fileEntities = CloudFileEntity.find.findByPaginator(paginator);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("files", fileEntities);
        ctx.put("paginator", paginator);
        return ok(renderFactory.create("cloudfile/list.vm").render(ctx));
    }

    @Route("/create")
    @Authenticated(Secured.class)
    public ActionResult create(HttpServletRequestWrapper request) {
        return createInBucket(request);
    }

    @Route("/create_in_bucket")
    @Authenticated(Secured.class)
    public ActionResult createInBucket(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        String bucketName = request.getParameter("bucket_name");
        BucketEntity bucketEntity;
        if(bucketName != null && !bucketName.isEmpty()) {
            bucketEntity = BucketEntity.find.where().eq("name", bucketName).eq("owner", user).first();
        } else {
            bucketEntity = null;
        }
        RenderContext ctx = getBaseRenderContext();
        CloudFileEntity fileEntity = new CloudFileEntity();
        fileEntity.setId(0L);
        fileEntity.setName("");
        fileEntity.setDescription("");
        ctx.put("file", fileEntity);
        ctx.put("user", user);
        ctx.put("currentBucket", bucketEntity);
        ctx.put("userBuckets", BucketEntity.find.where().eq("owner", user).all());
        return ok(renderFactory.create("cloudfile/create.vm").render(ctx));
    }

    @Route("/view/:id")
    public ActionResult get(String idStr) {
        long id = Common.tryParseLong(idStr, -1);
        AccountEntity user = currentUser();
        CloudFileEntity fileEntity = CloudFileEntity.find.byId(id);
        if(fileEntity == null) {
            flash("error", "Can't find file " + id);
            return redirectToIndex();
        }
        if(fileEntity.getIsPrivate()) {
            if(user == null || !user.getId().equals(fileEntity.getAuthor().getId())) {
                flash("error", "you have no permission to view file " + id);
                return redirectToIndex();
            }
        }
        fileEntity.setVisitCount(fileEntity.getVisitCount() + 1);
        fileEntity.update();
        return ok(fileEntity.getContent()).setContentType(fileEntity.getMimeType()).setEncoding(fileEntity.getEncoding());
    }

    @Route("/view_markdown/:id")
    public ActionResult getMarkDown(String idStr) {
        long id = Common.tryParseLong(idStr, -1);
        AccountEntity user = currentUser();
        CloudFileEntity fileEntity = CloudFileEntity.find.byId(id);
        if(fileEntity == null) {
            flash("error", "Can't find file " + id);
            return redirectToIndex();
        }
        if(fileEntity.getIsPrivate()) {
            if(user == null || !user.getId().equals(fileEntity.getAuthor().getId())) {
                flash("error", "you have no permission to view file " + id);
                return redirectToIndex();
            }
        }
        fileEntity.setVisitCount(fileEntity.getVisitCount() + 1);
        fileEntity.update();
        return ok(markDownService.renderMarkDown(fileEntity.getContent())).setContentType("text/html").setEncoding(fileEntity.getEncoding());
    }

    @Route(value = "/create", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult doCreate(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        CreateCloudFileForm createCloudFileForm = request.asForm(CreateCloudFileForm.class);
        BindingResult bindingResult = validator.validate(createCloudFileForm);
        if(bindingResult.hasErrors()) {
            flash("error", bindingResult.getErrors().next().getErrorMessage());
            return redirect(urlFor(FileController.class, "create"));
        }
        BucketEntity bucketEntity = BucketEntity.find.byId(createCloudFileForm.getBucketId());
        if(bucketEntity == null || !user.getId().equals(bucketEntity.getOwner().getId())) {
            flash("error", "You don't have this file");
            return redirect(urlFor(FileController.class, "create"));
        }
        CloudFileEntity cloudFileEntity = new CloudFileEntity();
        cloudFileEntity.setAuthor(user);
        cloudFileEntity.setName(createCloudFileForm.getName());
        cloudFileEntity.setDescription(createCloudFileForm.getDescription());
        cloudFileEntity.setBucket(bucketEntity);
        cloudFileEntity.setIsPrivate(createCloudFileForm.isPrivateBool());
        cloudFileEntity.setContent("");
        cloudFileEntity.save();
        cloudFileService.addFileChangeLog(cloudFileEntity);
        flash("success", "Created file successfully!");
        return redirect(urlFor(FileController.class, "edit", cloudFileEntity.getId()));
    }

    @Route("/edit/:id")
    @Authenticated(Secured.class)
    public ActionResult edit(String idStr) {
        long id = Common.tryParseLong(idStr, -1);
        AccountEntity user = currentUser();
        CloudFileEntity cloudFileEntity = CloudFileEntity.find.byId(id);
        boolean isNew = cloudFileEntity == null;
        if(isNew) {
            cloudFileEntity = new CloudFileEntity();
        } else {
            if(!user.getId().equals(cloudFileEntity.getAuthor().getId())) {
                flash("error", "You have no permission to edit this file");
                return redirectToLogin();
            }
            cloudFileEntity.setVisitCount(cloudFileEntity.getVisitCount() + 1);
            cloudFileEntity.update();
        }
        RenderContext ctx = getBaseRenderContext();
        ctx.put("isNew", isNew);
        ctx.put("file", cloudFileEntity);
        return ok(renderFactory.create("cloudfile/edit_content.vm").render(ctx));
    }

    @Route(value = "/update", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult createOrUpdate(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        CreateOrUpdateCloudFileForm createOrUpdateCloudFileForm = request.asForm(CreateOrUpdateCloudFileForm.class);
        CloudFileEntity fileEntity = CloudFileEntity.find.byId(createOrUpdateCloudFileForm.getId());
        boolean isNew = fileEntity == null;
        if(isNew) {
            flash("error", "Can't find this file to update");
            return redirect(urlFor(FileController.class, "create"));
        }
        if(!user.getId().equals(fileEntity.getAuthor().getId())) {
            return json(new AjaxResponse(false, "You are not author of this file"));
        }
        if(fileEntity.getFileVersion() > createOrUpdateCloudFileForm.getVersion()) {
            return json(new AjaxResponse(false, "You file version is too old, please reflesh it"));
        }
        Set<String> newTagsSet = createOrUpdateCloudFileForm.getTagsSet();
        if(!fileEntity.getContent().equals(createOrUpdateCloudFileForm.getContent())
                || !fileEntity.getMimeType().equals(createOrUpdateCloudFileForm.getMimeType())
                || !(fileEntity.getTags().containsAll(newTagsSet) && newTagsSet.containsAll(fileEntity.getTags()))
                || !(fileEntity.getDateString().equals(DateFormatUtils.format(createOrUpdateCloudFileForm.getDate(), "yyyy-MM-dd")))) {
            fileEntity.setMimeType(createOrUpdateCloudFileForm.getMimeType());
            fileEntity.setContent(createOrUpdateCloudFileForm.getContent());
            fileEntity.setFileVersion(fileEntity.getFileVersion());
            fileEntity.setLastUpdatedTime(new Date());
            fileEntity.setTagsString(createOrUpdateCloudFileForm.getTags().trim());
            fileEntity.setDate(createOrUpdateCloudFileForm.getDate());
            fileEntity.update();
            cloudFileService.addFileChangeLog(fileEntity);
            for(FileTagMappingEntity fileTagMappingEntity : FileTagMappingEntity.find.where().eq("file", fileEntity).all()) {
                fileTagMappingEntity.delete();
            }
            for(String tagStr : createOrUpdateCloudFileForm.getTagsSet()) {
                FileTagEntity fileTagEntity = new FileTagEntity();
                fileTagEntity.setName(tagStr);
                fileTagEntity.save();
                FileTagMappingEntity fileTagMappingEntity = new FileTagMappingEntity();
                fileTagMappingEntity.setFile(fileEntity);
                fileTagMappingEntity.setOwner(fileEntity.getAuthor());
                fileTagMappingEntity.setTag(fileTagEntity);
                fileTagMappingEntity.save();
            }
        }
        JSONObject fileJson = new JSONObject();
        fileJson.put("id", fileEntity.getId());
        fileJson.put("version", fileEntity.getFileVersion());
        fileJson.put("name", fileEntity.getName());
        return json(new AjaxResponse(true, fileJson));
    }

    @Route(value = "/change_file_acl", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult changeAcl(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        int version = Common.tryParseInt(request.getParameter("version"), -1);
        long id = Common.tryParseLong(request.getParameter("id"), -1);
        CloudFileEntity fileEntity = CloudFileEntity.find.byId(id);
        if(fileEntity == null) {
            return json(new AjaxResponse(false, "Can't find file " + id));
        }
        if(!user.getId().equals(fileEntity.getAuthor().getId())) {
            return json(new AjaxResponse(false, "You are not the author of file " + id));
        }
        if(fileEntity.getFileVersion() > version) {
            return json(new AjaxResponse(false, "You version of this file is expired, please refresh it"));
        }
        fileEntity.setIsPrivate(!fileEntity.getIsPrivate());
        fileEntity.setLastUpdatedTime(new Date());
        fileEntity.setFileVersion(fileEntity.getFileVersion() + 1);
        fileEntity.update();
        cloudFileService.addFileChangeLog(fileEntity);
        JSONObject fileJson = new JSONObject();
        fileJson.put("id", fileEntity.getId());
        fileJson.put("version", fileEntity.getFileVersion());
        fileJson.put("name", fileEntity.getName());
        fileJson.put("isPrivate", fileEntity.getIsPrivate());
        return json(new AjaxResponse(true, fileJson));
    }
}
