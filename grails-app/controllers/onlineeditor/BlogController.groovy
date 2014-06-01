package onlineeditor

import com.zoowii.online_editor.orm.Expr
import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

@Transactional
class BlogController extends BaseController {

    BlogService blogService
    BucketService bucketService
    TableService tableService

    def beforeInterceptor = [action: this.&auth, except: ['index', 'view', 'tag', 'indexOfZoowii']]

    def static allowedMethods = [updateMeta: 'POST']

    def indexOfZoowii() {
        return index('zoowii')
    }

    def index(String authorName) {
        def author = Account.findByUserName(authorName)
        println(authorName)
        if (!author) {
            flash.error = "Can't find user ${authorName}"
            redirectToIndex()
            return
        }
        def paginator = new Paginator(request)
        paginator.extraUrlParams = "&authorName=${authorName}"
        def articles = blogService.getRenderedArticles(paginator, author)
        def tags = blogService.getTags(author)
        def blogSiteTitle = blogService.getBlogSiteTitle(author)
        if (!blogSiteTitle) {
            blogSiteTitle = 'Blog Title'
        }
        def metaInfo = blogService.getBlogMetaInfo(author)
        def outLinks = blogService.getBlogOutLinks(author)
        render view: 'index', model: [articles: articles, outLinks: outLinks, paginator: paginator, author: author, blogTitle: blogSiteTitle, tags: tags, metaInfo: metaInfo, isAuthor: author == currentUser()]
    }

    def tag(String authorName, String name) {
        def author = Account.findByUserName(authorName)
        if (!author) {
            flash.error = "Can't find user ${authorName}"
            redirectToIndex()
            return
        }
        def paginator = new Paginator(request)
        paginator.extraUrlParams = "&authorName=${authorName}&name=${name}"
        // TODO
    }

    /**
     * view an article
     * @param id CloudFile id
     */
    def view(String id) {
        def file = CloudFile.findById(id)
        if (!file) {
            flash.error = "Can't find article ${id}"
            redirectToIndex()
            return
        }
        def author = file.author
        def tags = blogService.getTags(author)
        def blogSiteTitle = blogService.getBlogSiteTitle(author)
        if (!blogSiteTitle) {
            blogSiteTitle = 'Blog Title'
        }
        def article = blogService.parseArticle(file)
        def metaInfo = blogService.getBlogMetaInfo(author)
        def outLinks = blogService.getBlogOutLinks(author)
        [article: article, outLinks: outLinks, author: author, blogTitle: blogSiteTitle, tags: tags, isAuthor: author == currentUser(), metaInfo: metaInfo]
    }

    def admin() {
        def user = currentUser()
        def bucket = blogService.getOrCreateBlogBucket(user)
        def articleTable = blogService.getOrCreateBlogArticleTable(user)
        def paginator = new Paginator(request)
        def articleFiles = bucketService.findFilesByPaginator(bucket, user, paginator)
        def articles = blogService.parseArticles(articleFiles)
        def blogMeta = blogService.getBlogMetaInfo(user)
        def outLinksTable = blogService.getBlogOutLinksTable(user)
        [author: user, bucket: bucket, articleTable: articleTable, articles: articles, paginator: paginator, metaInfo: blogMeta, outLinksTable: outLinksTable]
    }

    def edit(String id) {
        def user = currentUser()
        def file = CloudFile.findById(id)
        if (file == null || file.author != user) {
            flash.error = "this article not exist or you aren't the author of this article"
            redirectToIndex()
            return
        }
        def article = blogService.parseArticle(file)
        [article: article, author: user]
    }

    def updateMeta() {
        def user = currentUser()
        String id = params.id
        def file = CloudFile.findById(id)
        if (file == null || file.author != user) {
            flash.error = "this article not exist or you aren't the author of this article"
            redirectToIndex()
            return
        }
        String title = params.title
        String tags = params.tags
        String dateStr = params.date
        String summary = params.summary
        def articleTable = blogService.getOrCreateBlogArticleTable(user)
        Entity entity = tableService.find(articleTable, Expr.createEQ('file_id', file.id))
        if (entity == null) {
            entity = new Entity(table: articleTable)
            articleTable.addEntity(entity)
            entity.save()
            articleTable.save()
            tableService.insertColumn(entity, 'file_id', id)
        }
        tableService.updateOrCreateColumn(entity, 'tags', tags)
        tableService.updateOrCreateColumn(entity, 'title', title)
        tableService.updateOrCreateColumn(entity, 'date', dateStr)
        tableService.updateOrCreateColumn(entity, 'summary', summary)
        flash.success = "update article ${title}'s meta info successfully"
        redirect(action: 'admin')
    }

}
