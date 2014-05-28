package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

@Transactional
class BlogController extends BaseController {

    BlogService blogService

    def index(String authorName) {
        def author = Account.findByUserName(authorName)
        if (!author) {
            flash.error = "Can't find user ${authorName}"
            redirectToIndex()
            return
        }
        def paginator = new Paginator(request)
        def articles = blogService.getRenderedArticles(paginator, author)
        def tags = blogService.getTags(author)
        def blogSiteTitle = blogService.getBlogSiteTitle(author)
        if (!blogSiteTitle) {
            blogSiteTitle = 'Blog Title'
        }
        [articles: articles, paginator: paginator, author: author, blogTitle: blogSiteTitle, tags: tags]
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
        [article: article, author: author, blogTitle: blogSiteTitle, tags: tags]
    }

    def tag(String name) {
        // TODO
    }

}
