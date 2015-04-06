package com.zoowii.online_editor.controllers;

import com.google.common.base.Function;
import com.zoowii.formutils.Validator;
import com.zoowii.jpa_utils.extension.Paginator;
import com.zoowii.online_editor.beans.BlogArticle;
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
import com.zoowii.playmore.util.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import zuice.annotations.Autowired;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by zoowii on 15/4/3.
 */
@Controller
@Route("")
public class BlogController extends CController {

    @Autowired
    private RenderFactory renderFactory;
    @Autowired
    private Validator validator;
    @Autowired
    private IMarkDownService markDownService;
    @Autowired
    private ICloudFileService cloudFileService;

    private static final String DEFAULT_AUTHOR_NAME = "zoowii";

    @Route("/blog")
    public ActionResult indexOfDefault(HttpServletRequestWrapper request) {
        return index(request, DEFAULT_AUTHOR_NAME);
    }

    @Route("/blog_tag/:authorName/:tag")
    public ActionResult listOfTag(HttpServletRequestWrapper request, String authorName, String tagName) {
        try {
            tagName = URLDecoder.decode(tagName, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        AccountEntity author = AccountEntity.findByUserNameOrEmail(authorName);
        if(author == null) {
            flash("error", "Can't find author " + authorName);
            return redirectToIndex();
        }
        AccountEntity currentUser = currentUser();
        Paginator paginator = new Paginator();
        paginator.setPage(Common.tryParseInt(request.getParameter("page"), 1));
        paginator.setPageSize(Common.tryParseInt(request.getParameter("page_size"), 8));
        paginator = paginator.eq("author", author).eq("deleted", false).like("tagsString", "%" + tagName + "%");
        paginator.getOrders().add(Pair.of("date", false));
        List<BlogArticle> articles = ListUtils.map(CloudFileEntity.find.findByPaginator(paginator), new Function<CloudFileEntity, BlogArticle>() {
            @Override
            public BlogArticle apply(@Nullable CloudFileEntity cloudFileEntity) {
                return parseArticle(cloudFileEntity);
            }
        });
        List<String> tags = getTagsOfBlogAuthor(author);
        BlogMetaInfoEntity blogMetaInfoEntity = BlogMetaInfoEntity.getBlogMetaInfoOfUser(author);
        String blogSiteTitle = blogMetaInfoEntity.getSiteTitle();
        if(blogSiteTitle == null) {
            blogSiteTitle = "Blog Title";
        }
        List<BlogOutLinkEntity> outLinks = getOutLinksOfBlogAuthor(author);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("articles", articles);
        ctx.put("outLinks", outLinks);
        ctx.put("paginator", paginator);
        ctx.put("author", author);
        ctx.put("blogTitle", blogSiteTitle);
        ctx.put("tags", tags);
        ctx.put("metaInfo", blogMetaInfoEntity);
        ctx.put("isAuthor", author == currentUser);
        return ok(renderFactory.create("blog/index.vm").render(ctx));
    }

    @Route("/blog/:authorName")
    public ActionResult index(HttpServletRequestWrapper request, String authorName) {
        AccountEntity author = AccountEntity.findByUserNameOrEmail(authorName);
        if(author == null) {
            flash("error", "Can't find author " + authorName);
            return redirectToIndex();
        }
        AccountEntity currentUser = currentUser();
        Paginator paginator = new Paginator();
        paginator.setPage(Common.tryParseInt(request.getParameter("page"), 1));
        paginator.setPageSize(Common.tryParseInt(request.getParameter("page_size"), 8));
        paginator = paginator.eq("author", author).eq("deleted", false);
        paginator.getOrders().add(Pair.of("date", false));
        List<BlogArticle> articles = ListUtils.map(CloudFileEntity.find.findByPaginator(paginator), new Function<CloudFileEntity, BlogArticle>() {
            @Override
            public BlogArticle apply(@Nullable CloudFileEntity cloudFileEntity) {
                return parseArticle(cloudFileEntity);
            }
        });
        List<String> tags = getTagsOfBlogAuthor(author);
        BlogMetaInfoEntity blogMetaInfoEntity = BlogMetaInfoEntity.getBlogMetaInfoOfUser(author);
        String blogSiteTitle = blogMetaInfoEntity.getSiteTitle();
        if(blogSiteTitle == null) {
            blogSiteTitle = "Blog Title";
        }
        List<BlogOutLinkEntity> outLinks = getOutLinksOfBlogAuthor(author);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("articles", articles);
        ctx.put("outLinks", outLinks);
        ctx.put("paginator", paginator);
        ctx.put("author", author);
        ctx.put("blogTitle", blogSiteTitle);
        ctx.put("tags", tags);
        ctx.put("metaInfo", blogMetaInfoEntity);
        ctx.put("isAuthor", author == currentUser);
        return ok(renderFactory.create("blog/index.vm").render(ctx));
    }

    @Route("/blog/:authorName/tag/:tag")
    public ActionResult viewBlogOfTag(String authorName, String tagName) {
        return ok("TODO"); // TODO
    }

    private BlogArticle parseArticle(CloudFileEntity cloudFileEntity) {
        String html = markDownService.renderMarkDown(cloudFileEntity.getContent());
        BlogArticle article = new BlogArticle();
        article.setAuthor(cloudFileEntity.getAuthor());
        article.setCreatedTime(cloudFileEntity.getCreatedTime());
        article.setDate(cloudFileEntity.getDate());
        article.setHtml(html);
        article.setId(cloudFileEntity.getId());
        article.setSummary(html.length()>300 ? html.substring(0, 300):html); // TODO
        article.setTags(ListUtils.map(FileTagMappingEntity.find.where().eq("file", cloudFileEntity).all(), new Function<FileTagMappingEntity, String>() {
            @Nullable
            @Override
            public String apply(FileTagMappingEntity fileTagMappingEntity) {
                return fileTagMappingEntity.getTag() != null ? fileTagMappingEntity.getTag().getName() : null;
            }
        }));
        article.setTitle(cloudFileEntity.getName());
        return article;
    }

    private List<BlogOutLinkEntity> getOutLinksOfBlogAuthor(AccountEntity author) {
        return BlogOutLinkEntity.find.where().eq("owner", author).all();
    }

    private List<String> getTagsOfBlogAuthor(AccountEntity author) {
        return FileTagMappingEntity.getSession().findListByQuery(String.class,
                String.format("select distinct ft.tag.name from FileTagMappingEntity ft where ft.owner.id=%d", author.getId()));
    }

    @Route("/blog/:authorName/:id")
    public ActionResult view(HttpServletRequestWrapper request, String authorName, String idStr) {
        long id = Common.tryParseLong(idStr, -1);
        CloudFileEntity file = CloudFileEntity.find.byId(id);
        if(file == null) {
            flash("error", "Can't find file " + id);
            return redirect(request.getContextPath() + "/blog/" + authorName);
        }
        AccountEntity author = file.getAuthor();
        List<String> tags = getTagsOfBlogAuthor(author);
        BlogArticle article = parseArticle(file);
        List<BlogOutLinkEntity> outLinks = getOutLinksOfBlogAuthor(author);
        BlogMetaInfoEntity blogMetaInfoEntity = BlogMetaInfoEntity.getBlogMetaInfoOfUser(author);
        String blogSiteTitle = blogMetaInfoEntity.getSiteTitle();
        if(blogSiteTitle == null) {
            blogSiteTitle = "Blog Title";
        }
        RenderContext ctx = getBaseRenderContext();
        ctx.put("article", article);
        ctx.put("outLinks", outLinks);
        ctx.put("author", author);
        ctx.put("blogTitle", blogSiteTitle);
        ctx.put("tags", tags);
        ctx.put("isAuthor", author == currentUser());
        ctx.put("metaInfo", blogMetaInfoEntity);
        return ok(renderFactory.create("blog/view.vm").render(ctx));
    }

    @Route("/blog/admin")
    @Authenticated(Secured.class)
    public ActionResult blogAdmin(HttpServletRequestWrapper request) {
        AccountEntity user = currentUser();
        Paginator paginator = new Paginator();
        paginator.setPage(Common.tryParseInt(request.getParameter("page"), 1));
        paginator.setPageSize(Common.tryParseInt(request.getParameter("page_size"), 8));
        paginator = paginator.eq("author", user).eq("deleted", false);
        paginator.getOrders().add(Pair.of("lastUpdatedTime", false));
        List<CloudFileEntity> files = CloudFileEntity.find.findByPaginator(paginator);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("files", files);
        ctx.put("outLinks", getOutLinksOfBlogAuthor(user));
        ctx.put("tags", getTagsOfBlogAuthor(user));
        ctx.put("paginator", paginator);
        return ok(renderFactory.create("blog/admin.vm").render(ctx));
    }

    @Route("/blog/edit/:id")
    @Authenticated(Secured.class)
    public ActionResult editBlogArticle(String idStr) {
        long id = Common.tryParseLong(idStr, -1);
        AccountEntity user = currentUser();
        CloudFileEntity fileEntity = CloudFileEntity.find.byId(id);
        if(fileEntity == null || fileEntity.getAuthor() != user) {
            flash("error", "this article not exist or you aren't the author of this article");
            return redirectToIndex();
        }
        BlogArticle article = parseArticle(fileEntity);
        RenderContext ctx = getBaseRenderContext();
        ctx.put("article", article);
        ctx.put("file", fileEntity);
        ctx.put("author", user);
        return ok(renderFactory.create("blog/edit.vm").render(ctx));
    }

    @Route(value = "/blog/update_meta/:id", methods = Route.POST)
    @Authenticated(Secured.class)
    public ActionResult updateBlogArticleMeta(HttpServletRequestWrapper request) {
        return ok("TODO"); // TODO
    }

}
